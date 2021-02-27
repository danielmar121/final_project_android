package com.daniel.final_project.activities.shared;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daniel.final_project.R;
import com.daniel.final_project.activities.buyer.BuyerMainActivity;
import com.daniel.final_project.activities.supplier.SupplierMainActivity;
import com.daniel.final_project.activities.supplier.SupplierSignUpActivity;
import com.daniel.final_project.interfaces.shared.LandingPageCallBack;
import com.daniel.final_project.objects.User;
import com.daniel.final_project.services.MyFireBase;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LandingPageActivity extends AppCompatActivity {
    private final int RC_SIGN_IN = 1234;
    private final int ANIMATION_DURATION = 2000;

    private MyFireBase myFireBase;
    private FirebaseUser firebaseUser;
    private ImageView landing_page_IMG_logo;

    private LandingPageCallBack landingPageCallBack = new LandingPageCallBack() {
        @Override
        public void openMainActivity(User user) {
            Intent mainActivityIntent;

            if (user.getSupplier()) {
                if (user.isSignUp()) {
                    mainActivityIntent = new Intent(getApplicationContext(), SupplierMainActivity.class);
                } else {
                    mainActivityIntent = new Intent(getApplicationContext(), SupplierSignUpActivity.class);
                }
            } else {
                mainActivityIntent = new Intent(getApplicationContext(), BuyerMainActivity.class);
            }

            startActivity(mainActivityIntent);
            finish();
        }

        @Override
        public void openSignUpFlow() {
            Intent signUpIntent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(signUpIntent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        myFireBase = MyFireBase.getInstance();
        firebaseUser = myFireBase.getFirebaseUser();

        findView();

        startApplication();
        startAnimation();
    }

    private void findView() {
        landing_page_IMG_logo = findViewById(R.id.landing_page_IMG_logo);

    }

    private void startAnimation() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        View view = landing_page_IMG_logo;

        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.setAlpha(0.0f);
        view.animate()
                .alpha(1.0f)
                .scaleY(1.0f)
                .scaleX(1.0f)
                .rotation(360)
                .translationY(0)
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });
    }

    private void startApplication() {
        if (firebaseUser != null) {
            myFireBase.logInExistingUser(landingPageCallBack);
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
                myFireBase.logInExistingUser(landingPageCallBack);
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
                Log.d("LandingPageActivity", "Sign-in error: ", response.getError());
            }
        }
    }

    private void showSnackBar(int id) {
        Toast.makeText(this, getText(id), Toast.LENGTH_SHORT).show();
    }
}