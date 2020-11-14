package com.example.productstore;

import androidx.annotation.DrawableRes;

public class ProductItem {
    int id;
    @DrawableRes int resId;
    String title;
    String prize;
    String description;
    boolean favorite;
    boolean inCart;

    ProductItem(int id,@DrawableRes int resId, String title, String prize, String description, boolean favorite, boolean inCart) {
        this.id = id;
        this.resId = resId;
        this.title = title;
        this.prize = prize;
        this.description = description;
        this.favorite = favorite;
        this.inCart = inCart;
    }
}
