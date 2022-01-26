package com.example.fitpot;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.net.Uri;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.fitpot.ui.map.MapFragment;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.fitpot.databinding.ActivityMainBinding;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;

public class MainActivity extends AppCompatActivity {

    private Gyroscope gyroscope;
    private ActivityMainBinding binding;
    private Accelerometer accelerometer;
    private double MagnitudePrevious = 0;
    private Integer stepCount = 0;
    private int water_tank = 1000;
    private SharedPreferences mPreferences;
    //private String sharedPrefFile = "com.example.android.hellosharedprefs";

    private Button signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gyroscope = new Gyroscope(this);
        accelerometer = new Accelerometer(this);
        mPreferences = getPreferences(MODE_PRIVATE);
        getFromShared();
        //water_tank = 1000;
        addToShared();
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
                if (MagnitudeDelta > 1 && stepCount < water_tank){
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

        // checks who's signed=in
        checkCurrentUser();
        signInBtn = (Button) findViewById(R.id.button2);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignInGoogleActivity();
            }
        });
    }

    private void openSignInGoogleActivity() {
        Intent intentGoogle = new Intent(this, SignInActivity.class);
        startActivity(intentGoogle);
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
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.clear();
        editor.putInt(getString(R.string.key_step_count), stepCount);
        editor.putInt(getString(R.string.key_water_tank), water_tank);
        editor.apply();
    }

    void getFromShared(){
        stepCount = mPreferences.getInt(getString(R.string.key_step_count), 0);
        water_tank = mPreferences.getInt(getString(R.string.key_water_tank), 1000);
    }

    public void showToast(String message){
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    public boolean buyFromShop(String message, int amount){
        getFromShared();
        if(stepCount > amount){
            stepCount -= amount;
            addToShared();
            showToast(message);
            return true;
        }
        else
        {
            showToast(getString(R.string.no_money));
            return false;
        }
    }

    public void buyWaterUpgrade(View view){
        if(buyFromShop(getString(R.string.purchase_1), 50))
        {
            water_tank += 500;
        }
        addToShared();
    }

    public void buyPlantUpgrade(View view){
        buyFromShop(getString(R.string.purchase_2), 500);
    }

    public void buyRunningUpgrade(View view){
        buyFromShop(getString(R.string.purchase_3), 1000);
    }

    // Firebase & Google Sign In
    public void checkCurrentUser() {
        // [START check_current_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
        } else {
            // No user is signed in
        }
        // [END check_current_user]
    }


}