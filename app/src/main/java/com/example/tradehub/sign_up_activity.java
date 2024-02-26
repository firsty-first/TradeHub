package com.example.tradehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.tradehub.databinding.ActivitySignUpBinding;
import com.example.tradehub.pojo.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sign_up_activity extends BaseActivity {
    private   FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    //ProgressDialog progressDialog;
    ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseApp.initializeApp(this);

        DatabaseReference testRef = FirebaseDatabase.getInstance().getReference().child("test");
        if (testRef.setValue("Hii i m child").isSuccessful()) {
            Log.d("Auth", "Database working fine");
        } else {
            Log.d("Auth", "Database issueeeeeeeeeeeeeee");
        }

        // progressDialog=new ProgressDialog(getApplicationContext());
       // progressDialog.setTitle("Creating acccount");
        //progressDialog.setMessage("creating your account");
        binding.oldUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(sign_up_activity.this,signInActivity.class));
                finish();
            }
        });
        // Toast.makeText(this, firebaseAuth.getUid(), Toast.LENGTH_SHORT).show();
        binding.signInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Auth","clicked for signup");
                if(!(binding.editTextTextEmailAddress.getText().toString().length()<10 || binding.editTextTextPassword.getText().toString().length()<5)) {
                    Log.d("Auth","edit text are good n valid");
                   // progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(binding.editTextTextEmailAddress.getText().toString(),
                            binding.editTextTextPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           //  progressDialog.dismiss();
                            Log.d("Auth","On complete ke andr ");
                            if (task.isSuccessful()) {
                                Log.d("Auth","On complete ke andr success ke andr ");
                                UserModel user = new UserModel();
                                user.setEmail(binding.editTextTextEmailAddress.getText().toString());
                                user.setUsername(binding.edittextName.getText().toString());

                                String id = task.getResult().getUser().getUid();
                                //user.setProfile_pic("null");
                                if(database.getReference().push().child("test").setValue("Hii i m child").isSuccessful())
                                Log.d("Auth","Database working fine");
                                else
                                    Log.d("Auth","Database issueeeeeeeeeeeeeee");
                                database.getReference().push().child("user").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                            Log.d("Auth","on complete");
                                        Log.d("Auth",user.getUsername());
                                        Toast.makeText(sign_up_activity.this, "User created successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(sign_up_activity.this, signInActivity.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("Auth",task.getException().toString());
                                        Log.d("Auth","Could not save in db");
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        if(task.isSuccessful())
                                            Log.d("Auth","on success");
                                        Log.d("Auth",user.getUsername());
                                        Toast.makeText(sign_up_activity.this, "User created successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(sign_up_activity.this, signInActivity.class));

                                    }
                                });


                            } else {
                                Log.d("Auth",task.getException().toString());
                                Toast.makeText(sign_up_activity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Auth",e.toString());
                        }
                    });
                }
                else
                {
                    Toast.makeText(sign_up_activity.this, "Enter proper data", Toast.LENGTH_SHORT).show();
                    binding.editTextTextEmailAddress.setHint("Enter proper email id");
                    binding.editTextTextPassword.setHint("length should be minimum 4");
                }
            }
        });
//        if (firebaseAuth.getCurrentUser()!=null)
//            startActivity(new Intent(MainActivity.this, HomeActivity.class));

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth= FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        setTopBarColor();
    }
    void setTopBarColor() {
        // Check if the device is running on Lollipop or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Get the window object
            Window window = getWindow();
            // Set the status bar color to the colorPrimaryDark defined in your theme
            window.setStatusBarColor(getResources().getColor(R.color.signuptheme, getTheme()));
        }
    }
}