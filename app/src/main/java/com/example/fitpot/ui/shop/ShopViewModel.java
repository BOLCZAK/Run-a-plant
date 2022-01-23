package com.example.fitpot.ui.shop;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShopViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private SensorManager sensorManager;
    boolean running = false;

    public ShopViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(String.valueOf(5));
    }

    public LiveData<String> getText() {
        return mText;
    }

}