package com.daniel.final_project.activities.buyer.ui.shops;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.final_project.R;
import com.daniel.final_project.interfaces.BuyerShopsCallBack;
import com.daniel.final_project.objects.Shop;
import com.daniel.final_project.services.MyFireBase;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ShopsFragment extends Fragment {

    View root;
    MyFireBase myFireBase;
    TextInputEditText buyer_shops_ETXT_search;
    ImageView buyer_shops_IMG_advertise;
    RecyclerView buyer_shops_LST_shops;

    private BuyerShopsCallBack buyerShopsCallBack = new BuyerShopsCallBack() {
        @Override
        public void putShopsInList(List<Shop> shops) {
            AdapterShop adapterShop = new AdapterShop(getContext(), shops);

            adapterShop.setClickListener(new AdapterShop.MyItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Log.d("putShopsInList", "Shop number " + position);
                }
            });

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
        buyer_shops_ETXT_search = root.findViewById(R.id.buyer_shops_ETXT_search);
        buyer_shops_IMG_advertise = root.findViewById(R.id.buyer_shops_IMG_advertise);
        buyer_shops_LST_shops = root.findViewById(R.id.buyer_shops_LST_shops);
    }
}
