package com.example.tradehub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.tradehub.databinding.ActivityProductDetailsBinding;
import com.example.tradehub.pojo.Product;

public class ProductDetailActivity extends AppCompatActivity {
ActivityProductDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Inside onCreate method or elsewhere in the receiving activity
        Intent intent = getIntent();
        if (intent != null) {
            // Retrieve data based on the key used to pass the data
            Product product = (Product) intent.getSerializableExtra("product");
            Log.d("check", "onCreate: "+product.getName());

            binding.textProductName.setText(product.getName());
            binding.textProductPrice.setText(product.getPrice());
           // binding.textProductDescription.setText(product.getDescription());

        }

    }
}