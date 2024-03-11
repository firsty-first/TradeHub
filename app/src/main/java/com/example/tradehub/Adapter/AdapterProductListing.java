package com.example.tradehub.Adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tradehub.ProductDetailsActivity;
import com.example.tradehub.R;
import com.example.tradehub.pojo.productModel;

import java.util.ArrayList;

public class AdapterProductListing extends RecyclerView.Adapter {
    ArrayList<productModel> productArrayList;
    Context context;
    public AdapterProductListing(Context context, ArrayList<productModel> productArrayList)
    {
        this.context=context;
        this.productArrayList=productArrayList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.rv_product_item,parent,false);
        return  new productViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        productModel productModel = productArrayList.get(position);

        productViewholder productViewholder = (productViewholder) holder;

        // Set the image resource for the ImageView
        // adViewHolder.imageView.setImageURI(Uri.parse(adItem.getAdImage()));

        // Set the text for the TextView
        productViewholder.productName.setText(productModel.getName());
        productViewholder.price.setText(productModel.getPrice());
        //TODO: will add in next version
       // productViewholder.productDescription.setText(productModel.getDescription());
        productViewholder.distance.setText(productModel.getName());
        productViewholder.productCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  context.startActivity(new Intent(context, ProductDetailsActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }
    public class productViewholder extends  RecyclerView.ViewHolder
    {
        ImageView productImage;
        CardView productCard;
        TextView productName,price,distance,timeRemaining;
        public productViewholder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.productImage);
            productName=itemView.findViewById(R.id.productName);
            price=itemView.findViewById(R.id.price);
            distance=itemView.findViewById(R.id.distance);
            timeRemaining=itemView.findViewById(R.id.timer);
            productCard=itemView.findViewById(R.id.productCardview);
        }
    }
}