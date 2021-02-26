package com.daniel.final_project.activities.supplier.ui.shop;

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
import com.mikhaellopez.hfrecyclerviewkotlin.HFRecyclerView;

public class AdapterShop extends HFRecyclerView<Product> {
    private MyItemClickListener itemClickListener;
    private MyHeaderClickListener headerClickListener;
    private Shop shop;

    public AdapterShop() {
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
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.bind(getShop());
        } else if (holder instanceof FooterViewHolder) {

        }
    }

    //region Override Get ViewHolder
    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent) {
        return new ItemViewHolder(inflater.inflate(R.layout.product_supplier_item, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent) {
        return new HeaderViewHolder(inflater.inflate(R.layout.shop_supplier_header, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent) {
        return null;
    }

    // allows clicks events to be caught
    public void setItemClickListener(MyItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setHeaderClickListener(MyHeaderClickListener headerClickListener) {
        this.headerClickListener = headerClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface MyItemClickListener {
        void onEditProductClick(View view, Product product);

        void onDeleteProductClick(View view, Product product);
    }

    public interface MyHeaderClickListener {
        void onEditShopClick(View view);

        void onAddProductClick(View view);
    }

    //region ViewHolder Header and Footer
    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView product_supplier_item_LBL_name, product_supplier_item_LBL_description, product_supplier_item_LBL_price, product_supplier_item_LBL_units_to_sell;
        Button product_supplier_item_BTN_delete_product, product_supplier_item_BTN_edit_product;
        ImageView product_supplier_item_IMG_product;
        RelativeLayout product_supplier_item_LAY_product_data, product_supplier_item_LAY_edit;

        ItemViewHolder(View itemView) {
            super(itemView);
            product_supplier_item_LBL_name = itemView.findViewById(R.id.product_supplier_item_LBL_name);
            product_supplier_item_LBL_description = itemView.findViewById(R.id.product_supplier_item_LBL_description);
            product_supplier_item_LBL_price = itemView.findViewById(R.id.product_supplier_item_LBL_price);
            product_supplier_item_LBL_units_to_sell = itemView.findViewById(R.id.product_supplier_item_LBL_units_to_sell);

            product_supplier_item_BTN_delete_product = itemView.findViewById(R.id.product_supplier_item_BTN_delete_product);
            product_supplier_item_BTN_edit_product = itemView.findViewById(R.id.product_supplier_item_BTN_edit_product);

            product_supplier_item_LAY_product_data = itemView.findViewById(R.id.product_supplier_item_LAY_product_data);
            product_supplier_item_LAY_edit = itemView.findViewById(R.id.product_supplier_item_LAY_edit);
            product_supplier_item_LAY_edit.setVisibility(View.GONE);

            product_supplier_item_IMG_product = itemView.findViewById(R.id.product_supplier_item_IMG_product);
        }

        void bind(Product product) {
            product_supplier_item_LBL_name.setText(product.getName());
            product_supplier_item_LBL_description.setText(product.getDescription());
            product_supplier_item_LBL_price.setText("" + product.getPrice());
            product_supplier_item_LBL_units_to_sell.setText("" + product.getQuantity());
            Glide
                    .with(itemView.getContext())
                    .load(product.getImageUrl())
                    .centerCrop()
                    .into(product_supplier_item_IMG_product);

            product_supplier_item_LAY_product_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        if (product_supplier_item_LAY_edit.getVisibility() == View.VISIBLE) {
                            product_supplier_item_LAY_edit.setVisibility(View.GONE);
                        } else {
                            product_supplier_item_LAY_edit.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            product_supplier_item_BTN_delete_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        itemClickListener.onDeleteProductClick(view, product);
                    }
                }
            });

            product_supplier_item_BTN_edit_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        itemClickListener.onEditProductClick(view, product);
                    }
                }
            });
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView supplier_shop_header_LBL_app_name, supplier_shop_header_LBL_description;
        ImageView supplier_shop_header_IMG_shop;
        Button supplier_shop_header_BTN_edit_shop, supplier_shop_header_BTN_add_product;


        HeaderViewHolder(View view) {
            super(view);
            supplier_shop_header_LBL_app_name = itemView.findViewById(R.id.supplier_shop_header_LBL_app_name);
            supplier_shop_header_LBL_description = itemView.findViewById(R.id.supplier_shop_header_LBL_description);

            supplier_shop_header_BTN_edit_shop = itemView.findViewById(R.id.supplier_shop_header_BTN_edit_shop);
            supplier_shop_header_BTN_add_product = itemView.findViewById(R.id.supplier_shop_header_BTN_add_product);

            supplier_shop_header_IMG_shop = itemView.findViewById(R.id.supplier_shop_header_IMG_shop);
        }

        void bind(Shop shop) {
            supplier_shop_header_LBL_app_name.setText(shop.getName());
            supplier_shop_header_LBL_description.setText(shop.getDescription());
            Glide
                    .with(itemView.getContext())
                    .load(shop.getImageUrlWide())
                    .centerCrop()
                    .into(supplier_shop_header_IMG_shop);

            supplier_shop_header_BTN_edit_shop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (headerClickListener != null) {
                        headerClickListener.onEditShopClick(view);
                    }
                }
            });

            supplier_shop_header_BTN_add_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (headerClickListener != null) {
                        headerClickListener.onAddProductClick(view);
                    }
                }
            });
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        FooterViewHolder(View view) {
            super(view);
        }
    }
    //endregion

}
