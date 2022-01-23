package com.example.fitpot.ui.shop;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitpot.Accelerometer;
import com.example.fitpot.R;
import com.example.fitpot.StepCounter;
import com.example.fitpot.databinding.FragmentShopBinding;

public class ShopFragment extends Fragment {

    private ShopViewModel shopViewModel;
    private FragmentShopBinding binding;
    private TextView textView;
    private TextView water_max;
    private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;
    ImageView img;
    private SharedPreferences mPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shopViewModel =
                new ViewModelProvider(this).get(ShopViewModel.class);
        binding = FragmentShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mPreferences = getActivity().getPreferences(MODE_PRIVATE);
        //final TextView textView = binding.textShop;
        textView = binding.tvStepsTaken;
        water_max = binding.tvWaterMax;
        img = (ImageView) binding.fillCircle;
        ClipDrawable clipDrawable =  (ClipDrawable) img.getDrawable();

        sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if(s.equals(getString(R.string.key_step_count)))
                {
                    int stepCount = getFromShared(getString(R.string.key_step_count));
                    int water_tank = getFromShared(getString(R.string.key_water_tank));
                    textView.setText(String.valueOf(stepCount));
                    water_max.setText(String.valueOf(water_tank));
                    float prc = (float) stepCount / (float) water_tank;
                    prc *= 10;
                    prc *= 1000;
                    clipDrawable.setLevel((int)prc);
                }
            }
        };
        shopViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                int stepCount = getFromShared(getString(R.string.key_step_count));
                int water_tank = getFromShared(getString(R.string.key_water_tank));
                textView.setText(String.valueOf(stepCount));
                water_max.setText(String.valueOf(water_tank));
                float prc = (float) stepCount / (float) water_tank;
                prc *= 100;
                prc *= 1000;
                clipDrawable.setLevel((int)prc);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume(){
        super.onResume();
        registerPrefListener(sharedPreferenceChangeListener);
    }

    @Override
    public void onPause(){
        super.onPause();
        unregisterPrefListener(sharedPreferenceChangeListener);
    }

    int getFromShared(String s){
        return mPreferences.getInt(s, 0);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    public void registerPrefListener(SharedPreferences.OnSharedPreferenceChangeListener listener)
    {
        mPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterPrefListener(SharedPreferences.OnSharedPreferenceChangeListener listener)
    {
        mPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

}