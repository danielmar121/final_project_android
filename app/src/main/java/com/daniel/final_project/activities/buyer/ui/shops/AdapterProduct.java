package com.daniel.final_project.activities.buyer.ui.shops;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daniel.final_project.R;
import com.daniel.final_project.objects.Product;
import com.daniel.final_project.objects.Shop;
import com.google.android.material.card.MaterialCardView;
import com.mikhaellopez.hfrecyclerviewkotlin.HFRecyclerView;

public class AdapterProduct extends HFRecyclerView<Product> {
    private MyItemClickListener itemClickListener;
    private Shop shop;

    public AdapterProduct() {
        // With Header & With Footer
        super(true, false);
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.bind(getItem(position));
        } else if (holder instanceof HeaderViewHolder) {
            AdapterProduct.HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.bind(getShop());
        } else if (holder instanceof FooterViewHolder) {

        }
    }

    //region Override Get ViewHolder
    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent) {
        return new ItemViewHolder(inflater.inflate(R.layout.product_buyer_item, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent) {
        return new HeaderViewHolder(inflater.inflate(R.layout.shop_buyer_header, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent) {
        return null;
    }

    // allows clicks events to be caught
    public void setClickListener(MyItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface MyItemClickListener {
        void onAddToCartClick(View view, Product product, int units);
    }

    //region ViewHolder Header and Footer
    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView product_item_LBL_name, product_item_LBL_description, product_item_LBL_price, product_item_LBL_units_to_buy;
        Button product_item_BTN_add_to_cart, product_item_BTN_plus_one, product_item_BTN_plus_ten, product_item_BTN_plus_hundred;
        RelativeLayout product_item_LAY_product_data, product_item_LAY_add_to_cart;
        ImageView product_item_IMG_product;
        MaterialCardView product_item_CARD_product_cover;

        ItemViewHolder(View itemView) {
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
            product_item_CARD_product_cover = itemView.findViewById(R.id.product_item_CARD_product_cover);
        }

        void bind(Product product) {
            product_item_LBL_name.setText(product.getName());
            product_item_LBL_description.setText(product.getDescription());
            product_item_LBL_price.setText("" + product.getPrice());

            Glide
                    .with(itemView.getContext())
                    .load(product.getImageUrl())
                    .centerCrop()
                    .into(product_item_IMG_product);

            product_item_LAY_product_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        if (product_item_LAY_add_to_cart.getVisibility() == View.VISIBLE) {
                            product_item_LAY_add_to_cart.setVisibility(View.GONE);
                        } else {
                            product_item_LAY_add_to_cart.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            product_item_BTN_add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        int units = Integer.parseInt(product_item_LBL_units_to_buy.getText().toString());
                        itemClickListener.onAddToCartClick(view, product, units);
                    }
                }
            });

            product_item_BTN_plus_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        int units = Integer.parseInt(product_item_LBL_units_to_buy.getText().toString()) + 1;
                        product_item_LBL_units_to_buy.setText("" + units);
                    }
                }
            });

            product_item_BTN_plus_ten.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        int units = Integer.parseInt(product_item_LBL_units_to_buy.getText().toString()) + 10;
                        product_item_LBL_units_to_buy.setText("" + units);
                    }
                }
            });

            product_item_BTN_plus_hundred.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        int units = Integer.parseInt(product_item_LBL_units_to_buy.getText().toString()) + 100;
                        product_item_LBL_units_to_buy.setText("" + units);
                    }
                }
            });
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView shop_buyer_header_LBL_shop_name, shop_buyer_header_LBL_description;
        ImageView shop_buyer_header_IMG_shop;


        HeaderViewHolder(View view) {
            super(view);
            shop_buyer_header_LBL_shop_name = itemView.findViewById(R.id.shop_buyer_header_LBL_shop_name);
            shop_buyer_header_LBL_description = itemView.findViewById(R.id.shop_buyer_header_LBL_description);

            shop_buyer_header_IMG_shop = itemView.findViewById(R.id.shop_buyer_header_IMG_shop);
        }

        void bind(Shop shop) {
            shop_buyer_header_LBL_shop_name.setText(shop.getName());
            shop_buyer_header_LBL_description.setText(shop.getDescription());
            Glide
                    .with(itemView.getContext())
                    .load(shop.getImageUrl())
                    .centerCrop()
                    .into(shop_buyer_header_IMG_shop);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        FooterViewHolder(View view) {
            super(view);
        }
    }
    //endregion
}

