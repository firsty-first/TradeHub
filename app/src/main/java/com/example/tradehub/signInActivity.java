package com.example.tradehub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.tradehub.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signInActivity extends BaseActivity {
    ActivitySignInBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignInBinding.inflate(getLayoutInflater());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());
        progressDialog=new ProgressDialog(signInActivity.this);
        progressDialog.setTitle("Loging in");
        progressDialog.setMessage("wait while we get you there");
        auth=FirebaseAuth.getInstance();


        binding.newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signInActivity.this, sign_up_activity.class));

            }
        });
        binding.btnSignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(binding.editTextEmail.getText().toString().length()<10 || binding.editTextPassword.getText().toString().length()<5))
                {

                    progressDialog.show();
                    auth.signInWithEmailAndPassword(binding.editTextEmail.getText().toString(), binding.editTextPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.hide();
                                    if (task.isSuccessful()) {
                              //          Log.d("Auth",task.getException().toString());
                                        Intent intent = new Intent(signInActivity.this,
                                                MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(signInActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        Log.d("Auth",task.getException().toString());
                                    }
                                }
                            });
                }
                else
                {
                    binding.editTextEmail.setHint("please enter email id");
                    binding.editTextPassword.setHint("Password is too short");
                    // binding.editTextTextPassword.setHintTextColor(getResources().getColor(color.(R.color.errorHint)));//res.getColor(R.color.green)
                }
            }
        });
        if(  auth.getCurrentUser()==null)
            Log.d("user","NoUser");
        if (auth.getCurrentUser()!=null) {
            startActivity(new Intent(signInActivity.this, MainActivity.class));
            finish();
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //auth=FirebaseAuth.getInstance();
    }
    @Override
    protected void onStart() {
        super.onStart();
       // NotificationUtils.createNotificationChannel(this);
        Log.d("life activity","start  homescreen");
        setTopBarColor();
    }
    void setTopBarColor() {
        // Check if the device is running on Lollipop or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Get the window object
            Window window = getWindow();

            // Set the status bar color to the colorPrimaryDark defined in your theme
            window.setStatusBarColor(getResources().getColor(R.color.lavender, getTheme()));
        }
    }


}