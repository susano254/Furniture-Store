package com.susano.furniturestore.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.susano.furniturestore.Adapters.CategoriesAdapter;
import com.susano.furniturestore.Models.Category;
import com.susano.furniturestore.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    LinearLayout add_product_btn;
    FloatingActionButton cart_btn;

    private RecyclerView categoryRecyclerView;
    private CategoriesAdapter categoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getUserData();
        CategoryRecyclerView();
        add_product_btn = findViewById(R.id.add_product_btn);

        add_product_btn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddProduct.class));
        });

    }

    private void getUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            boolean emailVerified = user.isEmailVerified();

            String uid = user.getUid();
        }
    }

    private void CategoryRecyclerView() {
        categoryRecyclerView = findViewById(R.id.categories_recyclerView);
        ArrayList<Category> categoryList = new ArrayList<>();

        //This is static data that will need to be loaded dynamically from a database
        categoryList.add(new Category("Chair", "hanged_chair"));
        categoryList.add(new Category("Table", "wood_table"));
        categoryList.add(new Category("Sofa", "sofa"));
        categoryList.add(new Category("Lights", "wall_lamp"));

        categoriesAdapter = new CategoriesAdapter(MainActivity.this, categoryList);
        categoryRecyclerView.setAdapter(categoriesAdapter);
    }
}