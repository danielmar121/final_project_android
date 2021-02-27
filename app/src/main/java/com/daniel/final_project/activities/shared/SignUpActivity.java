package com.daniel.final_project.activities.shared;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daniel.final_project.R;
import com.daniel.final_project.activities.supplier.SupplierSignUpActivity;
import com.daniel.final_project.objects.User;
import com.daniel.final_project.services.MyFireBase;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private MyFireBase myFireBase;
    private FirebaseUser firebaseUser;
    private Button sign_up_BTN_sign_up;
    private EditText sign_up_ETXT_first_name, sign_up_ETXT_last_name, sign_up_ETXT_email, sign_up_ETXT_phone;
    private CheckBox sign_up_CKB_supplier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        myFireBase = MyFireBase.getInstance();
        firebaseUser = myFireBase.getFirebaseUser();

        findViews();
        initViews();
    }

    private void findViews() {
        sign_up_BTN_sign_up = findViewById(R.id.sign_up_BTN_sign_up);
        sign_up_ETXT_first_name = findViewById(R.id.sign_up_ETXT_first_name);
        sign_up_ETXT_last_name = findViewById(R.id.sign_up_ETXT_last_name);
        sign_up_ETXT_email = findViewById(R.id.sign_up_ETXT_email);
        sign_up_ETXT_phone = findViewById(R.id.sign_up_ETXT_phone);
        sign_up_CKB_supplier = findViewById(R.id.sign_up_CKB_supplier);
    }

    private void initViews() {
        sign_up_BTN_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserSign();
            }
        });
    }

    private void updateUserSign() {

        User user = getUser();

        if (user == null) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (user.getSupplier() == true) {
            openShop(user.setSignUp(false));
        } else {
            signIn(user.setSignUp(true));
        }
    }

    private User getUser() {
        String firstName = sign_up_ETXT_first_name.getText().toString();
        String lastName = sign_up_ETXT_last_name.getText().toString();
        String email = sign_up_ETXT_email.getText().toString();
        String phoneNumber = sign_up_ETXT_phone.getText().toString();
        Boolean isSupplier = sign_up_CKB_supplier.isChecked();
        String uid = firebaseUser.getUid();

        if (firstName.matches("") || lastName.matches("") || email.matches("") || phoneNumber.matches("")) {
            return null;
        }

        User user = new User()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPhone(phoneNumber)
                .setUid(uid)
                .setEmail(email)
                .setSupplier(isSupplier);

        return user;
    }


    private void signIn(User user) {
        myFireBase.updateUser(user);
        Intent intent = new Intent(this, LandingPageActivity.class);
        startActivity(intent);
        finish();
    }

    private void openShop(User user) {
        myFireBase.updateUser(user);
        Intent intent = new Intent(this, SupplierSignUpActivity.class);
        startActivity(intent);
        finish();
    }
}
