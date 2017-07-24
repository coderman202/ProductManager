package com.example.android.productmanager.adapters;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.productmanager.ProductDetailsActivity;
import com.example.android.productmanager.R;
import com.example.android.productmanager.data.ProductManagerContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Reggie on 22/07/2017.
 * Custom adapter to handle the list of products
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Cursor cursor;

    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_name) TextView nameView;
        @BindView(R.id.product_image) ImageView imageView;
        @BindView(R.id.product_quantity) TextView quantityView;
        @BindView(R.id.product_price) TextView priceView;
        @BindView(R.id.product_sale_button) TextView saleView;
        @BindView(R.id.product_details_button) TextView detailsView;

        // This will be used for the quantity to allow the decrease to work in the onClick() method.
        int stockLevel;

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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        cursor.moveToPosition(position);

        final int productID = cursor.getInt(cursor.getColumnIndex(ProductManagerContract.ProductEntry.PK_PRODUCT_ID));

        final String productName = cursor.getString(cursor.getColumnIndex(ProductManagerContract.ProductEntry.NAME));
        final float price = cursor.getFloat(cursor.getColumnIndex(ProductManagerContract.ProductEntry.SALE_PRICE));
        int quantity = cursor.getInt(cursor.getColumnIndex(ProductManagerContract.ProductEntry.QUANTITY));
        final String quantityUnit = cursor.getString(cursor.getColumnIndex(ProductManagerContract.ProductEntry.QUANTITY_UNIT));

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

        holder.detailsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("productID", productID);
                context.startActivity(intent);
            }
        });

        // Now decrement the quantity and make sure it cannot become negative.
        holder.stockLevel = quantity;
        holder.saleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.stockLevel > 0) {
                    holder.stockLevel--;
                } else {
                    Toast.makeText(context, context.getString(R.string.order_prompt_message, productName), Toast.LENGTH_LONG).show();
                }
                String productQuantity = context.getString(R.string.product_quantity, holder.stockLevel, quantityUnit);
                holder.quantityView.setText(productQuantity);

                if (holder.stockLevel < 10) {
                    holder.quantityView.setTextColor(Color.RED);
                } else {
                    holder.quantityView.setTextColor(Color.BLACK);
                }

                ContentValues values = new ContentValues();
                values.put(ProductManagerContract.ProductEntry.QUANTITY, holder.stockLevel);

                Uri uri = ContentUris.withAppendedId(ProductManagerContract.ProductEntry.CONTENT_URI, productID);

                context.getContentResolver().update(uri, values, ProductManagerContract.ProductEntry.QUANTITY, null);
            }
        });

    }

    public void swapCursor(Cursor newCursor) {
        cursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return cursor;
    }

}
