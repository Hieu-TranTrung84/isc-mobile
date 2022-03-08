package edu.hieutt.majorshop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import edu.hieutt.majorshop.adapter.ShopAdapter;
import edu.hieutt.majorshop.model.Shop;

public class MainActivity extends AppCompatActivity implements ShopAdapter.IClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Major Shop");
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#1761A0"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        List<Shop> list = getShopData();
        initRecyclerView(list);
    }

    private void initRecyclerView(List<Shop> list) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ShopAdapter adapter = new ShopAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }

    private List<Shop> getShopData() {
        InputStream is = getResources().openRawResource(R.raw.shop);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {

        }

        String jsonStr = writer.toString();
        Gson gson = new Gson();
        Shop[] shops = gson.fromJson(jsonStr, Shop[].class);
        List<Shop> restList = Arrays.asList(shops);

        return restList;
    }

    @Override
    public void onItemClick(Shop shop) {
        Intent intent = new Intent(MainActivity.this, ShopMenuActivity.class);
        intent.putExtra("shop", shop);
        startActivity(intent);
    }
}