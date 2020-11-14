package com.example.productstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.jetbrains.annotations.NotNull;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {

        ProductFragment productFragment = (ProductFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        assert productFragment != null;
        int itemId = item.getItemId();
        if (itemId == R.id.changeLayoutOption) {
            productFragment.changeLayoutOption();
        } else if (itemId == R.id.favOption) {
            productFragment.favOption();
        } else if (itemId == R.id.cartOption) {
            productFragment.cartOption();
        }
        return super.onOptionsItemSelected(item);

    }
}
