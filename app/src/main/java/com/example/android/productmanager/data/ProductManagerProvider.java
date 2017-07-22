package com.example.android.productmanager.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.android.productmanager.R;

/**
 * Created by Reggie on 21/07/2017.
 * Custom provider class for interacting with ProductManagerDB
 */

public class ProductManagerProvider extends ContentProvider {

    public static final String LOG_TAG = ProductManagerProvider.class.getSimpleName();

    // PRODUCT TABLE CONSTANTS
    private static final int PRODUCT_TABLE = 100;
    private static final int PRODUCT_TABLE_ROW = 101;
    private static final String PATH_PRODUCTS = ProductManagerContract.ProductEntry.PATH;
    private static final String PRODUCT_TABLE_NAME = ProductManagerContract.ProductEntry.TABLE_NAME;
    private static final String PRODUCT_TABLE_LIST_TYPE = ProductManagerContract.ProductEntry.CONTENT_LIST_TYPE;
    private static final String PRODUCT_TABLE_ITEM_TYPE = ProductManagerContract.ProductEntry.CONTENT_ITEM_TYPE;

    // Columns
    private static final String PRODUCT_ID = ProductManagerContract.ProductEntry.PK_PRODUCT_ID;
    private static final String PRODUCT_NAME = ProductManagerContract.ProductEntry.NAME;
    private static final String PRODUCT_SALE_PRICE = ProductManagerContract.ProductEntry.SALE_PRICE;
    private static final String PRODUCT_QUANTITY = ProductManagerContract.ProductEntry.QUANTITY;
    private static final String PRODUCT_QUANTITY_UNIT = ProductManagerContract.ProductEntry.QUANTITY_UNIT;
    private static final String PRODUCT_PIC_ID = ProductManagerContract.ProductEntry.PIC_ID;
    private static final String PRODUCT_CATEGORY_ID = ProductManagerContract.ProductEntry.FK_CATEGORY_ID;
    private static final String PRODUCT_SUPPLIER_ID = ProductManagerContract.ProductEntry.FK_SUPPLIER_ID;

    // CATEGORY TABLE CONSTANTS
    private static final int CATEGORY_TABLE = 200;
    private static final int CATEGORY_TABLE_ROW = 201;
    private static final String PATH_CATEGORY = ProductManagerContract.CategoryEntry.PATH;
    private static final String CATEGORY_TABLE_NAME = ProductManagerContract.CategoryEntry.TABLE_NAME;
    private static final String CATEGORY_TABLE_LIST_TYPE = ProductManagerContract.CategoryEntry.CONTENT_LIST_TYPE;
    private static final String CATEGORY_TABLE_ITEM_TYPE = ProductManagerContract.CategoryEntry.CONTENT_ITEM_TYPE;

    // Columns
    private static final String CATEGORY_ID = ProductManagerContract.CategoryEntry.PK_CATEGORY_ID;
    private static final String CATEGORY_NAME = ProductManagerContract.CategoryEntry.NAME;
    private static final String CATEGORY_ICON_ID = ProductManagerContract.CategoryEntry.ICON_ID;

    // SUPPLIER TABLE CONSTANTS
    private static final int SUPPLIER_TABLE = 300;
    private static final int SUPPLIER_TABLE_ROW = 301;
    private static final String PATH_SUPPLIER = ProductManagerContract.SupplierEntry.PATH;
    private static final String SUPPLIER_TABLE_NAME = ProductManagerContract.SupplierEntry.TABLE_NAME;
    private static final String SUPPLIER_TABLE_LIST_TYPE = ProductManagerContract.SupplierEntry.CONTENT_LIST_TYPE;
    private static final String SUPPLIER_TABLE_ITEM_TYPE = ProductManagerContract.SupplierEntry.CONTENT_ITEM_TYPE;

    // Columns
    private static final String SUPPLIER_ID = ProductManagerContract.SupplierEntry.PK_SUPPLIER_ID;
    private static final String SUPPLIER_NAME = ProductManagerContract.SupplierEntry.NAME;
    private static final String SUPPLIER_ADDRESS = ProductManagerContract.SupplierEntry.ADDRESS;
    private static final String SUPPLIER_EMAIL = ProductManagerContract.SupplierEntry.EMAIL;
    private static final String SUPPLIER_PHONE = ProductManagerContract.SupplierEntry.PHONE;


