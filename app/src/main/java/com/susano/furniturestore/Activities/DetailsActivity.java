package com.susano.furniturestore.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.susano.furniturestore.R;

public class DetailsActivity extends AppCompatActivity {
    String name, description, imageKey;
    double price;
    int id;
    ImageView product_img, favBtn;
    TextView product_name, product_price, product_description, num_txt;
    ImageButton minus, plus, back;

    boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        product_img = findViewById(R.id.product_image);
        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.product_price);
        product_description = findViewById(R.id.product_description);

        favBtn = findViewById(R.id.favBtn);
        minus = findViewById(R.id.minusBtn);
        plus = findViewById(R.id.plusBtn);
        num_txt = findViewById(R.id.numTxt);
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



        back.setOnClickListener(v -> finish());
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
        minus.setOnClickListener(v -> {
            int value = Integer.parseInt(num_txt.getText().toString());
            if(value <= 0) return;
            value--;
            num_txt.setText(Integer.toString(value));
        });
        plus.setOnClickListener(v -> {
            int value = Integer.parseInt(num_txt.getText().toString());
            value++;
            num_txt.setText(Integer.toString(value));
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
                id = extras.getInt("id");
                name = extras.getString("name");
                imageKey = extras.getString("imageKey");
                description = extras.getString("description");
                price = extras.getDouble("price");
            }
        }
    }

}