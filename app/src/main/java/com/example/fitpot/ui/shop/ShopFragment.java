package com.example.fitpot.ui.shop;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.fitpot.StepCounter;
import com.example.fitpot.databinding.FragmentShopBinding;

public class ShopFragment extends Fragment {

    private ShopViewModel shopViewModel;
    private FragmentShopBinding binding;
    private TextView textView;
    private Accelerometer accelerometer;
    private double MagnitudePrevious = 0;
    private Integer stepCount = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shopViewModel =
                new ViewModelProvider(this).get(ShopViewModel.class);
        binding = FragmentShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        accelerometer = new Accelerometer(getContext());
        //final TextView textView = binding.textShop;
        textView = binding.tvStepsTaken;
        /*
        shopViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */
        textView.setText(String.valueOf(stepCount));

        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void onTranslation(float tx, float ty, float tz) {
                double Magnitude = Math.sqrt(tx*tx + ty*ty + tz*tz);
                double MagnitudeDelta = Magnitude - MagnitudePrevious;
                MagnitudePrevious = Magnitude;

                if (MagnitudeDelta > 4){
                    stepCount++;
                }
                textView.setText(stepCount.toString());
            }
        });
        accelerometer.register();
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

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepCount", 0);
    }

    @Override
    public void onPause(){
        super.onPause();

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepCount", stepCount);
        editor.apply();
    }
}