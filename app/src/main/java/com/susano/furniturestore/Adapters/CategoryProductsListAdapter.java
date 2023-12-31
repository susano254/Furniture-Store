package com.susano.furniturestore.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.susano.furniturestore.Activities.DetailsActivity;
import com.susano.furniturestore.Models.Product;
import com.susano.furniturestore.R;

import java.net.URI;
import java.util.ArrayList;

public class CategoryProductsListAdapter extends RecyclerView.Adapter<CategoryProductsListAdapter.ViewHolder> {
    private final Context context;
    ArrayList<Product> products;

    public CategoryProductsListAdapter(Context context, ArrayList<Product> products){
        this.context = context;
        this.products = products;
    }
    @NonNull
    @Override
    public CategoryProductsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view, parent, false);
        return new CategoryProductsListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryProductsListAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);

        holder.product_name.setText(product.getName());
        holder.product_price.setText("$" + product.getPrice());


        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference imageref = storageReference.child("images/" + product.getImageKey());
        imageref.getDownloadUrl().addOnSuccessListener(uri -> {
            String imageURL = uri.toString();


            Glide.with(context)
                    .load(imageURL)
                    .into(holder.product_image);
        });

        holder.card.setOnClickListener(v->{
            Intent intent = new Intent(context, DetailsActivity.class);

            intent.putExtra("category", products.get(position).getCategory());
            intent.putExtra("product_id", products.get(position).getProduct_id());
            intent.putExtra("imageKey", products.get(position).getImageKey());
            intent.putExtra("name", products.get(position).getName());
            intent.putExtra("price", products.get(position).getPrice());
            intent.putExtra("description", products.get(position).getDescription());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout wrapper;
        CardView card;
        ImageView product_image;
        TextView product_name, product_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wrapper = itemView.findViewById(R.id.wrapper);
            card = itemView.findViewById(R.id.cardview);
            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
        }
    }
}
