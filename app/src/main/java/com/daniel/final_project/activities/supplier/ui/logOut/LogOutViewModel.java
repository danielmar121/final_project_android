package com.daniel.final_project.activities.supplier.ui.logOut;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LogOutViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LogOutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Log Out fragment - supplier");
    }

    public LiveData<String> getText() {
        return mText;
    }
}