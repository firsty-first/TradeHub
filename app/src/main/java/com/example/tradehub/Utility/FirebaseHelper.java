package com.example.tradehub.Utility;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

public class FirebaseHelper {

    private FirebaseFirestore db;
    String productUniqueId;

    public String getProductUniqueId() {
        return productUniqueId;
    }

    public void setProductUniqueId(String productUniqueId) {
        this.productUniqueId = productUniqueId;
    }

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
                        productUniqueId=documentReference.getId();
                        listener.onProductAdded();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

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
    // Upload photo to Firebase Storage
    public static void uploadPhoto(Uri photoUri, String folderName, String categoryName, final OnPhotoUploadListener listener) {
        // Create a storage reference with the folder name and category
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("product").child("category");

        // Upload the photo to Firebase Storage
        UploadTask uploadTask = storageRef.putFile(photoUri);

        // Register observers to listen for when the upload is successful or fails
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Photo uploaded successfully
                // Get the download URL of the uploaded photo
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Retrieve the download URL and pass it to the listener
                        listener.onUploadSuccess(uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // Error getting the download URL
                        listener.onUploadFailure(e.getMessage());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                // Photo upload failed
                listener.onUploadFailure(e.getMessage());
            }
        });
    }
    public void updateProductField(String productId, String fieldName, Object value, final OnProductUpdateListener listener) {
        // Create a reference to the specific product document
        DocumentReference productRef = db.collection("products").document(productId);

        // Create a map with the field to be updated and its new value
        Map<String, Object> updates = new HashMap<>();
        updates.put(fieldName, value);

        // Update the specific field of the product document
        productRef.update(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Field updated successfully
                        listener.onProductUpdateSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to update field
                        listener.onProductUpdateFailure(e);
                    }
                });
    }

    // Other methods...

    // Listener interface for product update events
    public interface OnProductUpdateListener {
        void onProductUpdateSuccess();
        void onProductUpdateFailure(Exception e);
    }

    // Listener interface for photo upload events
    public interface OnPhotoUploadListener {
        void onUploadSuccess(String downloadUrl);
        void onUploadFailure(String errorMessage);
    }

}
