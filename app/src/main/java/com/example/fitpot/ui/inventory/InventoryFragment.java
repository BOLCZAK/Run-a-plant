package com.example.fitpot.ui.inventory;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fitpot.Inventory;
import com.example.fitpot.R;
import com.example.fitpot.SeedType;
import com.example.fitpot.databinding.FragmentInventoryBinding;
import com.google.gson.Gson;

public class InventoryFragment extends Fragment {

    private FragmentInventoryBinding binding;
    private SharedPreferences mPreferences;
    private TextView brownSeed;
    private TextView greenSeed;
    private TextView redSeed;
    private TextView yellowSeed;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInventoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mPreferences = getActivity().getPreferences(MODE_PRIVATE);
        brownSeed = binding.brownSeedText;
        greenSeed = binding.greenSeedText;
        redSeed = binding.redSeedText;
        yellowSeed = binding.yellowSeedText;
        setText();

        return root;
    }

    private Inventory getFromShared(String s){
        Inventory inv;
        Gson gson = new Gson();
        String json = mPreferences.getString(getString(R.string.key_inventory), "");
        inv = gson.fromJson(json, Inventory.class);
        return inv;
    }

    private void setText()
    {
        Inventory inventory = getFromShared(getString(R.string.key_inventory));
        int brownSeedNum = inventory.getSeedNumber(SeedType.brown);
        int greenSeedNum = inventory.getSeedNumber(SeedType.green);
        int redSeedNum = inventory.getSeedNumber(SeedType.red);
        int yellowSeedNum = inventory.getSeedNumber(SeedType.yellow);
        String brownSeedText = "";
        String greenSeedText = "";
        String redSeedText = "";
        String yellowSeedText = "";
        if(brownSeedNum == 1) {
            brownSeedText = brownSeedNum+" "+getString(R.string.brownSeed);
        }
        else
        {
            brownSeedText = brownSeedNum+" "+getString(R.string.brownSeeds);
        }

        if(greenSeedNum == 1) {
            greenSeedText = greenSeedNum+" "+getString(R.string.greenSeed);
        }
        else
        {
            greenSeedText = greenSeedNum+" "+getString(R.string.greenSeeds);
        }

        if(redSeedNum == 1) {
            redSeedText = redSeedNum+" "+getString(R.string.redSeed);
        }
        else
        {
            redSeedText = redSeedNum+" "+getString(R.string.redSeeds);
        }

        if(yellowSeedNum == 1) {
            yellowSeedText = yellowSeedNum+" "+getString(R.string.yellowSeed);
        }
        else
        {
            yellowSeedText = yellowSeedNum+" "+getString(R.string.yellowSeeds);
        }

        brownSeed.setText(brownSeedText);
        greenSeed.setText(greenSeedText);
        redSeed.setText(redSeedText);
        yellowSeed.setText(yellowSeedText);
    }

    @Override
    public void onResume() {
        super.onResume();
        setText();
    }
}
