package com.example.fitpot;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.fitpot.ui.map.MapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.fitpot.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Gyroscope gyroscope;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gyroscope = new Gyroscope(this);

        gyroscope.setListener(new Gyroscope.Listener() {

            @Override
            public void onRotation(float rx, float ry, float rz) {
                           /*
                if(rz > 1.0f){
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
                else if(rz < -1.0f)
                {
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                }
                            */
            }


        });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_map)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    protected void onResume(){
        super.onResume();
        gyroscope.register();
    }

    @Override
    protected void onPause(){
        super.onPause();
        gyroscope.unregister();
    }


}