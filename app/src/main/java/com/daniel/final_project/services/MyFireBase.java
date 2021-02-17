package com.daniel.final_project.services;

import android.content.Context;
import android.util.Log;

import com.daniel.final_project.interfaces.LandingPageCallBack;
import com.daniel.final_project.interfaces.ObjectCallBack;
import com.daniel.final_project.interfaces.ObjectsCallBack;
import com.daniel.final_project.interfaces.UserDetailsCallBack;
import com.daniel.final_project.interfaces.buyer.BuyerOrderCallBack;
import com.daniel.final_project.interfaces.buyer.BuyerProductOrderCallBack;
import com.daniel.final_project.interfaces.buyer.BuyerProductOrderItemCallBack;
import com.daniel.final_project.interfaces.buyer.BuyerShopsCallBack;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyFireBase {

    private static MyFireBase instance;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference myRef;
    private LandingPageCallBack landingPageCallBack;

    private MyFireBase(Context context) {
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
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
    }

    public void updateProductOrder(ProductOrder productOrder) {
        this.myRef = this.database.getReference("productOrder");
        myRef.child(productOrder.getProductOrderId()).setValue(productOrder);
    }

    public void getShopsForBuyer(BuyerShopsCallBack buyerShopsCallBack) {
        DatabaseReference shopsRef = this.database.getReference("shops");

        shopsRef.addValueEventListener(new ValueEventListener() {
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

    public void getProductsForShop(ObjectsCallBack objectsCallBack, String sid) {
        Query productsRef = this.database.getReference("products").orderByChild("sid").equalTo(sid);

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Object> products = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    products.add(product);
                }

                objectsCallBack.sendObjectsToActivity(products);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("logInExistingUser", "Failed to read value.", error.toException());
            }
        });
    }

    public void getOpenOrders(BuyerOrderCallBack buyerOrderCallBack, String uid) {
        Query productsRef = this.database.getReference("orders").orderByChild("uid").equalTo(uid);

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Order> orders = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Order order = snapshot.getValue(Order.class);
                    if (order.getOrderStatus().matches("OPEN"))
                        orders.add(order);
                }

                buyerOrderCallBack.putOrdersInList(orders);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("logInExistingUser", "Failed to read value.", error.toException());
            }
        });
    }

    public void getProduct(BuyerProductOrderItemCallBack buyerProductOrderItemCallBack, String pid) {
        DatabaseReference productRef = this.database.getReference("products").child(pid);

        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                buyerProductOrderItemCallBack.putProductDetailsInItem(product);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("logInExistingUser", "Failed to read value.", error.toException());
            }
        });
    }

    public void getProductOrders(BuyerProductOrderCallBack buyerProductOrderCallBack, String oid) {
        Query productsRef = this.database.getReference("productOrder").orderByChild("oid").equalTo(oid);

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ProductOrder> productOrders = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ProductOrder productOrder = snapshot.getValue(ProductOrder.class);
                    productOrders.add(productOrder);
                }

                buyerProductOrderCallBack.putProductOrdersInList(productOrders);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("logInExistingUser", "Failed to read value.", error.toException());
            }
        });
    }

    public void deleteProductOrder(String productOrderId) {
        DatabaseReference productOrderRef = this.database.getReference("productOrder").child(productOrderId);
        productOrderRef.removeValue();
    }

    public void deleteProduct(String pid) {
        DatabaseReference productRef = this.database.getReference("products").child(pid);
        productRef.removeValue();
    }

    public void updateOrderStatus(String oid, String status) {
        this.database.getReference("orders").child(oid).child("orderStatus").setValue(status);
    }

    public void logOut() {
        auth.signOut();
        firebaseUser = auth.getCurrentUser();
    }

    public void getUser(UserDetailsCallBack userDetailsCallBack) {
        if (firebaseUser == null) {
            this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        }

        DatabaseReference userRef = this.database.getReference("users").child(firebaseUser.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                userDetailsCallBack.passUserDetails(user);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("logInExistingUser", "Failed to read value.", error.toException());
            }
        });
    }

    public void getShop(String uid, ObjectCallBack objectCallBack) {
        Query productsRef = this.database.getReference("shops").orderByChild("uid").equalTo(uid);

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Shop shop = snapshot.getValue(Shop.class);
                    objectCallBack.sendObjectToActivity(shop);
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("logInExistingUser", "Failed to read value.", error.toException());
            }
        });
    }
}
