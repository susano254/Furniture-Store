package com.susano.furniturestore.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.susano.furniturestore.R;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddProduct extends AppCompatActivity {
    ConstraintLayout add_image_btn;
    MaterialButton add_product_btn;
    EditText productName, productPrice, productDiscription;
    ImageView add_image_view;
    Spinner spinner;
    public Uri imageUri;
    String spinner_choice;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    private String productImageKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productDiscription = findViewById(R.id.product_discription);
        spinner = findViewById(R.id.categories_spinner);

        add_product_btn = findViewById(R.id.add_product_btn);
        add_image_view = findViewById(R.id.add_img_imageView);
        add_image_btn = findViewById(R.id.add_img_btn);
        add_image_btn.setOnClickListener(v -> {
            choosePicture();
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_choice = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddProduct.this, spinner_choice, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        add_product_btn.setOnClickListener(v-> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            // Create a new user with a first and last name
            Map<String, Object> product = new HashMap<>();
            product.put("name", productName.getText().toString());
            product.put("price", productPrice.getText().toString());
            product.put("category", spinner_choice);
            product.put("Discription", productDiscription.getText().toString());
            product.put("picture", productImageKey);

            // Add a new document with a generated ID
            db.collection(spinner_choice)
                    .add(product)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddProduct.this, "product added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddProduct.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        });
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            add_image_view.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        productImageKey = UUID.randomUUID().toString();
        ProgressDialog progressDialog = new ProgressDialog(AddProduct.this);
        progressDialog.setTitle("Uploading image...");
        progressDialog.show();

        // Create a reference to 'images/mountains.jpg'
        StorageReference imgRef = storageReference.child("images/" + productImageKey);

        imgRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
            progressDialog.dismiss();
            Toast.makeText(AddProduct.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Toast.makeText(AddProduct.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }).addOnProgressListener(snapshot -> {
            double progressPercent = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
            progressDialog.setMessage("Percentage: " + (int) progressPercent + "%");

        });


    }
}