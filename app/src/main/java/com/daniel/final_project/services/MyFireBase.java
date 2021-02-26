package com.daniel.final_project.services;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.daniel.final_project.interfaces.LandingPageCallBack;
import com.daniel.final_project.interfaces.ObjectCallBack;
import com.daniel.final_project.interfaces.ObjectsCallBack;
import com.daniel.final_project.interfaces.buyer.BuyerProductOrderCallBack;
import com.daniel.final_project.interfaces.buyer.BuyerProductOrderItemCallBack;
import com.daniel.final_project.interfaces.buyer.BuyerShopsCallBack;
import com.daniel.final_project.interfaces.supplier.SupplierURLCallBack;
import com.daniel.final_project.objects.Order;
import com.daniel.final_project.objects.Product;
import com.daniel.final_project.objects.ProductOrder;
import com.daniel.final_project.objects.Shop;
import com.daniel.final_project.objects.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MyFireBase {

    private static MyFireBase instance;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;


    private MyFireBase(Context context) {
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        storage = FirebaseStorage.getInstance();


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

    public void logInExistingUser(LandingPageCallBack landingPageCallBack) {
        if (firebaseUser == null) {
            this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        }

        DatabaseReference userRef = this.database.getReference("users").child(firebaseUser.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    landingPageCallBack.openMainActivity(user);
                } else {
                    landingPageCallBack.openSignUpFlow();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("logInExistingUser", "Failed to read value.", error.toException());
            }
        });
    }

    public void updateUser(User user) {
        DatabaseReference usersRef = this.database.getReference("users");
        usersRef.child(user.getUid()).setValue(user);
    }

    public void updateProduct(Product product) {
        DatabaseReference productsRef = this.database.getReference("products");
        productsRef.child(product.getPid()).setValue(product);
    }

    public void updateShop(Shop shop) {
        DatabaseReference shopsRef = this.database.getReference("shops");
        shopsRef.child(shop.getSid()).setValue(shop);
    }

    public void updateOrder(Order order) {
        DatabaseReference ordersRef = this.database.getReference("orders");
        ordersRef.child(order.getOid()).setValue(order);
    }

    public void updateProductOrder(ProductOrder productOrder) {
        DatabaseReference productOrderRef = this.database.getReference("productOrder");
        productOrderRef.child(productOrder.getProductOrderId()).setValue(productOrder);
    }

    public void updateOrderStatus(String oid, String status) {
        this.database.getReference("orders").child(oid).child("orderStatus").setValue(status);
    }

    public void updateUserIsSignUpState(Boolean isSignUp) {
        this.database
                .getReference("users")
                .child(firebaseUser.getUid())
                .child("isSignUp")
                .setValue(isSignUp);
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

    public void deleteOrder(String oid) {
        DatabaseReference productRef = this.database.getReference("orders").child(oid);
        productRef.removeValue();
    }

    public void logOut() {
        auth.signOut();
        firebaseUser = auth.getCurrentUser();
    }

    public void getUser(ObjectCallBack objectCallBack) {
        if (firebaseUser == null) {
            this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        }

        DatabaseReference userRef = this.database.getReference("users").child(firebaseUser.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                objectCallBack.sendObjectToActivity(user);
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

    public void getOrders(ObjectsCallBack objectsCallBack, String id, String search, String orderStatus) {
        Query productsRef = this.database.getReference("orders").orderByChild(search).equalTo(id);

        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Object> orders = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Order order = snapshot.getValue(Order.class);
                    if (order.getOrderStatus().matches(orderStatus))
                        orders.add(order);
                }

                objectsCallBack.sendObjectsToActivity(orders);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("logInExistingUser", "Failed to read value.", error.toException());
            }
        });
    }

    public void savePhoto(SupplierURLCallBack supplierURLCallBack, Uri photoURI, String path) {
        String elementId = UUID.randomUUID().toString();

        final StorageReference ImagesPath = storage.getReference().child(path).child(elementId + ".jpg");

        ImagesPath.putFile(photoURI).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return ImagesPath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downUri = task.getResult();
                    downUri.toString();
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("image", downUri.toString());
                    supplierURLCallBack.passPhotoURL(downUri.toString());

                } else {
                    supplierURLCallBack.passPhotoURL(null);
                }
            }

        });
    }
}
