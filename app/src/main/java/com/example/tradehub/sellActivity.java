package com.example.tradehub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tradehub.databinding.ActivityAddItemtoSellBinding;
import com.example.tradehub.pojo.Product;

public class sellActivity extends AppCompatActivity {
ActivityAddItemtoSellBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddItemtoSellBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseHelper firebaseHelper = new FirebaseHelper();

binding.buttonUpload.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        binding.buttonUpload.setEnabled(false);
        Product product= new Product();
        product.setPrice(binding.editTextPrice.getText().toString());
        product.setName(binding.editTextTitle.getText().toString());
        Log.d("check", "onCreate: "+binding.editTextTitle.getText().toString());
        Log.d("check", "onCreate: "+binding.editTextPrice.getText().toString());
        product.setDescription("long description here ");
        product.setCategory("category");
        product.setAvailibilty("yes");
        product.setSellerInfo("seller info");
        product.setImage_url("image url");
        product.setLatitude(0.0);
        product.setLongitude(0.0);
        firebaseHelper.addProduct(product, new FirebaseHelper.ProductAddListener() {
            @Override
            public void onProductAdded() {
                // Handle the event when the product is added successfully
                Log.d("check", "onProductAdded: ");
                TODO: //ANIMATION with a tick mark for success
                Toast.makeText(sellActivity.this, "Addes for sale ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAddError(Exception e) {
Log.d("check", "onAddError: "+e.getMessage());
binding.buttonUpload.setEnabled(true);
            }

        });

    }
});

    }
}