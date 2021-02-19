package com.daniel.final_project.activities.supplier.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.final_project.R;
import com.daniel.final_project.activities.supplier.SupplierAddingProductActivity;
import com.daniel.final_project.activities.supplier.SupplierSignUpActivity;
import com.daniel.final_project.interfaces.ObjectCallBack;
import com.daniel.final_project.interfaces.ObjectsCallBack;
import com.daniel.final_project.objects.Product;
import com.daniel.final_project.objects.Shop;
import com.daniel.final_project.services.MyFireBase;
import com.daniel.final_project.utils.Constants;
import com.google.gson.Gson;

import java.util.List;

public class ShopFragment extends Fragment {

    View root;
    MyFireBase myFireBase;
    RecyclerView supplier_shop_LST_shop;
    Shop shop;
    AdapterShop adapterShop;

    private ObjectsCallBack objectsCallBack = new ObjectsCallBack() {
        @Override
        public void sendObjectsToActivity(List<Object> objects) {
            adapterShop = new AdapterShop();
            List<Product> products = (List<Product>) (List<?>) objects;

            adapterShop.setItemClickListener(new AdapterShop.MyItemClickListener() {
                @Override
                public void onEditProductClick(View view, Product product) {
                    Gson gson = new Gson();
                    Intent shopIntent = new Intent(getContext(), SupplierAddingProductActivity.class);

                    String shopJson = gson.toJson(shop);
                    shopIntent.putExtra(Constants.SHOP, shopJson);

                    String productJson = gson.toJson(product);
                    shopIntent.putExtra(Constants.PRODUCT, productJson);

                    startActivity(shopIntent);
                }

                @Override
                public void onDeleteProductClick(View view, Product product) {
                    myFireBase.deleteProduct(product.getPid());
                }
            });

            adapterShop.setHeaderClickListener(new AdapterShop.MyHeaderClickListener() {
                @Override
                public void onEditShopClick(View view) {
                    Gson gson = new Gson();
                    Intent shopIntent = new Intent(getContext(), SupplierSignUpActivity.class);
                    String shopJson = gson.toJson(shop);
                    shopIntent.putExtra(Constants.SHOP, shopJson);
                    startActivity(shopIntent);
                }

                @Override
                public void onAddProductClick(View view) {
                    Gson gson = new Gson();
                    Intent shopIntent = new Intent(getContext(), SupplierAddingProductActivity.class);
                    String shopJson = gson.toJson(shop);
                    shopIntent.putExtra(Constants.SHOP, shopJson);
                    startActivity(shopIntent);
                }
            });

            adapterShop.setData(products);
            adapterShop.setShop(shop);
            supplier_shop_LST_shop.setLayoutManager(new LinearLayoutManager(getContext()));
            supplier_shop_LST_shop.setAdapter(adapterShop);
        }
    };

    private ObjectCallBack objectCallBack = new ObjectCallBack() {
        @Override
        public void sendObjectToActivity(Object object) {
            shop = (Shop) object;
            myFireBase.getProductsForShop(objectsCallBack, shop.getSid());
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_shop_supplier, container, false);
        myFireBase = MyFireBase.getInstance();

        findViews();
        initViews();

        return root;
    }

    private void initViews() {
        myFireBase.getShop(myFireBase.getFirebaseUser().getUid(), objectCallBack);
    }

    private void findViews() {
        supplier_shop_LST_shop = root.findViewById(R.id.supplier_shop_LST_shop);
    }
}