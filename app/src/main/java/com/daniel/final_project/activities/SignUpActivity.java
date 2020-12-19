package com.daniel.final_project.activities;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

        import androidx.appcompat.app.AppCompatActivity;

        import com.daniel.final_project.R;

public class SignUpActivity extends AppCompatActivity {
    Button login_BTN_sing_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViews();
        initViews();
    }

    private void findViews() {
        login_BTN_sing_in = findViewById(R.id.sign_up_BTN_sign_in);
    }

    private void initViews() {
        login_BTN_sing_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}