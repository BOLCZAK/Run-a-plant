package com.example.fitpot.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
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

import com.example.fitpot.MainActivity;
import com.example.fitpot.R;
import com.example.fitpot.databinding.FragmentHomeBinding;
import com.google.gson.Gson;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private TextView textView;
    private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener;
    private SharedPreferences mPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mPreferences = getActivity().getPreferences(MODE_PRIVATE);

        textView = binding.textView;

        sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                if(s != null)
                {
                    if(s.equals(getString(R.string.key_step_count)))
                    {
                        String msg = String.valueOf(getFromShared()) + " " + getString(R.string.home_textview_text);
                        textView.setText(msg);
                    }
                }
            }
        };

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                String msg = String.valueOf(getFromShared()) + s;
                textView.setText(msg);
            }
        });
        return root;
    }

    int getFromShared(){
        return mPreferences.getInt("stepCount", 0);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final int[] stage = {0};

        view.findViewById(R.id.button_water_plant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int steps = getFromShared();
                MainActivity mainActivity = (MainActivity) getActivity();
                if ( steps >= 500)
                {
                    mainActivity.setSteps(steps - 500);
                    ImageView img = view.findViewById(R.id.imageView);
                    stage[0]++;
                    if (stage[0] > 4) stage[0] = 0;
                    if (stage[0] == 0) img.setImageResource(R.drawable.ic_plant_1);
                    if (stage[0] == 1) img.setImageResource(R.drawable.ic_plant_2);
                    if (stage[0] == 2) img.setImageResource(R.drawable.ic_plant_3);
                    if (stage[0] == 3) img.setImageResource(R.drawable.ic_plant_4);
                    if (stage[0] == 4) img.setImageResource(R.drawable.ic_plant_5);
                }
                else
                {
                    Toast.makeText(getActivity(), getString(R.string.no_money), Toast.LENGTH_SHORT).show();
                }

            }
        });


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

    public void registerPrefListener(SharedPreferences.OnSharedPreferenceChangeListener listener)
    {
        mPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterPrefListener(SharedPreferences.OnSharedPreferenceChangeListener listener)
    {
        mPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

}