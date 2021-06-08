package com.example.foodprintapps;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoodViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.image_data_view);
        textView=itemView.findViewById(R.id.text_data_view);
    }
}
