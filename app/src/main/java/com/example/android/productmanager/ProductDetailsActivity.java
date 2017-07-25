package com.example.android.productmanager;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.productmanager.data.ProductManagerContract;
import com.example.android.productmanager.dialogs.DeleteProductAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {

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
    @BindView(R.id.product_decrease)
    TextView decrementView;
    @BindView(R.id.product_increase)
    TextView incrementView;
    @BindView(R.id.product_order_more)
    TextView orderView;
    @BindView(R.id.delete_product)
    TextView deleteView;

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

    // Variables to store the ID of the product, supplier and category to append on the Uri.
    int supplierID;
    int categoryID;
    int productID;

    // The product quantity variables. To be stored globally as it can be altered and db may need to
    // be updated with latest changes.
    private int quantity;
    private String quantityUnit;

    // Supplier email variable. To be used when the order button is chosen to be appended to the
    // intent. Also global String variable to store the product name to be set as the email subject.
    private String supplierEmail;
    private String emailSubject;

    // Variable to store the title of the dialog which is called when the user presses the
    // delete button. Another to confirm deletion if so. And one more if deletion is cancelled.
    private String dialogTitle;
    private String deleteConfirmed;
    private String deleteCancelled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.product_details));

        actionBar.setDisplayHomeAsUpEnabled(true);

        productID = getIntent().getIntExtra("productID", 0);

        ButterKnife.bind(this);

        setProductDetails();
        setCategoryDetails();
        setSupplierDetails();

        incrementView.setOnClickListener(this);
        decrementView.setOnClickListener(this);
        orderView.setOnClickListener(this);
        deleteView.setOnClickListener(this);
    }

    /**
     * A method to set all the product details.
     */
    private void setProductDetails() {
        Uri uri = ContentUris.withAppendedId(ProductManagerContract.ProductEntry.CONTENT_URI, productID);

        Cursor cursor = getContentResolver().query(uri, PRODUCT_ENTRY_COLUMNS, null, null, null);

        cursor.moveToFirst();

        supplierID = cursor.getInt(cursor.getColumnIndex(ProductManagerContract.ProductEntry.FK_SUPPLIER_ID));
        categoryID = cursor.getInt(cursor.getColumnIndex(ProductManagerContract.ProductEntry.FK_CATEGORY_ID));

        String productName = cursor.getString(cursor.getColumnIndex(ProductManagerContract.ProductEntry.NAME));
        float price = cursor.getFloat(cursor.getColumnIndex(ProductManagerContract.ProductEntry.SALE_PRICE));
        quantity = cursor.getInt(cursor.getColumnIndex(ProductManagerContract.ProductEntry.QUANTITY));
        quantityUnit = cursor.getString(cursor.getColumnIndex(ProductManagerContract.ProductEntry.QUANTITY_UNIT));

        String imageFileName = cursor.getString(cursor.getColumnIndex(ProductManagerContract.ProductEntry.PIC_ID));
        Uri imageUri = Uri.parse(imageFileName);

        String productQuantity = getString(R.string.product_quantity, quantity, quantityUnit);
        String productPrice = getString(R.string.product_price, price, quantityUnit);

        nameView.setText(productName);
        imageView.setImageURI(imageUri);
        priceView.setText(productPrice);
        quantityView.setText(productQuantity);

        emailSubject = getString(R.string.email_subject, productName);
        dialogTitle = getString(R.string.product_delete_dialog_title, productName);
        deleteConfirmed = getString(R.string.product_delete_confirmation, productName);
        deleteCancelled = getString(R.string.product_delete_rejection, productName);

        if (quantity < 10) {
            quantityView.setTextColor(Color.RED);
        } else {
            quantityView.setTextColor(Color.BLACK);
        }

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
        supplierEmail = cursor.getString(cursor.getColumnIndex(ProductManagerContract.SupplierEntry.EMAIL));

        supplierView.setText(supplierName);

        cursor.close();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.product_increase:
                quantity++;
                updateQuantity();
                break;
            case R.id.product_decrease:
                if (quantity > 0) {
                    quantity--;
                }
                updateQuantity();
                break;
            case R.id.product_order_more:
                makeOrder();
                break;
            case R.id.delete_product:
                deleteProduct();
                break;
        }
    }

    /**
     * Quick method to update the quantity with the new value and also check if quantity is less
     * than 10 and set the text color to warn the user. Then update the db with the new value too.
     */
    public void updateQuantity() {
        String productQuantity = getString(R.string.product_quantity, quantity, quantityUnit);
        quantityView.setText(productQuantity);

        if (quantity < 10) {
            quantityView.setTextColor(Color.RED);
        } else {
            quantityView.setTextColor(Color.BLACK);
        }

        ContentValues values = new ContentValues();
        values.put(ProductManagerContract.ProductEntry.QUANTITY, quantity);

        Uri uri = ContentUris.withAppendedId(ProductManagerContract.ProductEntry.CONTENT_URI, productID);

        getContentResolver().update(uri, values, ProductManagerContract.ProductEntry.QUANTITY, null);

        if (quantity == 0) {
            Snackbar snackbar = Snackbar.make(quantityView, R.string.order_prompt, Snackbar.LENGTH_LONG);
            snackbar.setAction(getString(R.string.snackbar_action_message), new OrderProductListener());
            snackbar.show();
        }
    }

    /**
     * Email intent which let's user select from their email apps to make a new order. The Subject
     * line is preset with the name of the product that would be ordered.
     */
    public void makeOrder() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + supplierEmail));
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        startActivity(Intent.createChooser(intent, getString(R.string.email_intent_chooser_header)));
    }

    /**
     * Custom listener for the snackbar which is displayed when the quantity is 0.
     * The makeOrder() method is then called to launch the email intent.
     */
    private class OrderProductListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            makeOrder();
        }
    }

    /**
     * A method which first opens a dialog to confirm whether the user wants to delete the product.
     * If they choose yet, product will be deleted and user will be returned to the MainActivity to
     * be met with the updated product list.
     */
    public void deleteProduct() {

        DeleteProductAlertDialog dialog = new DeleteProductAlertDialog(this, productID, deleteConfirmed, deleteCancelled, dialogTitle);
        dialog.show();
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
