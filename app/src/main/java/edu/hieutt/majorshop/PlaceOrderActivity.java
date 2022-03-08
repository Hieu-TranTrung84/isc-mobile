package edu.hieutt.majorshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import edu.hieutt.majorshop.adapter.PlaceOrderAdapter;
import edu.hieutt.majorshop.model.Menu;
import edu.hieutt.majorshop.model.Shop;

public class PlaceOrderActivity extends AppCompatActivity {
    private EditText edt_name, edt_address, edt_city, edt_state, edt_card_number, edt_card_pin;
    private RecyclerView cart_items_recyclerView;
    private TextView btn_place_order, tv_total_amount, tv_subtotal_amount, tv_delivery_charge_amount;
    private boolean isDeliveryOn;
    private PlaceOrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        Shop shop = getIntent().getParcelableExtra("shop");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(shop.getName());
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#1761A0"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mapping();

        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlaceOrderButtonClick(shop);
            }
        });
        initRecyclerView(shop);
        calculateTotalAmount(shop);
    }

    private void initRecyclerView(Shop shop) {
        cart_items_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlaceOrderAdapter(shop.getMenus());
        cart_items_recyclerView.setAdapter(adapter);
    }

    private void calculateTotalAmount(Shop shop) {
        float subTotalAmount = 0f;

        for (Menu m : shop.getMenus()) {
            subTotalAmount += m.getPrice() * m.getTotalInCart();
        }

        tv_subtotal_amount.setText("$" + String.format("%.2f", subTotalAmount));
        if (isDeliveryOn) {
            tv_delivery_charge_amount.setText("$" + String.format("%.2f", shop.getDelivery_charge()));
            subTotalAmount += shop.getDelivery_charge();
        }
        tv_total_amount.setText("$" + String.format("%.2f", subTotalAmount));
    }

    public void onPlaceOrderButtonClick(Shop shop) {
        if (TextUtils.isEmpty(edt_name.getText().toString())) {
            edt_name.setError("Please enter name ");
            return;
        } else if (TextUtils.isEmpty(edt_address.getText().toString())) {
            edt_address.setError("Please enter address ");
            return;
        } else if (TextUtils.isEmpty(edt_city.getText().toString())) {
            edt_city.setError("Please enter city ");
            return;
        } else if (TextUtils.isEmpty(edt_state.getText().toString())) {
            edt_state.setError("Please enter zip ");
            return;
        } else if (TextUtils.isEmpty(edt_card_number.getText().toString())) {
            edt_card_number.setError("Please enter card number ");
            return;
        } else if (TextUtils.isEmpty(edt_card_pin.getText().toString())) {
            edt_card_pin.setError("Please enter card pin ");
            return;
        }

        Intent i = new Intent(PlaceOrderActivity.this, OrderSuccessActivity.class);
        i.putExtra("shop", shop);
        startActivityForResult(i, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1000) {
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void mapping() {
        edt_name = findViewById(R.id.edt_name);
        edt_address = findViewById(R.id.edt_address);
        edt_state = findViewById(R.id.edt_state);
        edt_city = findViewById(R.id.edt_city);
        edt_card_number = findViewById(R.id.edt_card_number);
        edt_card_pin = findViewById(R.id.edt_card_pin);
        btn_place_order = findViewById(R.id.btn_place_order);
        tv_total_amount = findViewById(R.id.tv_total_amount);
        tv_subtotal_amount = findViewById(R.id.tv_subtotal_amount);
        cart_items_recyclerView = findViewById(R.id.cart_items_recyclerView);
        tv_delivery_charge_amount = findViewById(R.id.tv_delivery_charge_amount);
    }
}