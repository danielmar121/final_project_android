package com.daniel.final_project.interfaces;

import com.daniel.final_project.objects.User;

public interface LandingPageCallBack {
    void openMainActivity(User user);

    void openSignUpFlow();
}
