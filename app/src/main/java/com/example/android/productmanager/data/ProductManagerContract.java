package com.example.android.productmanager.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Reggie on 21/07/2017.
 * A custom contract class to store the information about the tables in the db
 */

public class ProductManagerContract {

    // The content authority and the base content URI which will be used to generate all URIs in
    // this contract class.
    public static final String CONTENT_AUTHORITY = "com.example.android.productmanager";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Empty private constructor. Ensures class is not going to be initialised.
    private ProductManagerContract(){}

    public static final class ProductEntry implements BaseColumns{

        // Table name
        public static final String TABLE_NAME = "Product";

        // Table path for URI
        public static final String PATH = TABLE_NAME.toLowerCase();

        // Content URI
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;


        // Primary key
        public static final String PK_PRODUCT_ID = BaseColumns._ID;

        // Other column names
        public static final String NAME = "ProductName";
        public static final String SALE_PRICE = "SalePrice";
        public static final String QUANTITY = "Quantity";
        public static final String QUANTITY_UNIT = "QuantityUnit";
        public static final String FK_SUPPLIER_ID = "SupplierID";
        public static final String FK_CATEGORY_ID = "CategoryID";
        public static final String PIC_ID = "PicID";

        // Constants to represent possible units of quantity.
        public static final String UNIT_KG = "kg";
        public static final String UNIT_G = "g";
        public static final String UNIT_ML = "mL";
        public static final String UNIT_L = "L";
        public static final String UNIT_MG = "mg";
        public static final String UNIT_PACK = "pack(s)";
        public static final String UNIT_BOTTLE = "bottle(s)";

        /**
         * A Method to check that the quantity unit to be inserted is a valid unit. Returns true
         * if so.
         * @param unit  the unit represented in String form
         * @return      true or false
         */
        public static boolean isValidUnit(String unit){
            return(unit.equals(UNIT_KG) || unit.equals(UNIT_G) || unit.equals(UNIT_ML) ||
                    unit.equals(UNIT_L) || unit.equals(UNIT_MG) || unit.equals(UNIT_PACK) ||
                    unit.equals(UNIT_BOTTLE));
        }

    }

    public static final class CategoryEntry implements BaseColumns{

        // Table name.
        public static final String TABLE_NAME = "Category";

        // Table path for URI
        public static final String PATH = TABLE_NAME.toLowerCase();

        // Content URI
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        // Primary key
        public static final String PK_CATEGORY_ID = BaseColumns._ID;

        // Other column names.
        public static final String NAME = "CategoryName";
        public static final String ICON_ID = "IconID";
    }

    public static final class SupplierEntry implements BaseColumns{

        // Table name.
        public static final String TABLE_NAME = "Supplier";

        // Table path for URI
        public static final String PATH = TABLE_NAME.toLowerCase();

        // Content URI
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        // Primary key.
        public static final String PK_SUPPLIER_ID = BaseColumns._ID;

        // Other column names.
        public static final String NAME = "SupplierName";
        public static final String ADDRESS = "SupplierAddress";
        public static final String EMAIL = "SupplierEmail";
        public static final String PHONE = "SupplierPhone";
    }
}