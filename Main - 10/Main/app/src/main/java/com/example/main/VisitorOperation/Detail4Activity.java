package com.example.main.VisitorOperation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.main.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Bundle;
import android.view.MenuItem;

public class Detail4Activity extends AppCompatActivity {

    Home2Fragment h = new Home2Fragment();
    Bill2Fragment b = new Bill2Fragment();
    Info2Fragment i = new Info2Fragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail4);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_container,h).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.home:
                            getSupportFragmentManager().beginTransaction().replace(R.id.bottom_container,h).commit();
                            // Handle item 1 selection
                            return true;
                        case R.id.bill:
                            getSupportFragmentManager().beginTransaction().replace(R.id.bottom_container,b).commit();

                            // Handle item 2 selection
                            return true;
                        case R.id.info:
                            getSupportFragmentManager().beginTransaction().replace(R.id.bottom_container,i).commit();

                            // Handle item 3 selection
                            return true;
                    }
                    return false;
                });
    }
}