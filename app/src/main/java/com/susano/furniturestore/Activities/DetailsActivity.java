package com.susano.furniturestore.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.susano.furniturestore.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailsActivity extends AppCompatActivity {
    String category, product_id, name, description, imageKey;
    double price;

    ConstraintLayout cart_nav_btn;
    ImageView product_img, favBtn;
    TextView product_name, product_price, product_description;
    ImageButton back;
    AppCompatButton addToCartButton;

    boolean isFavorite = false;


    FirebaseFirestore firestore;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        product_img = findViewById(R.id.product_image);
        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.product_price);
        product_description = findViewById(R.id.product_description);
        addToCartButton = findViewById(R.id.addBtn);
        cart_nav_btn = findViewById(R.id.cart_nav_btn);



        favBtn = findViewById(R.id.favBtn);
        back = findViewById(R.id.back_btn);

        loadData(savedInstanceState);

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference imageref = storageReference.child("images/" + imageKey);
        imageref.getDownloadUrl().addOnSuccessListener(uri -> {
            String imageURL = uri.toString();


            Glide.with(this)
                    .load(imageURL)
                    .into(product_img);

        });
        product_name.setText(name);
        product_price.setText(Double.toString(price));
        product_description.setText(description);



        back.setOnClickListener(v -> finish());
        cart_nav_btn.setOnClickListener(v-> startActivity(new Intent(DetailsActivity.this, CartActivity.class)));
        favBtn.setOnClickListener(v->{
            isFavorite = !isFavorite;
            if(isFavorite) {
                favBtn.setImageResource(R.drawable.favorite);
                addToFavorite();
            }

            else {
                favBtn.setImageResource(R.drawable.favourite_white);
                removeFromFavorite();
            }

        });
        addToCartButton.setOnClickListener(v -> addToCart());
    }

    private void addToCart() {
        String date, time;
        date = new SimpleDateFormat("dd, MM, yyyy").format(Calendar.getInstance().getTime());
        time = new SimpleDateFormat("HH:MM:ss").format(Calendar.getInstance().getTime());

        HashMap<String, Object> productOrder = new HashMap<>();
        productOrder.put("category", category);
        productOrder.put("product_id", product_id);
        productOrder.put("date", date);
        productOrder.put("time", time);
        productOrder.put("quantity", 1);

        firestore.collection("Carts").document(user.getUid()).collection("MyCart")
                .add(productOrder).addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                        Toast.makeText(DetailsActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(DetailsActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void addToFavorite() {
        Toast.makeText(DetailsActivity.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
    }

    private void removeFromFavorite() {
        Toast.makeText(DetailsActivity.this, "Removed from Favourites", Toast.LENGTH_SHORT).show();
    }

    private void loadData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {

                category = extras.getString("category");
                product_id = extras.getString("product_id");
                imageKey = extras.getString("imageKey");
                name = extras.getString("name");
                price = extras.getDouble("price");
                description = extras.getString("description");
            }
        }
    }

}