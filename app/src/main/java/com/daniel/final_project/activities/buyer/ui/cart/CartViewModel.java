package com.daniel.final_project.activities.buyer.ui.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CartViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CartViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is cart fragment - buyer");
    }

    public LiveData<String> getText() {
        return mText;
    }
}