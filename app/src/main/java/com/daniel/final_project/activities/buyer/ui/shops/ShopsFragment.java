package com.daniel.final_project.activities.buyer.ui.shops;

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

import com.daniel.final_project.R;

public class ShopsFragment extends Fragment {

    private ShopsViewModel shopsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shopsViewModel =
                new ViewModelProvider(this).get(ShopsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shops_buyer, container, false);
        final TextView textView = root.findViewById(R.id.buyer_shops_TXT_shops);
        shopsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}