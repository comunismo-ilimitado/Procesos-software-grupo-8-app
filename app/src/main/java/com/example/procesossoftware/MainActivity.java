package com.example.procesossoftware;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_screen1) {
                startActivity(new Intent(MainActivity.this, ScreenActivity1.class));
                return true;
            } else if (item.getItemId() == R.id.action_screen2) {
                startActivity(new Intent(MainActivity.this, ScreenActivity2.class));
                return true;
            } else if (item.getItemId() == R.id.action_screen3) {
                startActivity(new Intent(MainActivity.this, ScreenActivity3.class));
                return true;
            }
            return false;
        });
    }

}
