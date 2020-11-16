package com.example.productstore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database_name";
    public static final String TABLE_NAME = "table_name";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(@NotNull SQLiteDatabase db) {
        String createTable = "create table " + TABLE_NAME + "(id INTEGER PRIMARY KEY, resId INTEGER, title TEXT, prize TEXT, description TEXT, favorite NUMERIC NOT NULL default 0, cart NUMERIC NOT NULL default 0)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(@NotNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addText(int id, @DrawableRes int resId, String title, String prize, String description, boolean favorite, boolean cart) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("resId", resId);
        contentValues.put("title", title);
        contentValues.put("prize", prize);
        contentValues.put("description", description);
        contentValues.put("favorite", favorite);
        contentValues.put("cart", cart);
        try {
            sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ProductItem> getAllText() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<ProductItem> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(new ProductItem(
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))),
                            Integer.parseInt(cursor.getString(cursor.getColumnIndex("resId"))),
                            cursor.getString(cursor.getColumnIndex("title")),
                            cursor.getString(cursor.getColumnIndex("prize")),
                            cursor.getString(cursor.getColumnIndex("description")),
                            (Integer.parseInt(cursor.getString(cursor.getColumnIndex("favorite"))) == 1),
                            (Integer.parseInt(cursor.getString(cursor.getColumnIndex("cart"))) == 1)
                    )
            );
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }

    public boolean changeValuesAtPos(int id, @NotNull ProductItem productItem) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int cartInt, favoriteInt;
        if (productItem.inCart) cartInt = 1;
        else cartInt = 0;
        if (productItem.favorite) favoriteInt = 1;
        else favoriteInt = 0;
        contentValues.put("id", id);
        contentValues.put("resId", productItem.resId);
        contentValues.put("title", productItem.title);
        contentValues.put("prize", productItem.prize);
        contentValues.put("description", productItem.description);
        contentValues.put("favorite", favoriteInt);
        contentValues.put("cart", cartInt);
        long result = sqLiteDatabase.update(TABLE_NAME, contentValues, "id = " + id, null);
        return result != -1;
    }

    public ArrayList<ProductItem> getCartText() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<ProductItem> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME + " WHERE CART = 1 ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(new ProductItem(
                            Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))),
                            Integer.parseInt(cursor.getString(cursor.getColumnIndex("resId"))),
                            cursor.getString(cursor.getColumnIndex("title")),
                            cursor.getString(cursor.getColumnIndex("prize")),
                            cursor.getString(cursor.getColumnIndex("description")),
                            (Integer.parseInt(cursor.getString(cursor.getColumnIndex("favorite"))) == 1),
                            (Integer.parseInt(cursor.getString(cursor.getColumnIndex("cart"))) == 1)
                    )
            );
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }


    public ArrayList<ProductItem> getFavoriteText() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<ProductItem> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME + " WHERE FAVORITE = 1 ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(new ProductItem(
                            Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))),
                            Integer.parseInt(cursor.getString(cursor.getColumnIndex("resId"))),
                            cursor.getString(cursor.getColumnIndex("title")),
                            cursor.getString(cursor.getColumnIndex("prize")),
                            cursor.getString(cursor.getColumnIndex("description")),
                            (Integer.parseInt(cursor.getString(cursor.getColumnIndex("favorite"))) == 1),
                            (Integer.parseInt(cursor.getString(cursor.getColumnIndex("cart"))) == 1)
                    )
            );
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }

    public boolean checkDataBase(@NotNull Context context) {
        SQLiteDatabase checkDB = null;
        Log.e("getDatabasePath()", String.valueOf(context.getDatabasePath(DATABASE_NAME)));
        try {
            checkDB = SQLiteDatabase.openDatabase(String.valueOf(context.getDatabasePath(DATABASE_NAME)), null,
                    SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }
}
