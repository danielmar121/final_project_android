package com.daniel.final_project.activities.buyer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.daniel.final_project.R;
import com.daniel.final_project.services.MyFireBase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BuyerMainActivity extends AppCompatActivity {
    MyFireBase myFireBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_main);
        BottomNavigationView navView = findViewById(R.id.buyer_main_NAV_bottom);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_log_out_buyer,
                R.id.navigation_shops_buyer, R.id.navigation_cart_buyer, R.id.navigation_profile_buyer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.buyer_main_FRM_activity);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}