package com.example.fitpot.ui.inventory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitpot.databinding.FragmentHomeBinding;
import com.example.fitpot.databinding.FragmentInventoryBinding;
import com.example.fitpot.ui.home.HomeViewModel;

public class InventoryFragment extends Fragment {

    private FragmentInventoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInventoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }
}
