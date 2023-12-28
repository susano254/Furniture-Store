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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.susano.furniturestore.R;

public class SignUp extends AppCompatActivity {
    EditText emailEditText, passwordEditText, confirmPasswordEditText;
    MaterialButton register_btn;
    ProgressBar signup_progressbar;
    TextView login_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailEditText = findViewById(R.id.signup_email_edit_text);
        passwordEditText = findViewById(R.id.signup_editText_password);
        confirmPasswordEditText = findViewById(R.id.signup_editText_confirm_password);
        register_btn = findViewById(R.id.sign_up_btn);
        signup_progressbar = findViewById(R.id.signup_progressbar);
        login_btn = findViewById(R.id.login_textview_btn);



        register_btn.setOnClickListener(v -> createAccount() );

        login_btn.setOnClickListener(v -> {
            startActivity(new Intent(SignUp.this, SignIn.class));
            finish();
        });
    }

    private void createAccount() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        boolean isValid = validateData(email, password, confirmPassword);

        if(!isValid) return;

        inProgress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUp.this, task -> {
            inProgress(false);
            if (task.isSuccessful()) {
                Toast.makeText(SignUp.this, "Account created Succesfully check your email for verification", Toast.LENGTH_SHORT).show();
                firebaseAuth.getCurrentUser().sendEmailVerification();
                firebaseAuth.signOut();
                finish();
            }
            else {
                Toast.makeText(SignUp.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        });


    }

    private void inProgress(boolean busy){
        if(busy){
            signup_progressbar.setVisibility(View.VISIBLE);
            register_btn.setVisibility(View.GONE);
        }
        else {
            register_btn.setVisibility(View.VISIBLE);
            signup_progressbar.setVisibility(View.GONE);
        }
    }


    private boolean validateData(String email, String password, String confirmPassword){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Email is invalid");
            return false;
        }
        if(password.length() < 8){
            passwordEditText.setError("Password is too short");
            return false;
        }
        if(!password.equals(confirmPassword)){
            confirmPasswordEditText.setError("Passwords don't match");
            return false;
        }

        return true;
    }
}