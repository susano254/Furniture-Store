package com.susano.furniturestore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.susano.furniturestore.Models.CartItem;
import com.susano.furniturestore.R;

import java.util.ArrayList;

public class CartProductsAdapter extends RecyclerView.Adapter<CartProductsAdapter.ViewHolder> {
    private final Context context;
    ArrayList<CartItem> cartItems;

    public CartProductsAdapter(Context context, ArrayList<CartItem> cartItems){
        this.context = context;
        this.cartItems = cartItems;
    }
    @NonNull
    @Override
    public CartProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product, parent, false);
        return new CartProductsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartProductsAdapter.ViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);

        holder.product_name.setText(cartItem.getName());
        holder.product_price.setText(Double.toString(cartItem.getPrice()));
        holder.quantity.setText(Integer.toString(cartItem.getQuantity()));
        loadImage(cartItem.getImageKey(), holder.product_image);




        holder.delete.setOnClickListener(v->{
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            Toast.makeText(context, "Item removed from cart", Toast.LENGTH_SHORT).show();
            notifyCart();
        });
        holder.minus.setOnClickListener(v -> {
            int value = Integer.parseInt(holder.quantity.getText().toString());
            if(value <= 0) return;
            value--;
            holder.quantity.setText(Integer.toString(value));
            notifyCart();
        });
        holder.plus.setOnClickListener(v -> {
            int value = Integer.parseInt(holder.quantity.getText().toString());
            value++;
            holder.quantity.setText(Integer.toString(value));
            notifyCart();
        });
    }


    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    private void loadImage(String imageKey, ImageView view){
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference imageref = storageReference.child("images/" + imageKey);
        imageref.getDownloadUrl().addOnSuccessListener(uri -> {
            String imageURL = uri.toString();


            Glide.with(context)
                    .load(imageURL)
                    .into(view);
        });
    }


    private void notifyCart() {
    }






    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout wrapper;
        ImageView product_image;
        TextView product_name, product_price, quantity;
        ImageButton minus, plus, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wrapper = itemView.findViewById(R.id.wrapper);
            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            quantity = itemView.findViewById(R.id.numTxt);

            delete = itemView.findViewById(R.id.deleteButton);
            minus = itemView.findViewById(R.id.minusBtn);
            plus = itemView.findViewById(R.id.plusBtn);
        }
    }
}
