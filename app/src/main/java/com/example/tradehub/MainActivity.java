package com.example.tradehub;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tradehub.databinding.ActivityMain2Binding;
import com.example.tradehub.nav_screen_fragments.CommunityFragment;
import com.example.tradehub.nav_screen_fragments.Upload_Fragment;
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
                if (item.getItemId() == R.id.upload){
                    replacefragment(new Upload_Fragment());
                }
                if (item.getItemId() == R.id.home){
                        replacefragment(new Home_Fragment());
                    }
                if (item.getItemId() == R.id.community){
                    replacefragment(new CommunityFragment());
                }
                if (item.getItemId() == R.id.profile) {
                        replacefragment(new Profile_Fragment());
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Forward the result to the fragment
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

}