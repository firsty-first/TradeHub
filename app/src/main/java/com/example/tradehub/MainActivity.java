package com.example.tradehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.tradehub.databinding.ActivityMain2Binding;
import com.example.tradehub.databinding.ActivityMainBinding;
import com.example.tradehub.nav_screen_fragments.Cart_Fragment;
import com.example.tradehub.nav_screen_fragments.Home_Fragment;
import com.example.tradehub.nav_screen_fragments.Profile_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity {
ActivityMain2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replacefragment(new Home_Fragment());

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.home){
                        replacefragment(new Home_Fragment());
                    }
                if (item.getItemId() == R.id.profile) {
                        replacefragment(new Profile_Fragment());
                    }
                if (item.getItemId() == R.id.cart){
                        replacefragment(new Cart_Fragment());
                    }
                return false;
            }
        });

    }
    private void replacefragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}