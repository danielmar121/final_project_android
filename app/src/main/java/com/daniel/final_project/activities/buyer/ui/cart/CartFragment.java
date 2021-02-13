package com.daniel.final_project.activities.buyer.ui.cart;

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
import com.daniel.final_project.interfaces.buyer.BuyerOrderCallBack;
import com.daniel.final_project.objects.Order;
import com.daniel.final_project.services.MyFireBase;
import com.daniel.final_project.utils.Constants;

import java.util.List;

public class CartFragment extends Fragment {

    View root;
    MyFireBase myFireBase;
    RecyclerView buyer_cart_LST_orders;

    private BuyerOrderCallBack buyerOrderCallBack = new BuyerOrderCallBack() {
        @Override
        public void putOrdersInList(List<Order> Orders) {
            if (Orders == null || Orders.isEmpty())
                return;

            AdapterOrder adapterOrder = new AdapterOrder(getContext(), Orders);

            adapterOrder.setClickListener(new AdapterOrder.MyItemClickListener() {
                @Override
                public void openOrderDetails(View view, Order order) {
                    Intent shopIntent = new Intent(getContext(), ProductsOrderBuyer.class);
                    shopIntent.putExtra(Constants.ORDER_ID, order.getOid());
                    startActivity(shopIntent);
                }
            });

            buyer_cart_LST_orders.setLayoutManager(new LinearLayoutManager(getContext()));
            buyer_cart_LST_orders.setAdapter(adapterOrder);
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_cart_buyer, container, false);
        myFireBase = MyFireBase.getInstance();

        findViews();
        initViews();

        return root;
    }

    private void initViews() {
        String uid = myFireBase.getFirebaseUser().getUid();
        myFireBase.getOpenOrders(buyerOrderCallBack, uid);
    }

    private void findViews() {
        buyer_cart_LST_orders = root.findViewById(R.id.buyer_cart_LST_orders);
    }
}