package com.example.fooddelivery;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuUtama extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuutama);
        getFragmentPage(new home());
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home2:
                        getFragmentPage(new home());
                        return true;
                    case R.id.checkout:
                        getFragmentPage(new checkout());
                        return  true;
                    case R.id.account:
                        getFragmentPage(new account());
                        return true;

                }
                return false;
            }
        });
    }
    private void getFragmentPage(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
