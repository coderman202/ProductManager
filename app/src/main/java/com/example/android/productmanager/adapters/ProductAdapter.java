package com.example.android.productmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.productmanager.ProductDetailsActivity;
import com.example.android.productmanager.R;
import com.example.android.productmanager.data.ProductManagerContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Reggie on 22/07/2017.
 * Custom adapter to handle the list of products
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements View.OnClickListener {

    private Cursor cursor;

    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_name) TextView nameView;
        @BindView(R.id.product_image) ImageView imageView;
        @BindView(R.id.product_quantity) TextView quantityView;
        @BindView(R.id.product_price) TextView priceView;
        @BindView(R.id.product_sale_button) TextView saleView;
        @BindView(R.id.product_details_button) TextView detailsView;


        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ProductAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if ( null == cursor ) return 0;
        cursor.moveToFirst();
        return cursor.getCount();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        cursor.moveToPosition(position);

        String productName = cursor.getString(cursor.getColumnIndex(ProductManagerContract.ProductEntry.NAME));
        float price = cursor.getFloat(cursor.getColumnIndex(ProductManagerContract.ProductEntry.SALE_PRICE));
        int quantity = cursor.getInt(cursor.getColumnIndex(ProductManagerContract.ProductEntry.QUANTITY));
        String quantityUnit = cursor.getString(cursor.getColumnIndex(ProductManagerContract.ProductEntry.QUANTITY_UNIT));

        // Get the image resource id from the image file name String stored in the db.
        String imageFileName = cursor.getString(cursor.getColumnIndex(ProductManagerContract.ProductEntry.PIC_ID));
        int productImageResID = context.getResources().getIdentifier(imageFileName, "drawable", context.getPackageName());

        String productQuantity = context.getString(R.string.product_quantity, quantity, quantityUnit);
        String productPrice = context.getString(R.string.product_price, price, quantityUnit);
        String sellItem = context.getString(R.string.product_sale, quantityUnit);

        holder.nameView.setText(productName);
        holder.imageView.setImageResource(productImageResID);
        holder.priceView.setText(productPrice);
        holder.quantityView.setText(productQuantity);
        holder.saleView.setText(sellItem);

        if (quantity < 10) {
            holder.quantityView.setTextColor(Color.RED);
        } else {
            holder.quantityView.setTextColor(Color.BLACK);
        }

        holder.detailsView.setOnClickListener(this);

    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.product_details_button:
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                context.startActivity(intent);
        }

    }


}