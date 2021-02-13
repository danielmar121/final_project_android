package com.daniel.final_project.activities.buyer.ui.shops;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.daniel.final_project.R;
import com.daniel.final_project.objects.Product;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.MyViewHolder> {
    private List<Product> products;
    private LayoutInflater mInflater;
    private MyItemClickListener mClickListener;

    // data is passed into the constructor
    AdapterProduct(Context context, List<Product> _products) {
        this.mInflater = LayoutInflater.from(context);
        this.products = _products;
    }

    // inflates the row layout from xml when needed
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.product_buyer_item, parent, false);
        return new MyViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product product = products.get(position);
        holder.product_item_LBL_name.setText(product.getName());
        holder.product_item_LBL_description.setText(product.getDescription());
        holder.product_item_LBL_price.setText("" + product.getPrice());

//        Glide
//                .with(mInflater.getContext())
//                .load(product.getImageUrl())
//                .centerCrop()
//                .into(holder.product_item_IMG_product);

        holder.product_item_LAY_product_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    if (holder.product_item_LAY_add_to_cart.getVisibility() == View.VISIBLE) {
                        holder.product_item_LAY_add_to_cart.setVisibility(View.GONE);
                    } else {
                        holder.product_item_LAY_add_to_cart.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        holder.product_item_BTN_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    int units = Integer.parseInt(holder.product_item_LBL_units_to_buy.getText().toString());
                    mClickListener.onAddToCartClick(view, product, units);
                }
            }
        });

        holder.product_item_BTN_plus_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    int units = Integer.parseInt(holder.product_item_LBL_units_to_buy.getText().toString()) + 1;
                    holder.product_item_LBL_units_to_buy.setText("" + units);
                }
            }
        });

        holder.product_item_BTN_plus_ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    int units = Integer.parseInt(holder.product_item_LBL_units_to_buy.getText().toString()) + 10;
                    holder.product_item_LBL_units_to_buy.setText("" + units);
                }
            }
        });

        holder.product_item_BTN_plus_hundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    int units = Integer.parseInt(holder.product_item_LBL_units_to_buy.getText().toString()) + 100;
                    holder.product_item_LBL_units_to_buy.setText("" + units);
                }
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return products.size();
    }

    // convenience method for getting data at click position
    public Product getItem(int id) {
        return products.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(MyItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface MyItemClickListener {
        void onAddToCartClick(View view, Product product, int units);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_item_LBL_name, product_item_LBL_description, product_item_LBL_price, product_item_LBL_units_to_buy;
        Button product_item_BTN_add_to_cart, product_item_BTN_plus_one, product_item_BTN_plus_ten, product_item_BTN_plus_hundred;
        RelativeLayout product_item_LAY_product_data, product_item_LAY_add_to_cart;
        ImageView product_item_IMG_product;


        MyViewHolder(View itemView) {
            super(itemView);
            product_item_LBL_name = itemView.findViewById(R.id.product_item_LBL_name);
            product_item_LBL_description = itemView.findViewById(R.id.product_item_LBL_description);
            product_item_LBL_price = itemView.findViewById(R.id.product_item_LBL_price);
            product_item_LBL_units_to_buy = itemView.findViewById(R.id.product_item_LBL_units_to_buy);

            product_item_BTN_add_to_cart = itemView.findViewById(R.id.product_item_BTN_add_to_cart);
            product_item_BTN_plus_one = itemView.findViewById(R.id.product_item_BTN_plus_one);
            product_item_BTN_plus_ten = itemView.findViewById(R.id.product_item_BTN_plus_ten);
            product_item_BTN_plus_hundred = itemView.findViewById(R.id.product_item_BTN_plus_hundred);

            product_item_LAY_product_data = itemView.findViewById(R.id.product_item_LAY_product_data);
            product_item_LAY_add_to_cart = itemView.findViewById(R.id.product_item_LAY_add_to_cart);
            product_item_LAY_add_to_cart.setVisibility(View.GONE);

            product_item_IMG_product = itemView.findViewById(R.id.product_item_IMG_product);
        }
    }

}
