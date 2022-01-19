package com.example.fitpot.ui.shop;

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

import com.example.fitpot.StepCounter;
import com.example.fitpot.databinding.FragmentShopBinding;

public class ShopFragment extends Fragment {

    private ShopViewModel shopViewModel;
    private FragmentShopBinding binding;
    private TextView textView;
    private StepCounter stepCounter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shopViewModel =
                new ViewModelProvider(this).get(ShopViewModel.class);
        binding = FragmentShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        stepCounter = new StepCounter(getContext());
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
        stepCounter.setListener(new StepCounter.Listener() {
            @Override
            public void onStep(float steps) {
                //System.out.println(steps);
                //Toast.makeText(getContext(), String.valueOf())
                textView.setText(String.valueOf(steps));
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
        stepCounter.register();
    }

    @Override
    public void onPause(){
        super.onPause();
    }
}