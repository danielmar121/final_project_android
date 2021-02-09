package com.daniel.final_project.services;

import android.content.Context;
import android.util.Log;

import com.daniel.final_project.interfaces.BuyerShopsCallBack;
import com.daniel.final_project.interfaces.LandingPageCallBack;
import com.daniel.final_project.objects.Order;
import com.daniel.final_project.objects.Product;
import com.daniel.final_project.objects.ProductOrder;
import com.daniel.final_project.objects.Shop;
import com.daniel.final_project.objects.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyFireBase {

    private static MyFireBase instance;
    private FirebaseDatabase database;
    private FirebaseUser firebaseUser;
    private DatabaseReference myRef;
    private LandingPageCallBack landingPageCallBack;

    private MyFireBase(Context context) {
        database = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new MyFireBase(context.getApplicationContext());
        }
    }

    public static MyFireBase getInstance() {
        return instance;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setLandingPageCallBack(LandingPageCallBack landingPageCallBack) {
        this.landingPageCallBack = landingPageCallBack;
    }

    public void logInExistingUser() {
        if (firebaseUser == null) {
            this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        }

        DatabaseReference userRef = this.database.getReference("users").child(firebaseUser.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                landingPageCallBack.openMainActivity(user.getSupplier());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("logInExistingUser", "Failed to read value.", error.toException());
            }
        });
    }

    public void updateUser(User user) {
        this.myRef = this.database.getReference("users");
        myRef.child(user.getUid()).setValue(user);
    }

    public void updateProduct(Product product) {
        this.myRef = this.database.getReference("products");
        myRef.child(product.getPid()).setValue(product);
    }

    public void updateShop(Shop shop) {
        this.myRef = this.database.getReference("shops");
        myRef.child(shop.getSid()).setValue(shop);
    }

    public void updateOrder(Order order) {
        this.myRef = this.database.getReference("orders");
        myRef.child(order.getOid()).setValue(order);
//        Order order = new Order()
//                .setOid("O001")
//                .setUid(firebaseUser.getUid())
//                .setSid("S001");
    }

    public void updateProductOrder(ProductOrder productOrder) {
        this.myRef = this.database.getReference("productOrder");
        myRef.child(productOrder.getProductOrderId()).setValue(productOrder);
//        ProductOrder productOrder = new ProductOrder()
//                .setProductOrderId("PO001")
//                .setOid("O001")
//                .setPid("P001")
//                .setQuantity(2);
    }

    public void getShopsForBuyer(BuyerShopsCallBack buyerShopsCallBack) {
        DatabaseReference userRef = this.database.getReference("shops");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Shop> shops = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Shop shop = snapshot.getValue(Shop.class);
                    shops.add(shop);
                }

                buyerShopsCallBack.putShopsInList(shops);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("logInExistingUser", "Failed to read value.", error.toException());
            }
        });
    }
}
