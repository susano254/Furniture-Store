package com.susano.furniturestore.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

    private RecyclerView categoryRecyclerView;
    private CategoryProductsListAdapter categoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoyr_products);

        loadData(savedInstanceState);
        Toast.makeText(this, category, Toast.LENGTH_SHORT).show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(category)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(CategoryProducts.this, "success", Toast.LENGTH_SHORT).show();
                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            String name = document.get("name").toString();
//                            double price = Double.parseDouble(document.get("price").toString());
//                            String imageKey = document.get("imageKey").toString();
//                            String description = document.get("description").toString();
//                            Product product = new Product(name, description, imageKey, category, price);
//                            products.add(product);

                        }
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



    private void CategoryRecyclerView() {
        categoryRecyclerView = findViewById(R.id.categories_recyclerView);
        categoriesAdapter = new CategoryProductsListAdapter(CategoryProducts.this, products);
        categoryRecyclerView.setAdapter(categoriesAdapter);
    }
}