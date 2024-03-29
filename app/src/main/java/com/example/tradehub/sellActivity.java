package com.example.tradehub;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tradehub.Utility.FirebaseHelper;
import com.example.tradehub.Utility.ImagePicker;
import com.example.tradehub.databinding.ActivityAddItemtoSellBinding;
import com.example.tradehub.pojo.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import com.google.firebase.storage.UploadTask;
public class sellActivity extends AppCompatActivity {
    ActivityAddItemtoSellBinding binding;
   public static FirebaseHelper firebaseHelper;
    Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddItemtoSellBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseHelper = new FirebaseHelper();
        binding.imageViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.openCameraOrGallery(sellActivity.this);

            }
        });


        binding.buttonUpload.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View view) {
                binding.buttonUpload.setEnabled(false);
                Product product = new Product();
                // Upload photo to Firebase Storage
                if (photoURI != null)
                    FirebaseHelper.uploadPhoto(photoURI, "product", "category", new FirebaseHelper.OnPhotoUploadListener() {
                        @Override
                        public void onUploadSuccess(String downloadUrl) {
                            // Photo uploaded successfully, use the download URL
                            Log.d("Firebase", "Upload success. Download URL: " + downloadUrl);
                            product.setImage_url(downloadUrl);
                        }

                        @Override
                        public void onUploadFailure(String errorMessage) {
                            // Photo upload failed
                            Log.e("Firebase", "Upload failed: " + errorMessage);
                        }
                    });


                product.setPrice(binding.editTextPrice.getText().toString());
                product.setName(binding.editTextTitle.getText().toString());
                Log.d("check", "onCreate: " + binding.editTextTitle.getText().toString());
                Log.d("check", "onCreate: " + binding.editTextPrice.getText().toString());
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
                        TODO:
                        //ANIMATION with a tick mark for success
                        Toast.makeText(sellActivity.this, "Addes for sale ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAddError(Exception e) {
                        Log.d("check", "onAddError: " + e.getMessage());
                        binding.buttonUpload.setEnabled(true);
                    }

                });

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == ImagePicker.REQUEST_GALLERY && data != null) {
                // User selected an image from the gallery
                Uri selectedImageUri = data.getData();
                File selectedImage = ImagePicker.getFileFromUri(this, selectedImageUri);
                binding.imageViewItem.setImageURI(selectedImageUri);


                uploadImageToFirebase(this, selectedImageUri, "product", new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        photoURI = uri;
                    }
                }, new OnFailureListener() {

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(sellActivity.this, "Error uploading image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                // Now you can use the selectedImageUri to do further processing, such as displaying the image in an ImageView or uploading it to a server
            } else if (requestCode == ImagePicker.REQUEST_CAMERA) {
                // User captured an image with the camera
                // Get the URI of the saved image file
                File photoFile = ImagePicker.getLastCapturedImageFile(getApplicationContext());
                if (photoFile != null) {

                    binding.imageViewItem.setImageURI(photoURI);
                    // Now you can use the photoURI to do further processing, such as displaying the image in an ImageView or uploading it to a server
                } else {
                    // Handle the case where the image file is not available
                    Toast.makeText(this, "Error: Image file not found", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public static void uploadImageToFirebase(Activity activity, Uri imageUri, String storagePath,
                                             OnSuccessListener<Uri> onSuccessListener,
                                             OnFailureListener onFailureListener) {
        // Create a storage reference
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        // Create a reference to the image file
        StorageReference imageRef = storageRef.child(storagePath + "/" + imageUri.getLastPathSegment());

        // Upload the image file to Firebase Storage
        imageRef.putFile(imageUri)
                .addOnSuccessListener(activity, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get the download URL for the uploaded image
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUri) {

                                // Image uploaded successfully, get its URI
                                Log.d("firebase",firebaseHelper.getProductUniqueId());
                                firebaseHelper.updateProductField(firebaseHelper.getProductUniqueId(), "image_url", downloadUri, new FirebaseHelper.OnProductUpdateListener() {
                                    @Override
                                    public void onProductUpdateSuccess() {
                                        // Price updated successfully
                                    }

                                    @Override
                                    public void onProductUpdateFailure(Exception e) {
                                        // Handle update failure
                                    }
                                });

                                onSuccessListener.onSuccess(downloadUri);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                onFailureListener.onFailure(e);
                            }
                        });
                    }
                })
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // Handle unsuccessful uploads
                        onFailureListener.onFailure(e);
                    }
                });
    }

}