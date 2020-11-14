package com.example.productstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private final OnItemClickListener listener;
    private final Context context;
    private final DatabaseHelper databaseHelper;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public final class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        ImageView image;
        TextView title, description, prize;
        ViewGroup layoutCardView, linearLayout;
        ImageButton cartImageButton, favoriteImageButton;

        public MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.productImageView);
            title = view.findViewById(R.id.titleTextView);
            prize = view.findViewById(R.id.productPrizeTextView);
            description = view.findViewById(R.id.descriptionTextView);
            layoutCardView = view.findViewById(R.id.layoutCardView);
            linearLayout = view.findViewById(R.id.linearLayout);
            cartImageButton = view.findViewById(R.id.cartImageButton);
            favoriteImageButton = view.findViewById(R.id.favButton);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                listener.onItemClick(position, image, title, layoutCardView, linearLayout, cartImageButton, favoriteImageButton, databaseHelper.getAllText().get(position).resId);
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductAdapter(Context context, OnItemClickListener listener) {
        this.listener = listener;
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    // Create new views (invoked by the layout manager)
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent,
                                           int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item_view, parent, false);
        return new MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ProductItem currentItem = databaseHelper.getAllText().get(position);
        holder.image.setImageResource(currentItem.resId);
        holder.title.setText(currentItem.title);
        holder.prize.setText(currentItem.prize);
        holder.description.setText(currentItem.description);
        changeCartDrawable(currentItem.inCart, holder);
        changeFavoriteDrawable(currentItem.favorite, holder);

        holder.favoriteImageButton.setOnClickListener(v -> {

            currentItem.favorite = !currentItem.favorite;

            boolean flag = databaseHelper.changeValuesAtPos(currentItem.id, currentItem);

            if (flag) {
                changeFavoriteDrawable(currentItem.favorite, holder);
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        holder.cartImageButton.setOnClickListener(v -> {
            currentItem.inCart = !currentItem.inCart;
            boolean flag = databaseHelper.changeValuesAtPos(currentItem.id, currentItem);

            if (flag) {
                changeCartDrawable(currentItem.inCart, holder);
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void changeCartDrawable(boolean inCart, MyViewHolder holder) {
        if (inCart)
            holder.cartImageButton.setImageDrawable(ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_baseline_remove_shopping_cart_24
            ));
        else {
            holder.cartImageButton.setImageDrawable(ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_baseline_add_shopping_cart_24
            ));
        }
    }

    void changeFavoriteDrawable(boolean favorite, MyViewHolder holder) {
        if (favorite) {
            holder.favoriteImageButton.setImageDrawable(ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_baseline_favorite_24
            ));
        } else {
            holder.favoriteImageButton.setImageDrawable(ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_baseline_favorite_border_24
            ));
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return databaseHelper.getAllText().size();
    }

    interface OnItemClickListener {
        void onItemClick(int position, ImageView imageView, TextView textView, ViewGroup layoutCardView, ViewGroup linearLayout, ImageButton cartImageButton, ImageButton favoriteImageButton, int resId);
    }
}