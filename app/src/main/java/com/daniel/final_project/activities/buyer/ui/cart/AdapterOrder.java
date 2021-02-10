package com.daniel.final_project.activities.buyer.ui.cart;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.daniel.final_project.R;
import com.daniel.final_project.objects.Order;

import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.MyViewHolder> {
    private List<Order> orders;
    private LayoutInflater mInflater;
    private MyItemClickListener mClickListener;

    // data is passed into the constructor
    AdapterOrder(Context context, List<Order> _orders) {
        this.mInflater = LayoutInflater.from(context);
        this.orders = _orders;
    }

    // inflates the row layout from xml when needed
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.order_buyer_item, parent, false);
        return new MyViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.order_item_LBL_shop_name.setText(order.getShopName());
        holder.order_item_LBL_status.setText(order.getOrderStatus());

        holder.order_item_LAY_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    mClickListener.openOrderDetails(view, order);
                }
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return orders.size();
    }

    // convenience method for getting data at click position
    public Order getItem(int id) {
        return orders.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(MyItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface MyItemClickListener {
        void openOrderDetails(View view, Order order);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView order_item_LBL_shop_name, order_item_LBL_status;
        RelativeLayout order_item_LAY_main;

        MyViewHolder(View itemView) {
            super(itemView);
            order_item_LBL_shop_name = itemView.findViewById(R.id.order_item_LBL_shop_name);
            order_item_LBL_status = itemView.findViewById(R.id.order_item_LBL_status);

            order_item_LAY_main = itemView.findViewById(R.id.order_item_LAY_main);
        }
    }

}

