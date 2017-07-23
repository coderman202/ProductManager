package com.example.android.productmanager;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.productmanager.data.ProductManagerContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailsActivity extends AppCompatActivity {

    private ActionBar actionBar;

    @BindView(R.id.product_name)
    TextView nameView;
    @BindView(R.id.product_image)
    ImageView imageView;
    @BindView(R.id.product_quantity)
    TextView quantityView;
    @BindView(R.id.product_price)
    TextView priceView;
    @BindView(R.id.product_supplier)
    TextView supplierView;

    // List of columns to be queried for each table
    public static final String[] PRODUCT_ENTRY_COLUMNS = {
            ProductManagerContract.ProductEntry.PK_PRODUCT_ID,
            ProductManagerContract.ProductEntry.NAME,
            ProductManagerContract.ProductEntry.QUANTITY,
            ProductManagerContract.ProductEntry.QUANTITY_UNIT,
            ProductManagerContract.ProductEntry.SALE_PRICE,
            ProductManagerContract.ProductEntry.PIC_ID,
            ProductManagerContract.ProductEntry.FK_CATEGORY_ID,
            ProductManagerContract.ProductEntry.FK_SUPPLIER_ID
    };

    public static final String[] CATEGORY_ENTRY_COLUMNS = {
            ProductManagerContract.CategoryEntry.PK_CATEGORY_ID,
            ProductManagerContract.CategoryEntry.NAME,
            ProductManagerContract.CategoryEntry.ICON_ID
    };

    public static final String[] SUPPLIER_ENTRY_COLUMNS = {
            ProductManagerContract.SupplierEntry.PK_SUPPLIER_ID,
            ProductManagerContract.SupplierEntry.NAME,
            ProductManagerContract.SupplierEntry.ADDRESS,
            ProductManagerContract.SupplierEntry.EMAIL,
            ProductManagerContract.SupplierEntry.PHONE,
    };

    // Variables to store the ID of the supplier and category to append on the Uri.
    int supplierID;
    int categoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.product_details));

        actionBar.setDisplayHomeAsUpEnabled(true);

        int id = getIntent().getIntExtra("productID", 0);

        ButterKnife.bind(this);

        setProductDetails();
        setCategoryDetails();
        setSupplierDetails();


    }

    /**
     * A method to set all the product details.
     */
    private void setProductDetails() {
        Uri uri = ContentUris.withAppendedId(ProductManagerContract.ProductEntry.CONTENT_URI, 4);

        Cursor cursor = getContentResolver().query(uri, PRODUCT_ENTRY_COLUMNS, null, null, null);

        cursor.moveToFirst();

        supplierID = cursor.getInt(cursor.getColumnIndex(ProductManagerContract.ProductEntry.FK_SUPPLIER_ID));
        categoryID = cursor.getInt(cursor.getColumnIndex(ProductManagerContract.ProductEntry.FK_CATEGORY_ID));

        String productName = cursor.getString(cursor.getColumnIndex(ProductManagerContract.ProductEntry.NAME));
        float price = cursor.getFloat(cursor.getColumnIndex(ProductManagerContract.ProductEntry.SALE_PRICE));
        int quantity = cursor.getInt(cursor.getColumnIndex(ProductManagerContract.ProductEntry.QUANTITY));
        String quantityUnit = cursor.getString(cursor.getColumnIndex(ProductManagerContract.ProductEntry.QUANTITY_UNIT));

        // Get the image resource id from the image file name String stored in the db.
        String imageFileName = cursor.getString(cursor.getColumnIndex(ProductManagerContract.ProductEntry.PIC_ID));
        int productImageResID = getResources().getIdentifier(imageFileName, "drawable", getPackageName());

        String productQuantity = getString(R.string.product_quantity, quantity, quantityUnit);
        String productPrice = getString(R.string.product_price, price, quantityUnit);

        nameView.setText(productName);
        imageView.setImageResource(productImageResID);
        priceView.setText(productPrice);
        quantityView.setText(productQuantity);

        cursor.close();
    }

    /**
     * A method to set all the category details.
     */
    private void setCategoryDetails() {
        Uri uri = ContentUris.withAppendedId(ProductManagerContract.CategoryEntry.CONTENT_URI, categoryID);

        Cursor cursor = getContentResolver().query(uri, CATEGORY_ENTRY_COLUMNS, null, null, null);

        cursor.moveToFirst();

        String categoryName = cursor.getString(cursor.getColumnIndex(ProductManagerContract.CategoryEntry.NAME));

        actionBar.setSubtitle(categoryName);

        cursor.close();
    }

    /**
     * A method to set all the supplier details.
     */
    private void setSupplierDetails() {
        Uri uri = ContentUris.withAppendedId(ProductManagerContract.SupplierEntry.CONTENT_URI, supplierID);

        Cursor cursor = getContentResolver().query(uri, SUPPLIER_ENTRY_COLUMNS, null, null, null);

        cursor.moveToFirst();

        String supplierName = cursor.getString(cursor.getColumnIndex(ProductManagerContract.SupplierEntry.NAME));
        supplierName = getString(R.string.product_supplier, supplierName);

        supplierView.setText(supplierName);

        cursor.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
