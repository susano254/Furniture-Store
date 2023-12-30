package com.susano.furniturestore.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.susano.furniturestore.Activities.CategoryProducts;
import com.susano.furniturestore.Activities.DetailsActivity;
import com.susano.furniturestore.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.susano.furniturestore.Models.Category;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    ArrayList<Category> categories;
    Context context;

    public CategoriesAdapter(Context context, ArrayList<Category> categories){
        this.context = context;
        this.categories = categories;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout wrapper;
        TextView name;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wrapper = itemView.findViewById(R.id.category_wrapper);
            name = itemView.findViewById(R.id.category_name);
            image = itemView.findViewById(R.id.category_pic);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(categories.get(position).getTitle());

        holder.wrapper.setOnClickListener(v->{
            Intent intent = new Intent(context, CategoryProducts.class);
            intent.putExtra("category",holder.name.getText().toString());
            context.startActivity(intent);
        });

        String imageName = categories.get(position).getPic();
        int resourceId = holder.itemView.getContext().getResources().getIdentifier(imageName, "drawable", holder.itemView.getContext().getPackageName());
        holder.image.setImageResource(resourceId);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

}
