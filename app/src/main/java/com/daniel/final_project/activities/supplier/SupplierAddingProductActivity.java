package com.daniel.final_project.activities.supplier;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.daniel.final_project.R;
import com.daniel.final_project.objects.Product;

public class SupplierAddingProductActivity extends AppCompatActivity {
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_adding_product);
//        findViews();
//        initViews();
    }

//    private void findViews() {
//        login_BTN_sign_in = findViewById(R.id.sign_up_BTN_sign_in);
//    }
//
//    private void initViews() {
//       login_BTN_sign_in.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addProduct();
//            }
//        });
//    }
//
//    private void addProduct() {
//        Product product = new Product()
//                .setName("Adidas")
//                .setCategory("Shoes")
//                .setDescription("white with red stripes")
//                .setPid("P001")
//                .setPrice(250.0)
//                .setQuantity(10)
//                .setSid("S001");
//    }
}