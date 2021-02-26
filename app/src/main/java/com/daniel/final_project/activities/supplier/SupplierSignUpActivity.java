package com.daniel.final_project.activities.supplier;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.daniel.final_project.R;
import com.daniel.final_project.interfaces.supplier.SupplierURLCallBack;
import com.daniel.final_project.objects.Shop;
import com.daniel.final_project.services.MyFireBase;
import com.daniel.final_project.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.UUID;

public class SupplierSignUpActivity extends AppCompatActivity {
    private Uri squarePhotoUri, widePhotoUri;
    private Shop shop;
    private MyFireBase myFireBase;
    private Button supplier_sign_up_BTN_upload_photo, supplier_sign_up_BTN_open_store;
    private EditText supplier_sign_up_ETXT_name, supplier_sign_up_ETXT_address, supplier_sign_up_ETXT_description;
    private Spinner supplier_sign_up_SPN_type;
    private ArrayAdapter<CharSequence> spinnerTypeAdapter;
    private Boolean photoUploaded = false;

    private SupplierURLCallBack ImageUrlSquareCallBack = new SupplierURLCallBack() {
        @Override
        public void passPhotoURL(String photoURL) {
            if (photoURL.isEmpty() || photoURL == null) {
                Log.d("SupplierSignUpActivity", "Failed to pass photoURL");
                Toast.makeText(SupplierSignUpActivity.this, "Photo URL is invalid", Toast.LENGTH_SHORT).show();
            } else {
                shop.setImageUrlSquare(photoURL);
                insertShop();
            }
        }
    };

    private SupplierURLCallBack ImageUrlWideCallBack = new SupplierURLCallBack() {
        @Override
        public void passPhotoURL(String photoURL) {
            if (photoURL.isEmpty() || photoURL == null) {
                Log.d("SupplierSignUpActivity", "Failed to pass photoURL");
                Toast.makeText(SupplierSignUpActivity.this, "Photo URL is invalid", Toast.LENGTH_SHORT).show();
            } else {
                shop.setImageUrlWide(photoURL);
                myFireBase.savePhoto(ImageUrlSquareCallBack, squarePhotoUri, "shops");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_sign_up);

        Spinner spinner = (Spinner) findViewById(R.id.supplier_sign_up_SPN_type);
        spinnerTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.supplier_types, android.R.layout.simple_spinner_item);
        spinnerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerTypeAdapter);

        myFireBase = MyFireBase.getInstance();

        Gson gson = new Gson();
        String shopJson = getIntent().getStringExtra(Constants.SHOP);
        shop = gson.fromJson(shopJson, Shop.class);

        findViews();
        initViews();
    }

    private void findViews() {
        supplier_sign_up_BTN_upload_photo = findViewById(R.id.supplier_sign_up_BTN_upload_photo);
        supplier_sign_up_BTN_open_store = findViewById(R.id.supplier_sign_up_BTN_open_store);
        supplier_sign_up_ETXT_name = findViewById(R.id.supplier_sign_up_ETXT_name);
        supplier_sign_up_ETXT_address = findViewById(R.id.supplier_sign_up_ETXT_address);
        supplier_sign_up_ETXT_description = findViewById(R.id.supplier_sign_up_ETXT_description);
        supplier_sign_up_SPN_type = findViewById(R.id.supplier_sign_up_SPN_type);
    }

    private void initViews() {
        if (shop != null) {
            supplier_sign_up_ETXT_name.setText(shop.getName());
            supplier_sign_up_ETXT_address.setText(shop.getAddress());
            supplier_sign_up_ETXT_description.setText(shop.getDescription());
            supplier_sign_up_SPN_type.setSelection(spinnerTypeAdapter.getPosition(shop.getType()));
            supplier_sign_up_BTN_open_store.setText(R.string.update_shop);
        } else {
            shop = new Shop().setSid(UUID.randomUUID().toString());
        }

        supplier_sign_up_BTN_open_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoUploaded) {
                    savePhoto();
                } else {
                    insertShop();
                }
                finish();
            }
        });

        supplier_sign_up_BTN_upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });
    }

    private void savePhoto() {
        myFireBase.savePhoto(ImageUrlWideCallBack, widePhotoUri, "shops");
    }

    private void uploadPhoto() {
        //reference to activity
        //LETS THE USER SEE THE PERMISION
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //LETS THE USER CHOSE TO EXEXPT IT OR DENIE IT
            if (ContextCompat.checkSelfPermission(SupplierSignUpActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(SupplierSignUpActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(SupplierSignUpActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                choosePhoto();
            }
        } else {
            choosePhoto();
        }
    }

    private void choosePhoto() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setActivityTitle("Shop Square Photo")
                .start(this);

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(2, 1)
                .setActivityTitle("Shop Wide Photo")
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                if ((result.getCropRect().right - result.getCropRect().left) == (result.getCropRect().bottom - result.getCropRect().top)) {
                    squarePhotoUri = result.getUri();
                } else {
                    widePhotoUri = result.getUri();
                }
                photoUploaded = true;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "CROP IMAGE ERROR", Toast.LENGTH_SHORT).show();
                squarePhotoUri = null;
                widePhotoUri = null;
            }
        }
    }

    private void insertShop() {
        String shopName = supplier_sign_up_ETXT_name.getText().toString();
        String address = supplier_sign_up_ETXT_address.getText().toString();
        String description = supplier_sign_up_ETXT_description.getText().toString();
        String type = supplier_sign_up_SPN_type.getSelectedItem().toString();

        shop.setName(shopName)
                .setType(type)
                .setAddress(address)
                .setDescription(description)
                .setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        myFireBase.updateShop(shop);
        myFireBase.updateUserIsSignUpState(true);
    }
}