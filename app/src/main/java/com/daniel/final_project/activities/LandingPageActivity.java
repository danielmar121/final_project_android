package com.daniel.final_project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daniel.final_project.R;
import com.daniel.final_project.activities.buyer.BuyerMainActivity;
import com.daniel.final_project.activities.supplier.SupplierMainActivity;
import com.daniel.final_project.interfaces.LandingPageCallBack;
import com.daniel.final_project.services.MyFireBase;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LandingPageActivity extends AppCompatActivity {
    private final int RC_SIGN_IN = 1234;
    MyFireBase myFireBase;


    private LandingPageCallBack landingPageCallBack = new LandingPageCallBack() {
        @Override
        public void openMainActivity(Boolean isSupplier) {
            Intent myIntent;

            if (isSupplier) {
                myIntent = new Intent(getApplicationContext(), SupplierMainActivity.class);
            } else {
                myIntent = new Intent(getApplicationContext(), BuyerMainActivity.class);
            }

            startActivity(myIntent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        myFireBase = MyFireBase.getInstance();
        myFireBase.setLandingPageCallBack(landingPageCallBack);
        FirebaseUser firebaseUser = myFireBase.getFirebaseUser();


        if (firebaseUser != null) {
            myFireBase.logInExistingUser();
        } else {
            startLoginMethod();
        }
    }

    private void startLoginMethod() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
                                new AuthUI.IdpConfig.PhoneBuilder().build()
                        ))
                        .setLogo(R.drawable.vegetables)
                        .setTosAndPrivacyPolicyUrls(
                                "https://example.com/terms.html",
                                "https://example.com/privacy.html")
                        .setTheme(R.style.OrangeTheme)
                        .build(),
                RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                myFireBase.logInExistingUser();
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackBar(R.string.sign_in_cancelled);
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackBar(R.string.no_internet_connection);
                    return;
                }

                showSnackBar(R.string.unknown_error);
                Log.e("pttt", "Sign-in error: ", response.getError());
            }
        }
    }

    private void showSnackBar(int id) {
        Toast.makeText(this, getText(id), Toast.LENGTH_SHORT).show();
    }
}