package com.daniel.final_project.activities.shared.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.daniel.final_project.R;
import com.daniel.final_project.interfaces.shared.ObjectCallBack;
import com.daniel.final_project.interfaces.buyer.BuyerProductOrderPriceCallBack;
import com.daniel.final_project.objects.Product;
import com.daniel.final_project.objects.ProductOrder;
import com.daniel.final_project.services.MyFireBase;

import java.util.List;

public class AdapterProductOrder extends RecyclerView.Adapter<AdapterProductOrder.MyViewHolder> {
    private List<ProductOrder> productOrders;
    private LayoutInflater mInflater;
    private MyItemClickListener mClickListener;
    private BuyerProductOrderPriceCallBack buyerProductOrderPriceCallBack;

    // data is passed into the constructor
    AdapterProductOrder(Context context, List<ProductOrder> _productOrders, BuyerProductOrderPriceCallBack buyerProductOrderPriceCallBack) {
        this.mInflater = LayoutInflater.from(context);
        this.productOrders = _productOrders;
        this.buyerProductOrderPriceCallBack = buyerProductOrderPriceCallBack;
    }

    // inflates the row layout from xml when needed
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.product_order_item, parent, false);
        return new MyViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ProductOrder productOrder = productOrders.get(position);
        MyFireBase myFireBase = MyFireBase.getInstance();

        ObjectCallBack productCallBack = new ObjectCallBack() {
            @Override
            public void sendObjectToActivity(Object object) {
                Product product = (Product) object;
                if (product != null) {
                    holder.product_order_buyer_LBL_name.setText(product.getName());
                    holder.product_order_buyer_LBL_price_unit.setText("" + product.getPrice());

                    Double totalPrice = productOrder.getQuantity() * product.getPrice();
                    holder.product_order_buyer_LBL_price_total.setText(totalPrice.toString());

                    buyerProductOrderPriceCallBack.raisePrice(totalPrice);
                    holder.product_order_buyer_BTN_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mClickListener != null) {
                                buyerProductOrderPriceCallBack.decreasePrice(totalPrice);
                                mClickListener.onDeleteClick(view, productOrder);
                            }
                        }
                    });
                }
            }
        };

        holder.product_order_buyer_LBL_units_amount.setText("" + productOrder.getQuantity());
        holder.product_order_buyer_LBL_units_total.setText("" + productOrder.getQuantity());
        myFireBase.getProduct(productCallBack, productOrder.getPid());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return productOrders.size();
    }

    // convenience method for getting data at click position
    public ProductOrder getItem(int id) {
        return productOrders.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(MyItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface MyItemClickListener {
        void onDeleteClick(View view, ProductOrder productOrder);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_order_buyer_LBL_name, product_order_buyer_LBL_units_amount, product_order_buyer_LBL_price_unit, product_order_buyer_LBL_units_total, product_order_buyer_LBL_price_total;
        Button product_order_buyer_BTN_delete;


        MyViewHolder(View itemView) {
            super(itemView);
            product_order_buyer_LBL_name = itemView.findViewById(R.id.product_order_shared_LBL_name);
            product_order_buyer_LBL_units_amount = itemView.findViewById(R.id.product_order_shared_LBL_units_amount);
            product_order_buyer_LBL_price_unit = itemView.findViewById(R.id.product_order_shared_LBL_price_unit);
            product_order_buyer_LBL_units_total = itemView.findViewById(R.id.product_order_shared_LBL_units_total);
            product_order_buyer_LBL_price_total = itemView.findViewById(R.id.product_order_shared_LBL_price_total);

            product_order_buyer_BTN_delete = itemView.findViewById(R.id.product_order_shared_BTN_delete);
        }
    }

}
