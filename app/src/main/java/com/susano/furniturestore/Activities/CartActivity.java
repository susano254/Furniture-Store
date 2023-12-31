package com.susano.furniturestore.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.susano.furniturestore.Adapters.CartProductsAdapter;
import com.susano.furniturestore.Interfaces.OnProductLoadedCallback;
import com.susano.furniturestore.Models.CartItem;
import com.susano.furniturestore.Models.Product;
import com.susano.furniturestore.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    LinearLayout checkout_btn;
    ImageButton back;
    RecyclerView recyclerView;
    TextView shipping_fee_txt, total_txt;


    CartProductsAdapter cartProductsAdapter;
    ArrayList<CartItem> cartItems;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartItems = new ArrayList<>();

        back = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.products_recyclerView);

        shipping_fee_txt = findViewById(R.id.shipping_fee);
        total_txt = findViewById(R.id.total);

        checkout_btn = findViewById(R.id.checkout_btn);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        back.setOnClickListener(v-> finish());

        checkout_btn.setOnClickListener(v->{
            // TODO
            Toast.makeText(CartActivity.this, "Check Out", Toast.LENGTH_SHORT).show();
        });
        loadData();
    }


    private void loadData(){
        firestore.collection("Carts").document(user.getUid()).collection("MyCart").get()
                .addOnCompleteListener(task -> {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String category = document.get("category").toString();
                        String product_id = document.get("product_id").toString();
                        String date = document.get("date").toString();
                        String time = document.get("time").toString();
                        String quantity = document.get("quantity").toString();

                        get_product(category, product_id, product -> {
                            // Do something with the product, like adding it to cartItems
                            if (product != null) {
                                cartItems.add(new CartItem(product, date, time, Integer.parseInt(quantity)));
                            }

                            // Check if all products are loaded and update the UI
                            if (cartItems.size() == task.getResult().size()) {
                                if (cartItems.size() > 0) {
                                    cartProductsAdapter = new CartProductsAdapter(CartActivity.this, cartItems);
                                    recyclerView.setAdapter(cartProductsAdapter);

                                    double shipping_fee = 40.00;
                                    double total = shipping_fee;
                                    for (CartItem cartItem : cartItems){
                                        total += cartItem.getPrice()*cartItem.getQuantity();
                                    }
                                    shipping_fee_txt.setText(Double.toString(shipping_fee));
                                    total_txt.setText(Double.toString(total));
                                }
                            }
                        });
                    }
                });
    }

    private void get_product(String category, String product_id, OnProductLoadedCallback callback) {
        // get product with the current id
        firestore.collection(category)
                .whereEqualTo("product_id", product_id)
                .get()
                .addOnCompleteListener(taskProduct -> {
                    if (taskProduct.isSuccessful()) {
                        for (QueryDocumentSnapshot documentProduct : taskProduct.getResult()) {
                            String name = documentProduct.get("name").toString();
                            double price = Double.parseDouble(documentProduct.get("price").toString());
                            String imageKey = documentProduct.get("imageKey").toString();
                            String description = documentProduct.get("description").toString();
                            Product product = new Product(product_id, name, description, imageKey, category, price);
                            callback.onProductLoaded(product);
                        }
                    }
                    else {
                        Toast.makeText(CartActivity.this, taskProduct.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        callback.onProductLoaded(null);
                    }
                });
    }
}