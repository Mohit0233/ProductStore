package com.example.productstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Button submitButton = findViewById(R.id.submitButton);
        ImageView overlayImageView = findViewById(R.id.overlayImageView);
        EditText name, phoneNo, address;
        name = findViewById(R.id.nameEditText);
        phoneNo = findViewById(R.id.phoneEditText);
        address = findViewById(R.id.addressEditText);

        submitButton.setOnClickListener(v -> {
            if (
                    name.getText().toString().matches("")
                            || phoneNo.getText().toString().matches("")
                            || address.getText().toString().matches("")
            ) {
                Snackbar.make(name.getRootView(), R.string.fill_form_properly, Snackbar.LENGTH_SHORT)
                        .show();
            } else {
                submitButton.setVisibility(View.GONE);
                overlayImageView.setVisibility(View.VISIBLE);
            }
            ArrayList<ProductItem> arrayList = databaseHelper.getAllText();
            int h = arrayList.size();
            for (int i = 0; i < arrayList.size(); i ++) {
                    arrayList.get(i).inCart = false;
                    databaseHelper.changeValuesAtPos(i,arrayList.get(i));
            }
        });
    }
}