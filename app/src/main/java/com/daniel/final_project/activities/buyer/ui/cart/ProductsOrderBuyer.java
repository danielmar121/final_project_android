package com.daniel.final_project.activities.buyer.ui.cart;

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
import com.daniel.final_project.interfaces.buyer.BuyerProductOrderCallBack;
import com.daniel.final_project.interfaces.buyer.BuyerProductOrderPriceCallBack;
import com.daniel.final_project.objects.ProductOrder;
import com.daniel.final_project.services.MyFireBase;
import com.daniel.final_project.utils.Constants;

import java.util.List;

public class ProductsOrderBuyer extends AppCompatActivity {
    MyFireBase myFireBase;
    Double totalPrice;
    RecyclerView products_order_buyer_LST_products;
    TextView products_order_buyer_LBL_price_amount;
    Button products_order_buyer_BTN_pay;

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
            AdapterProductOrder adapterProductOrder = new AdapterProductOrder(ProductsOrderBuyer.this, ProductOrders, buyerProductOrderPriceCallBack);
            totalPrice = 0.0;

            adapterProductOrder.setClickListener(new AdapterProductOrder.MyItemClickListener() {

                @Override
                public void onDeleteClick(View view, ProductOrder productOrder) {
                    myFireBase.deleteProductOrder(productOrder.getProductOrderId());
                }
            });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductsOrderBuyer.this);
            products_order_buyer_LST_products.setLayoutManager(linearLayoutManager);
            products_order_buyer_LST_products.setAdapter(adapterProductOrder);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ProductsOrderBuyer.this,
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
        myFireBase.getProductOrders(buyerProductOrderCallBack, oid);
        totalPrice = 0.0;


        products_order_buyer_BTN_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buyerPaymentIntent = new Intent(ProductsOrderBuyer.this, BuyerPaymentActivity.class);
                buyerPaymentIntent.putExtra(Constants.ORDER_ID, oid);
                startActivity(buyerPaymentIntent);
                finish();
            }
        });
    }

    private void findViews() {
        products_order_buyer_LST_products = findViewById(R.id.products_order_buyer_LST_products);
        products_order_buyer_LBL_price_amount = findViewById(R.id.products_order_buyer_LBL_price_amount);
        products_order_buyer_BTN_pay = findViewById(R.id.products_order_buyer_BTN_pay);

    }
}