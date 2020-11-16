package com.example.productstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ViewHolder> {

    private final OnItemClickListener listener;
    private final Context context;
    private final DatabaseHelper databaseHelper;

    public final class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView title, prize;
        ImageButton remove;
        ConstraintLayout cartLayout;

        public ViewHolder(@NonNull View view) {
            super(view);
            image = view.findViewById(R.id.cartImageView);
            title = view.findViewById(R.id.cartTitleTextView);
            prize = view.findViewById(R.id.cartPrizeTextView);
            remove = view.findViewById(R.id.cartRemoveImageButton);
            cartLayout = view.findViewById(R.id.cartLayout);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position, image, title, cartLayout, databaseHelper.getCartText().get(position).resId);
            }
        }
    }

    public ProductCartAdapter(OnItemClickListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ProductCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_cart_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductItem currentItem = databaseHelper.getCartText().get(position);
        holder.image.setImageResource(currentItem.resId);
        holder.title.setText(currentItem.title);
        holder.prize.setText(currentItem.prize);

        holder.remove.setOnClickListener(v -> {

            currentItem.inCart = false;
            boolean flag = databaseHelper.changeValuesAtPos(currentItem.id, currentItem);
            if (flag) {
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, databaseHelper.getCartText().size());
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
            listener.onButtonClick();
        });
    }

    @Override
    public int getItemCount() {
        return databaseHelper.getCartText().size();
    }

    interface OnItemClickListener {
        void onItemClick(int position, ImageView imageView, TextView textView, ViewGroup layoutCardView, int resId);

        void onButtonClick();
    }

}
