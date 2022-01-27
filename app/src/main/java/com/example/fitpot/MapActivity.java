package com.example.fitpot;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.gms.location.FusedLocationProviderClient;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MapActivity extends AppCompatActivity
        implements
        ActivityCompat.OnRequestPermissionsResultCallback,
        OnMapReadyCallback{

    private static final int DEFAULT_ZOOM = 15;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String LAST_LOCATION = "last_location";
    private static final String SEED = "seed";
    private static final String SEED_POSITION = "seed_position";
    private boolean locationPermissionGranted;
    private boolean permissionDenied = false;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap map;

    private Location lastKnownLocation;

    private Bundle bundle;

    private Random random = new Random();
    private Seed seed;
    private SeedType seedType;
    private LatLng seedPosition = null;
    double seedRadius = 1000;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getBundleExtra("mapBundle");
        if(bundle.getParcelable(SEED) != null) {
            seed = bundle.getParcelable(SEED);
            seedType = seed.getType();
        }
        if(bundle.getParcelable(LAST_LOCATION) != null) {
            lastKnownLocation = bundle.getParcelable(LAST_LOCATION);
        }
        if(bundle.getParcelable(SEED_POSITION) != null) {
            seedPosition = bundle.getParcelable(SEED_POSITION);
        }
        setContentView(R.layout.fragment_map);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        enableMyLocation();
        getDeviceLocation();
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                map.setMyLocationEnabled(true);
                locationPermissionGranted = true;
                permissionDenied = false;

            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
            locationPermissionGranted = false;
            permissionDenied = true;
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                            setSeed();
                        }
                    }
                });
            }
    }

    private void setSeed(){
        if(lastKnownLocation != null && seed == null && seedPosition == null)
        {
            double radiusInDegrees = seedRadius / 111000f;

            double u = random.nextDouble();
            double v = random.nextDouble();
            double w = radiusInDegrees * Math.sqrt(u);
            double t = 2 * Math.PI * v;
            double x = w * Math.cos(t);
            double y = w * Math.sin(t);

            double new_x = x / Math.cos(Math.toRadians(lastKnownLocation.getLatitude()));

            double foundLongitude = new_x + lastKnownLocation.getLongitude();
            double foundLatitude = y + lastKnownLocation.getLatitude();

            seedPosition = new LatLng(foundLatitude,
                    foundLongitude);
            seedType = randomSeed();
            String seedName = seedType.toString();
            map.addMarker(
                    new MarkerOptions()
                            .position(seedPosition)
                            .title(seedName));
            seed = new Seed(seedType);

            bundle.putParcelable(SEED, seed);
            bundle.putParcelable(LAST_LOCATION, lastKnownLocation);
            bundle.putParcelable(SEED_POSITION, seedPosition);
        }
        else if(seedPosition != null && seed != null ){
            String seedName = seedType.toString();
            map.addMarker(
                new MarkerOptions()
                        .position(seedPosition)
                        .title(seedName));
        }
    }

    private SeedType randomSeed()
    {
        List<SeedType> seeds =
            Collections.unmodifiableList(Arrays.asList(SeedType.values()));
        int size = seeds.size();
        return seeds.get(random.nextInt(size));
    }

    protected void saveBundle() {
        if (seed != null && seedPosition != null && lastKnownLocation != null) {
            getIntent().getBundleExtra("mapBundle").putParcelable(SEED, seed);
            getIntent().getBundleExtra("mapBundle").putParcelable(LAST_LOCATION, lastKnownLocation);
            getIntent().getBundleExtra("mapBundle").putParcelable(SEED_POSITION, seedPosition);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
            locationPermissionGranted = true;
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
            locationPermissionGranted = false;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        } return true;
    }

    @Override
    public void onBackPressed() {
        saveBundle();
        finish();
    }
    @Override
    public void finish(){
        Intent data = new Intent();
        data.putExtra("mapBundle", bundle);
        setResult(Activity.RESULT_OK, data);
        super.finish();
    }

}
