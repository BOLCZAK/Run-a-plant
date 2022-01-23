package com.example.fitpot.ui.shop;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shopViewModel =
                new ViewModelProvider(this).get(ShopViewModel.class);
        binding = FragmentShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                Toast.makeText(getActivity(), "f", Toast.LENGTH_SHORT);
                if(s.equals(getString(R.string.shared_prefs_key))){
                    int stepCount = getFromShared();
                    textView.setText(String.valueOf(stepCount));
                }
            }
        };
        //final TextView textView = binding.textShop;
        textView = binding.tvStepsTaken;
        shopViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                int stepCount = getFromShared();
                textView.setText(String.valueOf(stepCount));
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

        registerSharedPrefsListener();
    }

    @Override
    public void onPause(){
        super.onPause();
        unregisterSharedPrefsListener();
    }

    int getFromShared(){
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPreferences.getInt("stepCount", 0);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    void registerSharedPrefsListener(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    void unregisterSharedPrefsListener(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }
}