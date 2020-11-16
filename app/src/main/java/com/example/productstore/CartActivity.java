package com.example.productstore;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class CartActivity extends AppCompatActivity implements ProductCartAdapter.OnItemClickListener {

    private Button checkoutButton;
    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    TextView nothingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        
        checkoutButton = findViewById(R.id.checkoutButton);
        recyclerView = findViewById(R.id.productsCartRecyclerView);
        nothingTextView = findViewById(R.id.nothingTextView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        databaseHelper = new DatabaseHelper(this);

        ProductCartAdapter adapter = new ProductCartAdapter(this, this);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

        if (databaseHelper.getCartText().size() == 0) {
            checkoutButton.setVisibility(View.GONE);
            nothingTextView.setVisibility(View.VISIBLE);
        }

        checkoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CheckoutActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
        if (databaseHelper.getCartText().size() == 0) {
            checkoutButton.setVisibility(View.GONE);
            nothingTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(int position, ImageView imageView, TextView textView, ViewGroup cardLayout, int resId) {

        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra(ProductDetailActivity.POSITION_PRODUCT_ITEM, position);

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                new Pair<>(imageView,
                        ProductDetailActivity.VIEW_NAME_HEADER_IMAGE),
                new Pair<>(cardLayout,
                        ProductDetailActivity.VIEW_NAME_HEADER_TITLE)
        );
        startActivity(intent, options.toBundle());

    }

    @Override
    public void onButtonClick() {
        if (databaseHelper.getCartText().size() == 0) {
            checkoutButton.setVisibility(View.GONE);
            nothingTextView.setVisibility(View.VISIBLE);
        }
    }

}