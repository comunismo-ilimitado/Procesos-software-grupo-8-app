package com.example.procesossoftware;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 3;
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.pager);
        FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_screen1) {
                viewPager.setCurrentItem(0);
                return true;
            } else if (item.getItemId() == R.id.action_screen2) {
                viewPager.setCurrentItem(1);
                return true;
            } else if (item.getItemId() == R.id.action_screen3) {
                viewPager.setCurrentItem(2);
                return true;
            }
            return false;
        });

        // Agrega un callback para detectar cambios de página
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                // Actualiza el estado del botón según la página actual
                updateBottomNavigation(position);
            }
        });
    }

    private void updateBottomNavigation(int position) {
        // Desactiva todos los ítems antes de activar el correspondiente
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            bottomNavigationView.getMenu().getItem(i).setChecked(false);
        }

        // Activa el ítem correspondiente a la página actual
        bottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 1:
                    return new Fragment_s2();
                case 2:
                    return new Fragment_s3();
                default:
                    return new Fragment_s1();
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}

