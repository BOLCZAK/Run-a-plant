package com.example.fitpot;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fitpot.ui.map.Map;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.fitpot.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private Gyroscope gyroscope;
    private Map map;
    private ActivityMainBinding binding;
    private Accelerometer accelerometer;
    private double MagnitudePrevious = 0;
    private Integer stepCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gyroscope = new Gyroscope(this);
        accelerometer = new Accelerometer(this);
        getFromShared();
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

        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void onTranslation(float tx, float ty, float tz) {
                double Magnitude = Math.sqrt(tx*tx + ty*ty + tz*tz);
                double MagnitudeDelta = Magnitude - MagnitudePrevious;
                MagnitudePrevious = Magnitude;
                if (MagnitudeDelta > 1){
                    stepCount++;
                }
                addToShared();
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
        accelerometer.register();
    }

    @Override
    protected void onPause(){
        super.onPause();
        gyroscope.unregister();
        accelerometer.unregister();
    }

    void addToShared(){
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();
    }

    void getFromShared(){
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepCount", 0);
    }

    public void showToast(String message){
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    public void buyFromShop(String message, int amount){
        getFromShared();
        if(stepCount > amount){
            stepCount -= amount;
            addToShared();
            showToast(message);
        }
        else
        {
            showToast(getString(R.string.no_money));
        }
    }

    public void buyWaterUpgrade(View view){
        buyFromShop(getString(R.string.purchase_1), 50);
    }

    public void buyPlantUpgrade(View view){
        buyFromShop(getString(R.string.purchase_2), 500);
    }

    public void buyRunningUpgrade(View view){
        buyFromShop(getString(R.string.purchase_3), 1000);
    }

}