package com.example.procesossoftware;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 4;
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setScreen1();

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction(); // Creamos una nueva transacción para cada reemplazo
            if (item.getItemId() == R.id.action_screen1) {
                pagerAdapter.createFragment(1);
                return true;
            } else if (item.getItemId() == R.id.action_screen2) {
                pagerAdapter.createFragment(2);
                return true;
            } else if (item.getItemId() == R.id.action_screen3) {
                pagerAdapter.createFragment(3);
                return true;
            }

            return false;
        });

    }

    /*
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

     */

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position==1){
                return new Fragment_s2();
            }
            if (position==2){
                return new Fragment_s3();
            }
            if (position==3){
                return new Fragment_s4();
            }
            else{
                return new Fragment_s1();
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }


}
