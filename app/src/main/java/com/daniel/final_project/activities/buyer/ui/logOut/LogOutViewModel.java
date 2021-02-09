package com.daniel.final_project.activities.buyer.ui.logOut;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LogOutViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LogOutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Log Out fragment - buyer");
    }

    public LiveData<String> getText() {
        return mText;
    }
}