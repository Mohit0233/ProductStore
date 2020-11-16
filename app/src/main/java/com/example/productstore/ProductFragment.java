package com.example.productstore;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment implements ProductAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ProductAdapter mAdapter;
    boolean isGrid = true;
    private StaggeredGridLayoutManager layoutManager;
    private ConstraintLayout outerConstraintLayout;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.product_fragment, container, false);
        recyclerView = root.findViewById(R.id.productsRecyclerView);
        outerConstraintLayout = root.findViewById(R.id.outerConstraintLayout);

        databaseHelper = new DatabaseHelper(requireContext());

        if (!databaseHelper.checkDataBase(requireContext())) {
            databaseHelper.addText(0, R.drawable.phone1, "Phone 1", "₹88000.00", "This is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the product", false, false);
            databaseHelper.addText(1, R.drawable.phone_2, "Phone 2", "₹14000.00", "This is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the product", false, false);
            databaseHelper.addText(2, R.drawable.phone_3, "Phone 3", "₹24000.00", "This is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the product", false, false);
            databaseHelper.addText(3, R.drawable.phone_4, "Phone 4", "₹34000.00", "This is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productT productThis is the description of the productT productThis is the description of the productT productThis is the description of the productT productThis is the description of the productT productThis is the description of the productT productThis is the description of the productT productThis is the description of the productT productThis is the description of the productT productThis is the description of the productT productThis is the description of the productT productThis is the description of the productT productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the product", false, false);
            databaseHelper.addText(4, R.drawable.phone_5, "Phone 5", "₹44000.00", "This is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the product", false, false);
            databaseHelper.addText(5, R.drawable.phone_6, "Phone 6", "₹54000.00", "This is the description of the productThis is the description of the productThis is the description of the the productThis is the description of the productThis is the description of the productThis is the description of the product", false, false);
            databaseHelper.addText(6, R.drawable.phone_7, "Phone 7", "₹64000.00", "This is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the product", false, false);
            databaseHelper.addText(7, R.drawable.phone_8, "Phone 8", "₹74000.00", "This is the description of the productThis is the description of the productThis is the description of the the product", false, false);
            databaseHelper.addText(8, R.drawable.ear_buds, "Ear buds", "₹84000.00", "This is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the productThis is the description of the product", false, false);
            databaseHelper.addText(9, R.drawable.ear_bud_2, "Ear bud 2", "₹94000.00", "This is the description of the productThis is the description of the productThis is the description of", false, false);
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ProductAdapter(requireContext(),this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position, ImageView imageView, @NotNull TextView textView, ViewGroup layoutCardView, ViewGroup linearLayout, ImageButton cartImageButton, ImageButton favoriteImageButton, int resId) {

        Intent intent = new Intent(requireContext(), ProductDetailActivity.class);
        /*intent.putExtra(ProductDetailActivity.IMAGE_RESOURCE_DRAWABLE, resId);
        intent.putExtra(ProductDetailActivity.TITLE_NAME, textView.getText().toString());
        intent.putExtra(ProductDetailActivity.FAV_PRODUCT, productItems.get(position).favorite);
        intent.putExtra(ProductDetailActivity.IN_CART, productItems.get(position).inCart);*/
        intent.putExtra(ProductDetailActivity.POSITION_PRODUCT_ITEM, position);
        // create the transition animation - the images in the layouts
        // of both activities are defined with android:transitionName="robot"
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(requireActivity(),
                new Pair<>(imageView,
                        ProductDetailActivity.VIEW_NAME_HEADER_IMAGE),
                new Pair<>(linearLayout,
                        ProductDetailActivity.VIEW_NAME_HEADER_TITLE),
                new Pair<>(cartImageButton,
                        ProductDetailActivity.CART_BUTTON),
                new Pair<>(favoriteImageButton,
                        ProductDetailActivity.FAVORITE_BUTTON)
        );

        // start the new activity
        startActivity(intent, options.toBundle());

    }

    void changeLayoutOption() {
        if (isGrid)
            layoutManager.setSpanCount(2);
        else
            layoutManager.setSpanCount(1);
        mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        isGrid = !isGrid;
    }

    void favOption() {
        Intent intent = new Intent(requireActivity(), FavoriteActivity.class);
        startActivity(intent);
    }

    void cartOption() {
        Intent intent = new Intent(requireActivity(), CartActivity.class);
        startActivity(intent);
    }
}