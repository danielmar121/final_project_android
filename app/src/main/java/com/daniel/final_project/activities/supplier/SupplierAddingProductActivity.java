package com.daniel.final_project.activities.supplier;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.daniel.final_project.R;
import com.daniel.final_project.interfaces.supplier.SupplierURLCallBack;
import com.daniel.final_project.objects.Product;
import com.daniel.final_project.objects.Shop;
import com.daniel.final_project.services.MyFireBase;
import com.daniel.final_project.utils.Constants;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.UUID;

public class SupplierAddingProductActivity extends AppCompatActivity {

    private Uri photoUri;
    private Shop shop;
    private MyFireBase myFireBase;
    private Product product;
    private Button supplier_adding_product_BTN_approve, supplier_adding_product_BTN_cancel, supplier_adding_product_BTN_upload_photo;
    private EditText supplier_adding_product_ETXT_product_name, supplier_adding_product_ETXT_quantity, supplier_adding_product_ETXT_product_description, supplier_adding_product_ETXT_price;
    private Boolean photoUploaded = false;
    private TextView supplier_adding_product_LBL_page_name;

    private SupplierURLCallBack supplierURLCallBack = new SupplierURLCallBack() {
        @Override
        public void passPhotoURL(String photoURL) {
            if (photoURL.isEmpty() || photoURL == null) {
                Log.w("passPhotoURL", "Failed to pass photoURL");
                Toast.makeText(SupplierAddingProductActivity.this, "Photo URL is invalid", Toast.LENGTH_SHORT).show();
            } else {
                product.setImageUrl(photoURL);
                insertProduct();
            }

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_adding_product);
        myFireBase = MyFireBase.getInstance();
        Gson gson = new Gson();
        String productJson = getIntent().getStringExtra(Constants.PRODUCT);
        product = gson.fromJson(productJson, Product.class);


        findViews();
        initViews();
    }

    private void findViews() {
        supplier_adding_product_LBL_page_name = findViewById(R.id.supplier_adding_product_LBL_page_name);
        supplier_adding_product_BTN_approve = findViewById(R.id.supplier_adding_product_BTN_approve);
        supplier_adding_product_BTN_cancel = findViewById(R.id.supplier_adding_product_BTN_cancel);
        supplier_adding_product_BTN_upload_photo = findViewById(R.id.supplier_adding_product_BTN_upload_photo);
        supplier_adding_product_ETXT_product_name = findViewById(R.id.supplier_adding_product_ETXT_product_name);
        supplier_adding_product_ETXT_quantity = findViewById(R.id.supplier_adding_product_ETXT_quantity);
        supplier_adding_product_ETXT_product_description = findViewById(R.id.supplier_adding_product_ETXT_product_description);
        supplier_adding_product_ETXT_price = findViewById(R.id.supplier_adding_product_ETXT_price);
    }

    private void initViews() {
        if (product != null) {
            supplier_adding_product_ETXT_product_name.setText(product.getName());
            supplier_adding_product_ETXT_quantity.setText("" + product.getQuantity());
            supplier_adding_product_ETXT_product_description.setText(product.getDescription());
            supplier_adding_product_ETXT_price.setText("" + product.getPrice());
            supplier_adding_product_LBL_page_name.setText(R.string.edit_product);
        } else {
            String pid = UUID.randomUUID().toString();
            product = new Product()
                    .setPid(pid);
        }

        supplier_adding_product_BTN_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoUploaded) {
                    savePhoto();
                } else {
                    insertProduct();
                }
                finish();
            }
        });

        supplier_adding_product_BTN_upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });

        supplier_adding_product_BTN_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void savePhoto() {
        myFireBase.savePhoto(supplierURLCallBack, photoUri, "products");
    }

    private void uploadPhoto() {
        //reference to activity
        //LETS THE USER SEE THE PERMISION
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //LETS THE USER CHOSE TO EXEXPT IT OR DENIE IT
            if (ContextCompat.checkSelfPermission(SupplierAddingProductActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(SupplierAddingProductActivity.this, "Permision denied", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(SupplierAddingProductActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
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
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                photoUri = result.getUri();
                photoUploaded = true;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "CROP IMAGE ERROR", Toast.LENGTH_SHORT).show();
                photoUri = null;
            }
        }
    }

    private void insertProduct() {
        //TODO: add/remove category
        Gson gson = new Gson();
        String productName = supplier_adding_product_ETXT_product_name.getText().toString();
        int quantity = Integer.parseInt(supplier_adding_product_ETXT_quantity.getText().toString());
        String description = supplier_adding_product_ETXT_product_description.getText().toString();
        double price = Double.parseDouble(supplier_adding_product_ETXT_price.getText().toString());
        String shopJson = getIntent().getStringExtra(Constants.SHOP);
        shop = gson.fromJson(shopJson, Shop.class);
        product.setName(productName)
                //.setCategory("Shoes")
                .setDescription(description)
                .setPrice(price)
                .setQuantity(quantity)
                .setSid(shop.getSid());
        myFireBase.updateProduct(product);
    }

}