package com.example.productstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements ProductFavoriteAdapter.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        RecyclerView recyclerView = findViewById(R.id.productsFavoriteRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ProductFavoriteAdapter adapter = new ProductFavoriteAdapter(this, this);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);

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

}