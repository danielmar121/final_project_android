package com.daniel.final_project.activities.shared.orders;

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
import com.daniel.final_project.interfaces.ObjectCallBack;
import com.daniel.final_project.interfaces.ObjectsCallBack;
import com.daniel.final_project.objects.Order;
import com.daniel.final_project.objects.Shop;
import com.daniel.final_project.objects.User;
import com.daniel.final_project.services.MyFireBase;
import com.daniel.final_project.utils.Constants;

import java.util.List;

public class OrdersFragment extends Fragment {

    private View root;
    private MyFireBase myFireBase;
    private RecyclerView buyer_cart_LST_orders;
    private User user;

    private ObjectsCallBack ordersCallBack = new ObjectsCallBack() {
        @Override
        public void sendObjectsToActivity(List<Object> objects) {
            List<Order> Orders = (List<Order>) (List<?>) objects;
            if (Orders == null || Orders.isEmpty() || getContext() == null)
                return;

            AdapterOrder adapterOrder = new AdapterOrder(getContext(), Orders);

            adapterOrder.setClickListener(new AdapterOrder.MyItemClickListener() {
                @Override
                public void openOrderDetails(View view, Order order) {

                    Intent shopIntent = new Intent(getContext(), ProductsOrderActivity.class);
                    shopIntent.putExtra(Constants.ORDER_ID, order.getOid());
                    shopIntent.putExtra(Constants.IS_SUPPLIER, user.getSupplier());
                    startActivity(shopIntent);
                }

                @Override
                public void deleteOrder(Order order) {
                    myFireBase.deleteOrder(order.getOid());
                }
            });

            buyer_cart_LST_orders.setLayoutManager(new LinearLayoutManager(getContext()));
            buyer_cart_LST_orders.setAdapter(adapterOrder);
        }
    };

    private ObjectCallBack shopCallBack = new ObjectCallBack() {
        @Override
        public void sendObjectToActivity(Object object) {
            Shop shop = (Shop) object;
            String search = "sid";
            String orderStatus = Constants.RECEIVED;
            myFireBase.getOrders(ordersCallBack, shop.getSid(), search, orderStatus);
        }
    };

    private ObjectCallBack userCallBack = new ObjectCallBack() {
        @Override
        public void sendObjectToActivity(Object object) {
            user = (User) object;

            if (user.getSupplier()) {
                myFireBase.getShop(user.getUid(), shopCallBack);
            } else {
                String search = "uid";
                String orderStatus = Constants.OPEN;
                myFireBase.getOrders(ordersCallBack, user.getUid(), search, orderStatus);
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_orders_shared, container, false);
        myFireBase = MyFireBase.getInstance();

        findViews();
        initViews();

        return root;
    }

    private void initViews() {
        myFireBase.getUser(userCallBack);
    }

    private void findViews() {
        buyer_cart_LST_orders = root.findViewById(R.id.shared_orders_LST_orders);
    }

    @Override
    public void onStart() {
        super.onStart();
        myFireBase.getUser(userCallBack);
    }
}