package com.daniel.final_project.interfaces.shared;

import com.daniel.final_project.objects.User;

public interface LandingPageCallBack {
    void openMainActivity(User user);

    void openSignUpFlow();
}
