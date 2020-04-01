package com.shimadove.coronago;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.home:
                        selectedFragment = HomeFragment.newInstance();
                        break;
                    case R.id.wallet:
                        //todo:: inflate wallet fragment
                        break;
                    case R.id.leader_board:
                        //todo :: inflate leaderboard fragment
                        break;
                    case R.id.profile:
                        selectedFragment = ProfileFragment.newInstance();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.main_content, selectedFragment).commit();
                return true;
            }

        });
    }
}
