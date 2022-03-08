package edu.hieutt.majorshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.hieutt.majorshop.adapter.ShopMenuAdapter;
import edu.hieutt.majorshop.model.Menu;
import edu.hieutt.majorshop.model.Shop;

public class ShopMenuActivity extends AppCompatActivity implements ShopMenuAdapter.IClickListener {
    private Shop shop;
    private List<Menu> menuList;
    private List<Menu> itemsInCartList;
    private RecyclerView recyclerView;
    private int totalItemInCart;
    private TextView btn_checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_menu);
        shop = getIntent().getParcelableExtra("shop");
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#1761A0"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(shop.getName());
        actionBar.setDisplayHomeAsUpEnabled(true);
        menuList = shop.getMenus();
        btn_checkout = findViewById(R.id.btn_checkout);
        initRecyclerView();

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemsInCartList != null && itemsInCartList.size() <= 0) {
                    Toast.makeText(ShopMenuActivity.this, "Please Add Some Items In Cart", Toast.LENGTH_SHORT).show();
                    return;
                }
                shop.setMenus(itemsInCartList);
                Intent intent = new Intent(ShopMenuActivity.this, PlaceOrderActivity.class);
                intent.putExtra("shop", shop);
                startActivityForResult(intent, 1000);
            }
        });

    }

    public void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ShopMenuAdapter adapter = new ShopMenuAdapter(menuList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAddToCartClick(Menu menu) {
        if (itemsInCartList == null) {
            itemsInCartList = new ArrayList<>();
        }
        itemsInCartList.add(menu);
        totalItemInCart = 0;
        for (Menu m : itemsInCartList) {
            totalItemInCart = totalItemInCart + menu.getTotalInCart();
        }
        btn_checkout.setText("Checkout (" + totalItemInCart + ") items");
    }

    @Override
    public void onUpdateCartClick(Menu menu) {
        if (itemsInCartList.contains(menu)) {
            int index = itemsInCartList.indexOf(menu);
            itemsInCartList.remove(index);
            itemsInCartList.add(index, menu);

            totalItemInCart = 0;
            for (Menu m : itemsInCartList) {
                totalItemInCart = totalItemInCart + menu.getTotalInCart();
            }
            btn_checkout.setText("Checkout (" + totalItemInCart + ") items");
        }
    }

    @Override
    public void onRemoveFromCartClick(Menu menu) {
        if (itemsInCartList.contains(menu)) {
            itemsInCartList.remove(menu);

            totalItemInCart = 0;
            for (Menu m : itemsInCartList) {
                totalItemInCart = totalItemInCart + menu.getTotalInCart();
            }
            btn_checkout.setText("Checkout (" + totalItemInCart + ") items");
        }
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            //
            finish();
        }
    }
}