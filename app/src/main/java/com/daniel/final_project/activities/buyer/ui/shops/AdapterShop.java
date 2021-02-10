package com.daniel.final_project.activities.buyer.ui.shops;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.daniel.final_project.R;
import com.daniel.final_project.objects.Shop;

import java.util.List;

public class AdapterShop extends RecyclerView.Adapter<AdapterShop.MyViewHolder> {
    private List<Shop> shops;
    private LayoutInflater mInflater;
    private MyItemClickListener mClickListener;

    // data is passed into the constructor
    AdapterShop(Context context, List<Shop> _shops) {
        this.mInflater = LayoutInflater.from(context);
        this.shops = _shops;
    }

    // inflates the row layout from xml when needed
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.shop_buyer_item, parent, false);
        return new MyViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Shop shop = shops.get(position);
        holder.shop_buyer_LBL_name.setText(shop.getName());
        holder.movie_LBL_description.setText(shop.getDescription());
        Glide
                .with(mInflater.getContext())
                .load(shop.getImageUrl())
                .centerCrop()
                .into(holder.shop_buyer_IMG_cover);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return shops.size();
    }

    // convenience method for getting data at click position
    public Shop getItem(int id) {
        return shops.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(MyItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends ViewHolder {
        TextView shop_buyer_LBL_name, movie_LBL_description;
        ImageView shop_buyer_IMG_cover;

        MyViewHolder(View itemView) {
            super(itemView);
            shop_buyer_LBL_name = itemView.findViewById(R.id.shop_buyer_LBL_name);
            movie_LBL_description = itemView.findViewById(R.id.movie_LBL_description);
            shop_buyer_IMG_cover = itemView.findViewById(R.id.shop_buyer_IMG_cover);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        mClickListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });
        }
    }

}
