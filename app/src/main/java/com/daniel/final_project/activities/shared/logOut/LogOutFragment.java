package com.daniel.final_project.activities.shared.logOut;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.daniel.final_project.activities.shared.LandingPageActivity;
import com.daniel.final_project.services.MyFireBase;

public class LogOutFragment extends Fragment {
    private MyFireBase myFireBase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myFireBase = MyFireBase.getInstance();
        myFireBase.logOut();

        getActivity().finish();
        Intent landingPageIntent = new Intent(getContext(), LandingPageActivity.class);
        startActivity(landingPageIntent);
        return null;
    }
}