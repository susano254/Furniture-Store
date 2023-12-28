package com.susano.furniturestore.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.susano.furniturestore.R;

public class SignIn extends AppCompatActivity {
    EditText emailEditText, passwordEditText;
    TextView register;
    ProgressBar signin_progressBar;
    MaterialButton sign_in_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailEditText = findViewById(R.id.signin_edittext_email);
        passwordEditText = findViewById(R.id.signin_edittext_password);
        sign_in_btn = findViewById(R.id.sign_in_btn);
        register = findViewById(R.id.register_textview_btn);
        signin_progressBar = findViewById(R.id.signin_progressBar);

        register.setOnClickListener(v -> {
            startActivity(new Intent(SignIn.this, SignUp.class));
            finish();
        });

        sign_in_btn.setOnClickListener(v -> {
            login();

        });
    }

    private void login() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        boolean isValid = validateData(email, password);

        if(!isValid) return;
        inProgress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            inProgress(false);
            if(task.isSuccessful()){
                if(firebaseAuth.getCurrentUser().isEmailVerified())
                    startActivity(new Intent(SignIn.this, MainActivity.class));
                else
                    Toast.makeText(SignIn.this, "Email is not Verified, Please verify your email", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(SignIn.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void inProgress(boolean busy){
        if(busy){
            signin_progressBar.setVisibility(View.VISIBLE);
            sign_in_btn.setVisibility(View.GONE);
        }
        else {
            sign_in_btn.setVisibility(View.VISIBLE);
            signin_progressBar.setVisibility(View.GONE);
        }
    }


    private boolean validateData(String email, String password){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Email is invalid");
            return false;
        }
        if(password.length() < 8){
            passwordEditText.setError("Password is too short");
            return false;
        }
        return true;
    }
}