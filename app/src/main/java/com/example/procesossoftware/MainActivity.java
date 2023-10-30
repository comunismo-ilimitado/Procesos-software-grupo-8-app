package com.example.procesossoftware;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setScreen1();



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction(); // Creamos una nueva transacción para cada reemplazo
            if (item.getItemId() == R.id.action_screen1) {

                setScreen1();
                return true;
            } else if (item.getItemId() == R.id.action_screen2) {

                setScreen2();
                return true;
            } else if (item.getItemId() == R.id.action_screen3) {
                setScreen3();
                return true;
            }

            return false;
        });
    }

    private void setScreen2() {
        Fragment_s2 fragment = new Fragment_s2();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .commit();
    }

    private void setScreen1() {
        Fragment_s1 fragment = new Fragment_s1();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .commit();

    }
    private void setScreen3() {
        Fragment_s3 fragment = new Fragment_s3();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .commit();

    }
    private void setScreen4() {
        Fragment_s4 fragment = new Fragment_s4();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .commit();

    }

}
