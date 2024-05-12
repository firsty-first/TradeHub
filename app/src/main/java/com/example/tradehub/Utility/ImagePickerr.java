package com.example.tradehub.Utility;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.loader.content.CursorLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImagePickerr {

    // Request code for picking an image from the gallery
    public static final int REQUEST_GALLERY = 1;

    // Request code for capturing an image from the camera
    public static final int REQUEST_CAMERA = 2;

    // Method to open the camera or gallery
    public static void openCameraOrGallery(Activity activity) {
        // Create a file to save the captured image
        File photoFile = null;
        try {
            photoFile = createImageFile(activity);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (photoFile != null) {
            // Get the URI of the saved image file
            Uri photoURI = Uri.fromFile(photoFile);

            // Create an intent to open the camera
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

            // Create an intent to open the gallery
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            // Create a chooser intent to allow the user to choose between camera and gallery
            Intent chooserIntent = Intent.createChooser(galleryIntent, "Choose image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

            if (chooserIntent.resolveActivity(activity.getPackageManager()) != null) {
                // Start the chooser intent
                activity.startActivityForResult(chooserIntent, REQUEST_GALLERY);
            } else {
                // Display a message if no apps can handle the chooser intent
                Toast.makeText(activity, "No apps available to handle image picking", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Display a message if there was an error creating the image file
            Toast.makeText(activity, "Error creating image file", Toast.LENGTH_SHORT).show();
        }
    }

    // Create a file to save the captured image
    private static File createImageFile(Activity activity) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }
    // Method to get the file path of the last captured image
    public static File getLastCapturedImageFile(Context context) {
        String[] projection = {MediaStore.Images.ImageColumns.DATA};
        String sortOrder = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC";

        CursorLoader cursorLoader = new CursorLoader(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, sortOrder);

        Cursor cursor = cursorLoader.loadInBackground();
        if (cursor != null && cursor.moveToFirst()) {
            String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA));
            cursor.close();
            return new File(filePath);
        }

        return null;
    }
    // Method to get the actual file from URI
    public static File getFileFromUri(Context context, Uri uri) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File file = null;
        try {
            // Create a temporary file to store the image
            file = File.createTempFile("temp_image", null, context.getCacheDir());
            outputStream = new FileOutputStream(file);
            inputStream = context.getContentResolver().openInputStream(uri);

            // Copy the image data from the input stream to the output stream
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close streams
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
// Path: Home_Fragment.java
