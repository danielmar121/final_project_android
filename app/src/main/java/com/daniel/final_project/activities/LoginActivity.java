package com.daniel.final_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.daniel.final_project.R;

public class LoginActivity extends AppCompatActivity {
    Button login_BTN_sing_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        initViews();
    }

    private void findViews() {
        login_BTN_sing_up = findViewById(R.id.login_BTN_sign_up);
    }

    private void initViews() {
        login_BTN_sing_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
}