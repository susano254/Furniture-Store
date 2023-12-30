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
        holder.card.setOnClickListener(v->{
            Intent intent = new Intent(context, DetailsActivity.class);

            intent.putExtra("id", products.get(position).product_id);
            intent.putExtra("imageKey", products.get(position).imageKey);
            intent.putExtra("name", products.get(position).name);
            intent.putExtra("price", products.get(position).price);
            intent.putExtra("description", products.get(position).description);
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
