package com.daniel.final_project.activities.buyer.ui.shops;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daniel.final_project.R;
import com.daniel.final_project.objects.Shop;
import com.mikhaellopez.hfrecyclerviewkotlin.HFRecyclerView;

public class AdapterShops extends HFRecyclerView<Shop> {
    private MyItemClickListener mClickListener;

    public AdapterShops() {
        // With Header & With Footer
        super(true, false);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.bind(getItem(position));
        } else if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof FooterViewHolder) {

        }
    }

    //region Override Get ViewHolder
    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent) {
        return new ItemViewHolder(inflater.inflate(R.layout.shop_buyer_item, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent) {
        return new HeaderViewHolder(inflater.inflate(R.layout.shops_buyer_header, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent) {
        return null;
    }

    // allows clicks events to be caught
    public void setClickListener(MyItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface MyItemClickListener {
        void onItemClick(View view, Shop shop);
    }

    //region ViewHolder Header and Footer
    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView shop_buyer_LBL_name, movie_LBL_description;
        ImageView shop_buyer_IMG_cover;

        ItemViewHolder(View itemView) {
            super(itemView);
            shop_buyer_LBL_name = itemView.findViewById(R.id.shop_buyer_LBL_name);
            movie_LBL_description = itemView.findViewById(R.id.movie_LBL_description);
            shop_buyer_IMG_cover = itemView.findViewById(R.id.shop_buyer_IMG_cover);
        }

        void bind(Shop shop) {
            shop_buyer_LBL_name.setText(shop.getName());
            movie_LBL_description.setText(shop.getDescription());
            Glide
                    .with(itemView.getContext())
                    .load(shop.getImageUrl())
                    .centerCrop()
                    .into(shop_buyer_IMG_cover);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        mClickListener.onItemClick(view, shop);
                    }
                }
            });
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        ImageView shops_buyer_header_IMG_advertise;

        HeaderViewHolder(View view) {
            super(view);
            shops_buyer_header_IMG_advertise = itemView.findViewById(R.id.shops_buyer_header_IMG_advertise);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        FooterViewHolder(View view) {
            super(view);
        }
    }
    //endregion
}
