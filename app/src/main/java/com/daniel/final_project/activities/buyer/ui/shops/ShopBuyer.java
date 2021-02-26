package com.daniel.final_project.activities.buyer.ui.shops;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.final_project.R;
import com.daniel.final_project.interfaces.ObjectsCallBack;
import com.daniel.final_project.interfaces.ToastCallBack;
import com.daniel.final_project.objects.Order;
import com.daniel.final_project.objects.Product;
import com.daniel.final_project.objects.ProductOrder;
import com.daniel.final_project.objects.Shop;
import com.daniel.final_project.services.MyFireBase;
import com.daniel.final_project.utils.Constants;
import com.google.gson.Gson;

import java.util.List;
import java.util.UUID;

public class ShopBuyer extends AppCompatActivity {
    private MyFireBase myFireBase;
    private RecyclerView buyer_shop_LST_products;
    private ShopBuyer shopBuyer;
    private Shop shop;
    private Order order;

    private ToastCallBack toastCallBack = new ToastCallBack() {
        @Override
        public void toast(String units) {
            String message = "Cant buy more than " + units + " units";
            Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
        }
    };

    private ObjectsCallBack objectsCallBack = new ObjectsCallBack() {
        @Override
        public void sendObjectsToActivity(List<Object> objects) {
            List<Product> products = (List<Product>) (List<?>) objects;
            AdapterProduct adapterProduct = new AdapterProduct();

            adapterProduct.setClickListener(new AdapterProduct.MyItemClickListener() {

                @Override
                public void onAddToCartClick(View view, Product product, int units) {
                    if (units > 0) {
                        UUID uuid = UUID.randomUUID();
                        String productOrderId = uuid.toString();

                        ProductOrder productOrder = new ProductOrder()
                                .setOid(order.getOid())
                                .setPid(product.getPid())
                                .setQuantity(units)
                                .setProductOrderId(productOrderId);

                        product.setQuantity(product.getQuantity() - units);

                        myFireBase.updateOrder(order);
                        myFireBase.updateProduct(product);
                        myFireBase.updateProductOrder(productOrder);
                    }
                }
            });

            adapterProduct.setData(products);
            adapterProduct.setShop(shop);
            adapterProduct.setToastCallBack(toastCallBack);
            buyer_shop_LST_products.setLayoutManager(new LinearLayoutManager(shopBuyer));
            buyer_shop_LST_products.setAdapter(adapterProduct);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_buyer);
        myFireBase = MyFireBase.getInstance();
        shopBuyer = this;

        openNewOrder();

        findViews();
        initViews();
    }

    private void openNewOrder() {
        Gson gson = new Gson();
        UUID uuid = UUID.randomUUID();
        String oid = uuid.toString();
        String uid = myFireBase.getFirebaseUser().getUid();

        String shopJson = getIntent().getStringExtra(Constants.SHOP);
        shop = gson.fromJson(shopJson, Shop.class);

        order = new Order()
                .setOid(oid)
                .setSid(shop.getSid())
                .setUid(uid)
                .setOrderStatus("OPEN")
                .setShopName(shop.getName());
    }

    private void initViews() {
        myFireBase.getProductsForShop(objectsCallBack, shop.getSid());
    }

    private void findViews() {
        buyer_shop_LST_products = findViewById(R.id.buyer_shop_LST_products);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}