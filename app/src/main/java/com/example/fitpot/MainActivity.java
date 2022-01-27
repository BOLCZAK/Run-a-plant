package com.example.fitpot;


import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.fitpot.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Bundle bundle;
    private Gyroscope gyroscope;
    private ActivityMainBinding binding;
    private Accelerometer accelerometer;
    private double MagnitudePrevious = 0;
    private Integer stepCount = 0;
    private int water_tank = 1000;
    private SharedPreferences mPreferences;
    private int MAP_REQ = 1;
    //private String sharedPrefFile = "com.example.android.hellosharedprefs";

    private Button signInBtn;
    private ImageButton mapBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(bundle ==  null) {
            bundle = new Bundle();
        }
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
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_inventory)
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
        mapBtn = (ImageButton) findViewById(R.id.mapButton);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMyLocationActivity();
            }
        });
    }

    private void openSignInGoogleActivity() {
        Intent intentGoogle = new Intent(this, SignInActivity.class);
        startActivity(intentGoogle);
    }

    private void openMyLocationActivity() {
        Intent intentLocation = new Intent(this, MapActivity.class).putExtra("mapBundle", bundle);
        intentLocation.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intentLocation, MAP_REQ);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MAP_REQ && resultCode == RESULT_OK)
             bundle = data.getBundleExtra("mapBundle");
        super.onActivityResult(requestCode, resultCode, data);
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