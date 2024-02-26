package com.example.tradehub.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tradehub.R;
import com.example.tradehub.pojo.adModel;

import java.util.ArrayList;

public class Adapteradvertisement extends RecyclerView.Adapter {

    ArrayList<adModel> adArrayList;
    Context context;
    public Adapteradvertisement(Context context, ArrayList<adModel> adArrayList)
    {
        this.context=context;
        this.adArrayList=adArrayList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.rv_advertisement_card,parent,false);
        return  new adViewholder(view);
    }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            adModel adItem = adArrayList.get(position);
            adViewholder adViewHolder = (adViewholder) holder;

            // Set the image resource for the ImageView
           // adViewHolder.imageView.setImageURI(Uri.parse(adItem.getAdImage()));

            // Set the text for the TextView
            adViewHolder.textView.setText(adItem.getAdTitle());
    }

    @Override
    public int getItemCount() {
        return adArrayList.size();
    }
    public class adViewholder extends  RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textView;
        public adViewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.adImage);
            textView=itemView.findViewById(R.id.adtextView);
        }
    }
}
