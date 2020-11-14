package com.example.productstore;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";

    // View name of the header title. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_TITLE = "detail:header:title";
    public static final String IMAGE_RESOURCE_DRAWABLE = "image_resource_drawable";
    public static final String TITLE_NAME = "title_name";
    public static final String FAV_PRODUCT = "fav_product";
    public static final String IN_CART = "in_cart";
    public static final String CART_BUTTON = "cart_button";
    public static final String FAVORITE_BUTTON = "favorite_button";
    public static final String POSITION_PRODUCT_ITEM = "position_product_item";
    DatabaseHelper databaseHelper;
    ArrayList<ProductItem> productItems;

    AppBarLayout appBarLayout;
    ImageView image;
    TextView titleTextView;
    ViewGroup layoutConstraint, layoutLinear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        databaseHelper = new DatabaseHelper(this);

        productItems = databaseHelper.getAllText();


        int position = getIntent().getIntExtra(POSITION_PRODUCT_ITEM, 0);

        ProductItem currentItem = productItems.get(position);

        appBarLayout = findViewById(R.id.app_bar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        image = findViewById(R.id.productDetailImageView);
        titleTextView = findViewById(R.id.detailTitleTextView);
        layoutConstraint = findViewById(R.id.layoutConstraint);
        layoutLinear = findViewById(R.id.layoutLinear);
        FloatingActionButton fab = findViewById(R.id.fab);
        ExtendedFloatingActionButton extendedFab = findViewById(R.id.extendedFab);
        setFavoriteFabDrawable(currentItem.favorite, fab);
        setCartFabDrawable(currentItem.inCart, extendedFab);

        setSupportActionBar(toolbar);
        toolBarLayout.setTitle(currentItem.title);
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.itemTextColor, typedValue, true);
        toolBarLayout.setExpandedTitleColor(typedValue.data);

        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {

            if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                //  Collapsed
                extendedFab.extend();
                titleTextView.animate()
                        .translationY(-50 * ((getResources().getDisplayMetrics().densityDpi + 0F) / DisplayMetrics.DENSITY_DEFAULT))
                        .setDuration(100)
                        .start();
                titleTextView.postOnAnimation(() -> titleTextView.setVisibility(View.GONE));

            } else {
                //Expanded
                titleTextView.setVisibility(View.VISIBLE);
                titleTextView.animate()
                        .translationY(0)
                        .setDuration(100)
                        .start();

                extendedFab.shrink();

            }
        });


        image.setImageResource(currentItem.resId);
        titleTextView.setText(currentItem.title);

        // BEGIN_INCLUDE(detail_set_view_name)
        /*
         * Set the name of the view's which will be transition to, using the static values above.
         * This could be done in the layout XML, but exposing it via static variables allows easy
         * querying from other Activities
         */
        ViewCompat.setTransitionName(image, VIEW_NAME_HEADER_IMAGE);
        ViewCompat.setTransitionName(layoutLinear, VIEW_NAME_HEADER_TITLE);
        ViewCompat.setTransitionName(extendedFab, CART_BUTTON);
        ViewCompat.setTransitionName(fab, FAVORITE_BUTTON);
        // END_INCLUDE(detail_set_view_name)


        fab.setOnClickListener(v -> {

            currentItem.favorite = !currentItem.favorite;

            boolean flag = databaseHelper.changeValuesAtPos(currentItem.id, currentItem);

            if (flag) {
                setFavoriteFabDrawable(currentItem.favorite, fab);
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }

        });

        extendedFab.setOnClickListener(v -> {
            String extendedFabText;

            if (currentItem.inCart) extendedFabText = "Remove from Cart";
            else extendedFabText = "Add to Cart";

            extendedFab.setText(extendedFabText);

            currentItem.inCart = !currentItem.inCart;
            boolean flag = databaseHelper.changeValuesAtPos(currentItem.id, currentItem);

            if (flag) {
                setCartFabDrawable(currentItem.inCart, extendedFab);
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }

        });

    }

    void setCartFabDrawable(boolean inCart, ExtendedFloatingActionButton extendedFab) {
        if (inCart)
            extendedFab.setIcon(ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_remove_shopping_cart_24
            ));
        else
            extendedFab.setIcon(ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_add_shopping_cart_24
            ));
    }

    void setFavoriteFabDrawable(boolean favorite, FloatingActionButton fab) {
        if (favorite)
            fab.setImageDrawable(ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite_24
            ));
        else
            fab.setImageDrawable(ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite_border_24
            ));
    }
}