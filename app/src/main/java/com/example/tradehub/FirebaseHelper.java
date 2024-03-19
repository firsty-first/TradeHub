package com.example.tradehub;

import com.example.tradehub.pojo.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.List;
import java.util.ArrayList;

import android.util.Log;

import androidx.annotation.NonNull;

public class FirebaseHelper {

    private FirebaseFirestore db;

    public FirebaseHelper() {
        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
    }

    // Method to fetch products sorted alphabetically by name
    public void fetchProductsSortedByName(final ProductFetchListener listener) {
        db.collection("products")
                .orderBy("name")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Product> productList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Product product = document.toObject(Product.class);
                            productList.add(product);
                        }
                        listener.onProductsFetched(productList);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle errors
                        Log.e("FirebaseHelper", "Error fetching products from Firestore: " + e.getMessage());
                        listener.onFetchError(e);
                    }
                });
    }

    // Method to add a new product to Firestore
    public void addProduct(Product product, final ProductAddListener listener) {
        db.collection("products")
                .add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Product added successfully
                        listener.onProductAdded();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to add product
                        listener.onAddError(e);
                    }
                });
    }

    // Listener interface for fetching products
    public interface ProductFetchListener {
        void onProductsFetched(List<Product> productList);

        void onFetchError(Exception e);
    }

    // Listener interface for adding a product
    public interface ProductAddListener {
        void onProductAdded();

        void onAddError(Exception e);
    }

}
