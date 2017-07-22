package com.example.android.productmanager;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.product_details));

        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
