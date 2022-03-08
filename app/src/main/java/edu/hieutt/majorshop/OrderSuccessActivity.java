package edu.hieutt.majorshop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.hieutt.majorshop.model.Shop;

public class OrderSuccessActivity extends AppCompatActivity {
    private TextView btn_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);
        Shop shop = getIntent().getParcelableExtra("shop");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(shop.getName());
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#1761A0"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setDisplayHomeAsUpEnabled(false);
        btn_done = findViewById(R.id.btn_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}