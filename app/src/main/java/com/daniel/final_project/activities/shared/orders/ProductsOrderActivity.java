package com.daniel.final_project.activities.shared.orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.final_project.R;
import com.daniel.final_project.activities.buyer.ui.cart.BuyerPaymentActivity;
import com.daniel.final_project.interfaces.buyer.BuyerProductOrderCallBack;
import com.daniel.final_project.interfaces.buyer.BuyerProductOrderPriceCallBack;
import com.daniel.final_project.objects.ProductOrder;
import com.daniel.final_project.services.MyFireBase;
import com.daniel.final_project.utils.Constants;

import java.util.List;

public class ProductsOrderActivity extends AppCompatActivity {
    private MyFireBase myFireBase;
    private Double totalPrice;
    private RecyclerView products_order_buyer_LST_products;
    private TextView products_order_buyer_LBL_price_amount;
    private Button products_order_buyer_BTN_pay, products_order_buyer_BTN_cancel;

    private BuyerProductOrderPriceCallBack buyerProductOrderPriceCallBack = new BuyerProductOrderPriceCallBack() {
        @Override
        public void raisePrice(Double price) {
            totalPrice += price;
            products_order_buyer_LBL_price_amount.setText("" + totalPrice);
        }

        @Override
        public void decreasePrice(Double price) {
            totalPrice -= price;
            products_order_buyer_LBL_price_amount.setText("" + totalPrice);
        }
    };

    private BuyerProductOrderCallBack buyerProductOrderCallBack = new BuyerProductOrderCallBack() {

        @Override
        public void putProductOrdersInList(List<ProductOrder> ProductOrders) {
            AdapterProductOrder adapterProductOrder = new AdapterProductOrder(ProductsOrderActivity.this, ProductOrders, buyerProductOrderPriceCallBack);
            totalPrice = 0.0;

            adapterProductOrder.setClickListener(new AdapterProductOrder.MyItemClickListener() {

                @Override
                public void onDeleteClick(View view, ProductOrder productOrder) {
                    myFireBase.deleteProductOrder(productOrder);
                }
            });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductsOrderActivity.this);
            products_order_buyer_LST_products.setLayoutManager(linearLayoutManager);
            products_order_buyer_LST_products.setAdapter(adapterProductOrder);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ProductsOrderActivity.this,
                    DividerItemDecoration.VERTICAL);
            products_order_buyer_LST_products.addItemDecoration(dividerItemDecoration);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_order_buyer);
        myFireBase = MyFireBase.getInstance();

        findViews();
        initViews();
    }

    private void initViews() {
        String oid = getIntent().getStringExtra(Constants.ORDER_ID);
        Boolean isSupplier = getIntent().getBooleanExtra(Constants.IS_SUPPLIER, false);
        myFireBase.getProductOrders(buyerProductOrderCallBack, oid);
        totalPrice = 0.0;

        if (isSupplier) {
            initViewsSupplier(oid);
        } else {
            initViewsBuyer(oid);
        }

        products_order_buyer_BTN_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initViewsBuyer(String oid) {
        products_order_buyer_BTN_pay.setText(R.string.pay);
        products_order_buyer_BTN_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buyerPaymentIntent = new Intent(ProductsOrderActivity.this, BuyerPaymentActivity.class);
                buyerPaymentIntent.putExtra(Constants.ORDER_ID, oid);
                startActivity(buyerPaymentIntent);
                finish();
            }
        });
    }

    private void initViewsSupplier(String oid) {
        products_order_buyer_BTN_pay.setText(R.string.approve);
        products_order_buyer_BTN_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFireBase.updateOrderStatus(oid, Constants.CLOSE);
                finish();
            }
        });
    }

    private void findViews() {
        products_order_buyer_LST_products = findViewById(R.id.products_order_buyer_LST_products);
        products_order_buyer_LBL_price_amount = findViewById(R.id.products_order_buyer_LBL_price_amount);
        products_order_buyer_BTN_pay = findViewById(R.id.products_order_buyer_BTN_pay);
        products_order_buyer_BTN_cancel = findViewById(R.id.products_order_buyer_BTN_cancel);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}