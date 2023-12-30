package com.susano.furniturestore.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.susano.furniturestore.Adapters.CategoriesAdapter;
import com.susano.furniturestore.Adapters.CategoryProductsListAdapter;
import com.susano.furniturestore.Models.Category;
import com.susano.furniturestore.Models.Product;
import com.susano.furniturestore.R;

import java.util.ArrayList;

public class CategoryProducts extends AppCompatActivity {
    String category;
    ArrayList<Product> products;

    private RecyclerView productsRecyclerView;
    private CategoryProductsListAdapter productsAdapter;

    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoyr_products);


        back = findViewById(R.id.back_btn);
        back.setOnClickListener(v-> finish());



        products = new ArrayList<>();
        loadData(savedInstanceState);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(category)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.get("name").toString();
                            double price = Double.parseDouble(document.get("price").toString());
                            String imageKey = document.get("imageKey").toString();
                            String description = document.get("description").toString();
                            Product product = new Product(name, description, imageKey, category, price);
                            products.add(product);
                        }
                        productsRecyclerView = findViewById(R.id.products_recyclerView);
                        productsAdapter = new CategoryProductsListAdapter(CategoryProducts.this, products);
                        productsRecyclerView.setAdapter(productsAdapter);
                    } else {
                        Toast.makeText(CategoryProducts.this, "Error getting documents.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void loadData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                category = extras.getString("category");
            }
        }
    }
}