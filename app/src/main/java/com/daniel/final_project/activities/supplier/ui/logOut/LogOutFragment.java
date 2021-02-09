package com.daniel.final_project.activities.supplier.ui.logOut;

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

public class LogOutFragment extends Fragment {

    private LogOutViewModel logOutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        logOutViewModel =
                new ViewModelProvider(this).get(LogOutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_log_out_supplier, container, false);
        final TextView textView = root.findViewById(R.id.supplier_log_out_TXT_log_out);
        logOutViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}