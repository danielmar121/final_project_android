package com.daniel.final_project.activities.buyer.ui.shops;

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
import com.daniel.final_project.interfaces.buyer.BuyerShopsCallBack;
import com.daniel.final_project.objects.Shop;
import com.daniel.final_project.services.MyFireBase;
import com.daniel.final_project.utils.Constants;
import com.google.gson.Gson;

import java.util.List;

public class ShopsFragment extends Fragment {

    View root;
    MyFireBase myFireBase;
    RecyclerView buyer_shops_LST_shops;

    private BuyerShopsCallBack buyerShopsCallBack = new BuyerShopsCallBack() {
        @Override
        public void putShopsInList(List<Shop> shops) {
            AdapterShops adapterShop = new AdapterShops();

            adapterShop.setClickListener(new AdapterShops.MyItemClickListener() {
                @Override
                public void onItemClick(View view, Shop shop) {
                    Gson gson = new Gson();
                    Intent shopIntent = new Intent(getContext(), ShopBuyer.class);
                    String shopJson = gson.toJson(shop);
                    shopIntent.putExtra(Constants.SHOP, shopJson);
                    startActivity(shopIntent);
                }
            });

            adapterShop.setData(shops);
            buyer_shops_LST_shops.setLayoutManager(new LinearLayoutManager(getContext()));
            buyer_shops_LST_shops.setAdapter(adapterShop);
        }
    };


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_shops_buyer, container, false);
        myFireBase = MyFireBase.getInstance();

        findViews();
        initViews();

        return root;
    }

    private void initViews() {
        myFireBase.getShopsForBuyer(buyerShopsCallBack);
    }

    private void findViews() {
        buyer_shops_LST_shops = root.findViewById(R.id.buyer_shops_LST_shops);
    }
}