    // DB helper object for the Product Manager
    private ProductManagerDBHelper dbHelper;

    public static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // URIs for the Product table
        uriMatcher.addURI(ProductManagerContract.CONTENT_AUTHORITY, PATH_PRODUCTS, PRODUCT_TABLE);
        uriMatcher.addURI(ProductManagerContract.CONTENT_AUTHORITY, PATH_PRODUCTS, PRODUCT_TABLE_ROW);

        // URIs for the Category table
        uriMatcher.addURI(ProductManagerContract.CONTENT_AUTHORITY, PATH_CATEGORY, CATEGORY_TABLE);
        uriMatcher.addURI(ProductManagerContract.CONTENT_AUTHORITY, PATH_CATEGORY, CATEGORY_TABLE_ROW);

        // URIs for the Supplier table
        uriMatcher.addURI(ProductManagerContract.CONTENT_AUTHORITY, PATH_SUPPLIER, SUPPLIER_TABLE);
        uriMatcher.addURI(ProductManagerContract.CONTENT_AUTHORITY, PATH_SUPPLIER, SUPPLIER_TABLE_ROW);

    }

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        dbHelper = new ProductManagerDBHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection,
     * selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // This cursor will hold query result.
        Cursor cursor;

        // Making sure the URI is valid
        final int match = uriMatcher.match(uri);
        switch(match){
            case PRODUCT_TABLE:
                cursor = db.query(PRODUCT_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PRODUCT_TABLE_ROW:
                selection = ProductManagerContract.ProductEntry.PK_PRODUCT_ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(PRODUCT_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CATEGORY_TABLE:
                cursor = db.query(CATEGORY_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CATEGORY_TABLE_ROW:
                selection = ProductManagerContract.CategoryEntry.PK_CATEGORY_ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(CATEGORY_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case SUPPLIER_TABLE:
                cursor = db.query(SUPPLIER_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case SUPPLIER_TABLE_ROW:
                selection = ProductManagerContract.SupplierEntry.PK_SUPPLIER_ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(SUPPLIER_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_unsupported_insertion_exception, uri));
        }
        return cursor;
    }

    //region Data insertion methods
    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        // Making sure the URI is valid
        final int match = uriMatcher.match(uri);
        switch (match){
            case PRODUCT_TABLE:
                return insertProduct(uri, values);
            case CATEGORY_TABLE:
                return insertCategory(uri, values);
            case SUPPLIER_TABLE:
                return insertSupplier(uri, values);
            default:
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_unsupported_insertion_exception, uri));
        }
    }

    /**
     * A method to ensure all the values for product to be inserted are correct and valid.
     * @param uri       the uri
     * @param values    the values to be inserted for each column
     * @return          the uri including if all values are valid, otherwise null
     */
    public Uri insertProduct(Uri uri, ContentValues values){

        // Check the product name is not null
        String name = values.getAsString(PRODUCT_NAME);
        if (name == null) {
            throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_product_name_exception));
        }

        // Check the Sale Price is not null and is greater than 0.
        Float salePrice = values.getAsFloat(PRODUCT_SALE_PRICE);
        if(salePrice == null || salePrice < 0){
            throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_product_price_exception));
        }

        // Also ensure quantity can't be less than 0 nor null.
        Integer quantity = values.getAsInteger(PRODUCT_QUANTITY);
        if(quantity == null || quantity < 0){
            throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_product_quantity_exception));
        }

        // Also ensure quantity unit is valid.
        String unit = values.getAsString(PRODUCT_QUANTITY_UNIT);
        if(unit == null || !ProductManagerContract.ProductEntry.isValidUnit(unit)){
            throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_product_quantity_unit_exception));
        }

        // Get writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the new product with the given values
        long id = db.insert(PRODUCT_TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, dbHelper.getContext().getString(R.string.content_uri_insert_failure_exception, uri));
            return null;
        }

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    /**
     * A method to ensure all the values for category to be inserted are correct and valid.
     * @param uri       the uri
     * @param values    the values to be inserted for each column
     * @return          the new URI with the ID (of the newly inserted row) appended at the end, otherwise null
     */
    public Uri insertCategory(Uri uri, ContentValues values){

        // Check the category name is not null
        String name = values.getAsString(CATEGORY_NAME);
        if (name == null) {
            throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_category_name_exception));
        }

        // Get writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the new category with the given values
        long id = db.insert(CATEGORY_TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, dbHelper.getContext().getString(R.string.content_uri_insert_failure_exception, uri));
            return null;
        }

        return ContentUris.withAppendedId(uri, id);
    }

    /**
     * A method to ensure all the values for supplier to be inserted are correct and valid.
     * @param uri       the uri
     * @param values    the values to be inserted for each column
     * @return          the new URI with the ID (of the newly inserted row) appended at the end, otherwise null
     */
    public Uri insertSupplier(Uri uri, ContentValues values){

        // Check the supplier name is not null
        String name = values.getAsString(SUPPLIER_NAME);
        if (name == null) {
            throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_category_name_exception));
        }

        // Check the supplier name is not null
        String address = values.getAsString(SUPPLIER_ADDRESS);
        if (address == null) {
            throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_category_name_exception));
        }

        // Check the supplier name is not null
        String phone = values.getAsString(SUPPLIER_PHONE);
        if (phone == null) {
            throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_category_name_exception));
        }

        // Check the supplier name is not null
        String email = values.getAsString(SUPPLIER_EMAIL);
        if (email == null) {
            throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_category_name_exception));
        }

        // Get writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Insert the new category with the given values
        long id = db.insert(CATEGORY_TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, dbHelper.getContext().getString(R.string.content_uri_insert_failure_exception, uri));
            return null;
        }

        return ContentUris.withAppendedId(uri, id);
    }
    //endregion

    //region Update database methods
    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        final int match = uriMatcher.match(uri);
        switch (match) {
            case PRODUCT_TABLE:
                return updateProduct(uri, contentValues, selection, selectionArgs);
            case PRODUCT_TABLE_ROW:

                selection = PRODUCT_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateProduct(uri, contentValues, selection, selectionArgs);
            case CATEGORY_TABLE:
                return updateCategory(uri, contentValues, selection, selectionArgs);
            case CATEGORY_TABLE_ROW:

                selection = CATEGORY_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateCategory(uri, contentValues, selection, selectionArgs);
            case SUPPLIER_TABLE:
                return updateSupplier(uri, contentValues, selection, selectionArgs);
            case SUPPLIER_TABLE_ROW:

                selection = SUPPLIER_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateSupplier(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_unsupported_update_exception, uri));
        }
    }

    /**
     * A method which validates the update of a product or number of products in the db.
     * @param uri               the uri of the product entry
     * @param values            the content values
     * @param selection         selection
     * @param selectionArgs     selection arguments
     * @return                  Returns the number of database rows affected by the update statement
     */
    private int updateProduct(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        // If the {@link ProductEntry#NAME} key is present,
        // check that the name value is not null.
        if (values.containsKey(PRODUCT_NAME)) {
            String name = values.getAsString(PRODUCT_NAME);
            if (name == null) {
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_product_name_exception));
            }
        }

        // If the {@link ProductEntry#SALE_PRICE} key is present,
        // check that the price value is valid.
        if (values.containsKey(PRODUCT_SALE_PRICE)) {
            Float price = values.getAsFloat(PRODUCT_SALE_PRICE);
            if (price == null || price < 0) {
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_product_price_exception));
            }
        }

        // If the {@link ProductEntry#QUANTITY} key is present,
        // check that the quantity value is valid.
        if (values.containsKey(PRODUCT_QUANTITY)) {
            // Check that the value is greater than or equal to 0
            Integer quantity = values.getAsInteger(PRODUCT_QUANTITY);
            if (quantity == null || quantity < 0) {
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_product_quantity_exception));
            }
        }

        // If the {@link ProductEntry#QUANTITY} key is present,
        // check that the quantity unit value is valid.
        if (values.containsKey(PRODUCT_QUANTITY_UNIT)) {
            // Check that the weight is greater than or equal to 0 kg
            String unit = values.getAsString(PRODUCT_QUANTITY_UNIT);
            if (unit == null || ProductManagerContract.ProductEntry.isValidUnit(unit)) {
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_product_quantity_unit_exception));
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writable database to update the data
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        return database.update(PRODUCT_TABLE_NAME, values, selection, selectionArgs);
    }

    /**
     * A method which validates the update of a category or number of categories in the db.
     * @param uri               the uri of the category entry
     * @param values            the content values
     * @param selection         selection
     * @param selectionArgs     selection arguments
     * @return                  Returns the number of database rows affected by the update statement
     */
    private int updateCategory(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        // If the {@link CategoryEntry#NAME} key is present,
        // check that the name value is not null.
        if (values.containsKey(CATEGORY_NAME)) {
            String name = values.getAsString(CATEGORY_NAME);
            if (name == null) {
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_category_name_exception));
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writable database to update the data
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        return database.update(CATEGORY_TABLE_NAME, values, selection, selectionArgs);
    }

    /**
     * A method which validates the update of a supplier or number of suppliers in the db.
     * @param uri               the uri of the supplier entry
     * @param values            the content values
     * @param selection         selection
     * @param selectionArgs     selection arguments
     * @return                  Returns the number of database rows affected by the update statement
     */
    private int updateSupplier(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        // If the {@link CategoryEntry#NAME} key is present,
        // check that the name value is not null.
        if (values.containsKey(SUPPLIER_NAME)) {
            String name = values.getAsString(SUPPLIER_NAME);
            if (name == null) {
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_supplier_name_exception));
            }
        }

        // If the {@link CategoryEntry#ADDRESS} key is present,
        // check that the name value is not null.
        if (values.containsKey(SUPPLIER_ADDRESS)) {
            String name = values.getAsString(SUPPLIER_ADDRESS);
            if (name == null) {
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_supplier_address_exception));
            }
        }

        // If the {@link CategoryEntry#PHONE} key is present,
        // check that the name value is not null.
        if (values.containsKey(SUPPLIER_PHONE)) {
            String name = values.getAsString(SUPPLIER_PHONE);
            if (name == null) {
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_supplier_phone_exception));
            }
        }

        // If the {@link CategoryEntry#EMAIL} key is present,
        // check that the name value is not null.
        if (values.containsKey(SUPPLIER_EMAIL)) {
            String name = values.getAsString(SUPPLIER_EMAIL);
            if (name == null) {
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_supplier_email_exception));
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writable database to update the data
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        return database.update(SUPPLIER_TABLE_NAME, values, selection, selectionArgs);
    }
    //endregion

    //region Delete method(s)
    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        final int match = uriMatcher.match(uri);
        switch(match){
            case PRODUCT_TABLE:
                // Delete all rows that match the selection and selection args
                return db.delete(PRODUCT_TABLE_NAME, selection, selectionArgs);
            case PRODUCT_TABLE_ROW:
                // Delete a single row given by the ID in the URI
                selection = PRODUCT_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return db.delete(PRODUCT_TABLE_NAME, selection, selectionArgs);
            case CATEGORY_TABLE:
                // Delete all rows that match the selection and selection args
                return db.delete(CATEGORY_TABLE_NAME, selection, selectionArgs);
            case CATEGORY_TABLE_ROW:
                // Delete a single row given by the ID in the URI
                selection = CATEGORY_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return db.delete(CATEGORY_TABLE_NAME, selection, selectionArgs);
            case SUPPLIER_TABLE:
                // Delete all rows that match the selection and selection args
                return db.delete(SUPPLIER_TABLE_NAME, selection, selectionArgs);
            case SUPPLIER_TABLE_ROW:
                // Delete a single row given by the ID in the URI
                selection = SUPPLIER_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return db.delete(SUPPLIER_TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException(dbHelper.getContext().getString(R.string.content_uri_unsupported_deletion_exception, uri));
        }
    }
    //endregion

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case PRODUCT_TABLE:
                return PRODUCT_TABLE_LIST_TYPE;
            case PRODUCT_TABLE_ROW:
                return PRODUCT_TABLE_ITEM_TYPE;
            case CATEGORY_TABLE:
                return CATEGORY_TABLE_LIST_TYPE;
            case CATEGORY_TABLE_ROW:
                return CATEGORY_TABLE_ITEM_TYPE;
            case SUPPLIER_TABLE:
                return SUPPLIER_TABLE_LIST_TYPE;
            case SUPPLIER_TABLE_ROW:
                return SUPPLIER_TABLE_ITEM_TYPE;
            default:
                throw new IllegalStateException(dbHelper.getContext().getString(R.string.content_uri_unknown_exception, uri, match));
        }
    }


}
