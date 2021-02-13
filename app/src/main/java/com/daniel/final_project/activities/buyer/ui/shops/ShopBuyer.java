package com.daniel.final_project.activities.buyer.ui.shops;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.final_project.R;
import com.daniel.final_project.interfaces.buyer.BuyerShopCallBack;
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
    MyFireBase myFireBase;
    ImageView buyer_shop_IMG_shop;
    RecyclerView buyer_shop_LST_products;
    ShopBuyer shopBuyer;
    Shop shop;
    Order order;


    private BuyerShopCallBack buyerShopCallBack = new BuyerShopCallBack() {
        @Override
        public void putProductsInList(List<Product> products) {
            AdapterProduct adapterProduct = new AdapterProduct(shopBuyer, products);

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

                        myFireBase.updateOrder(order);
                        myFireBase.updateProductOrder(productOrder);
                    }
                }
            });

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
        myFireBase.getProductsForShop(buyerShopCallBack, shop.getSid());
    }

    private void findViews() {
        buyer_shop_IMG_shop = findViewById(R.id.buyer_shop_IMG_shop);
        buyer_shop_LST_products = findViewById(R.id.buyer_shop_LST_products);
    }
}