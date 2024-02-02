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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class signInActivity extends AppCompatActivity {
    ActivitySignInBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;

    GoogleSignInClient mG;
    int RID = 20;

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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                                .build();

        mG = GoogleSignIn.getClient(this,gso);

        binding.google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = mG.getSignInIntent();
                startActivityForResult(i,RID);
            }
        });

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
        if( auth.getCurrentUser()==null)
            Log.d("user","NoUser");
        if (auth.getCurrentUser()!=null) {
            startActivity(new Intent(signInActivity.this, MainActivity.class));
            finish();
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(getApplicationContext(),"goes in this",Toast.LENGTH_SHORT).show();
        if(requestCode == RID){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
               // Log.d("here",account.toString());
                //Log.d("here", String.valueOf(task));
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                //Log.d("here", String.valueOf(credential));
                auth.signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                               // Log.d("here", String.valueOf(task));
                                if(task.isSuccessful()){
                                    //Log.d("here", String.valueOf(credential));
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);

                                }else {
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            } catch (ApiException e) {
                e.printStackTrace();
            }

        }
        else {
            Toast.makeText(getApplicationContext(),"goes in this",Toast.LENGTH_SHORT).show();
        }
    }
}