package com.daniel.final_project.activities.buyer.ui.cart;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daniel.final_project.R;
import com.daniel.final_project.services.MyFireBase;
import com.daniel.final_project.utils.Constants;

public class BuyerPaymentActivity extends AppCompatActivity {
    private EditText buyer_payment_ETXT_address, buyer_payment_ETXT_id, buyer_payment_ETXT_full_name, buyer_payment_ETXT_credit_card, buyer_payment_ETXT_cvv;
    private Spinner buyer_payment_SPN_year, buyer_payment_SPN_month;
    private Button buyer_payment_BTN_pay, buyer_payment_BTN_cancel;
    private String oid;
    private MyFireBase myFireBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_payment);
        oid = getIntent().getStringExtra(Constants.ORDER_ID);
        myFireBase = MyFireBase.getInstance();

        findViews();
        initViews();
    }

    private void initViews() {
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.credit_card_year, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buyer_payment_SPN_year.setAdapter(yearAdapter);

        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.credit_card_month, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buyer_payment_SPN_month.setAdapter(monthAdapter);

        buyer_payment_BTN_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isPayed = pay();
                if (isPayed) {
                    myFireBase.updateOrderStatus(oid, "RECEIVED");
                    finish();
                } else {
                    Toast.makeText(BuyerPaymentActivity.this, "Payment failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buyer_payment_BTN_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findViews() {
        buyer_payment_ETXT_address = findViewById(R.id.buyer_payment_ETXT_address);
        buyer_payment_ETXT_id = findViewById(R.id.buyer_payment_ETXT_id);
        buyer_payment_ETXT_full_name = findViewById(R.id.buyer_payment_ETXT_full_name);
        buyer_payment_ETXT_credit_card = findViewById(R.id.buyer_payment_ETXT_credit_card);
        buyer_payment_ETXT_cvv = findViewById(R.id.buyer_payment_ETXT_cvv);

        buyer_payment_SPN_year = findViewById(R.id.buyer_payment_SPN_year);
        buyer_payment_SPN_month = findViewById(R.id.buyer_payment_SPN_month);

        buyer_payment_BTN_pay = findViewById(R.id.buyer_payment_BTN_pay);
        buyer_payment_BTN_cancel = findViewById(R.id.buyer_payment_BTN_cancel);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // We are not preforming paying in the application
    private boolean pay() {
        String address = buyer_payment_ETXT_address.getText().toString();
        String id = buyer_payment_ETXT_id.getText().toString();
        String full_name = buyer_payment_ETXT_full_name.getText().toString();
        String credit_card = buyer_payment_ETXT_credit_card.getText().toString();
        String cvv = buyer_payment_ETXT_cvv.getText().toString();

        if (address.matches("") || id.matches("") || full_name.matches("") || credit_card.matches("") || cvv.matches("")) {
            return false;
        } else {
            return true;
        }
    }


}
