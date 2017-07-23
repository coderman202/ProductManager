package com.example.android.productmanager.utils;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.android.productmanager.data.ProductManagerContract;
import com.example.android.productmanager.model.Category;
import com.example.android.productmanager.model.Supplier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reggie on 22/07/2017.
 * A utility class.
 */

public class ProductManagerUtils {

    // Empty private constructor. Ensures class is not going to be initialised.
    private ProductManagerUtils() {
    }

    /**
     * A method to get a list of {@link Category} objects from the cursor parameter. Closes the
     * passed cursor after the list is generated as it will not be needed anymore.
     *
     * @param context The context.
     * @param cursor  The cursor
     * @return A list of {@link Category} objects.
     */
    public static List<Category> getCategoriesFromCursor(Context context, Cursor cursor) {

        List<Category> categoryList = new ArrayList<>();

        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            int catID = cursor.getInt(cursor.getColumnIndex(ProductManagerContract.CategoryEntry.PK_CATEGORY_ID));
            String catName = cursor.getString(cursor.getColumnIndex(ProductManagerContract.CategoryEntry.NAME));
            String iconFileName = cursor.getString(cursor.getColumnIndex(ProductManagerContract.CategoryEntry.ICON_ID));

            int iconResID = context.getResources().getIdentifier(iconFileName, "drawable", context.getPackageName());
            Log.e(catName, catID + "");

            categoryList.add(new Category(catID, catName, iconResID));
            cursor.moveToNext();
        }
        cursor.close();

        return categoryList;
    }

    /**
     * A method to get a list of {@link Supplier} objects from the cursor parameter. Closes the
     * passed cursor after the list is generated as it will not be needed anymore.
     *
     * @param context The context.
     * @param cursor  The cursor
     * @return A list of {@link Supplier} objects.
     */
    public static List<Supplier> getSuppliersFromCursor(Context context, Cursor cursor) {

        List<Supplier> supplierList = new ArrayList<>();

        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            int suppID = cursor.getInt(cursor.getColumnIndex(ProductManagerContract.SupplierEntry.PK_SUPPLIER_ID));
            String suppName = cursor.getString(cursor.getColumnIndex(ProductManagerContract.SupplierEntry.NAME));
            String suppAddress = cursor.getString(cursor.getColumnIndex(ProductManagerContract.SupplierEntry.ADDRESS));
            String suppEmail = cursor.getString(cursor.getColumnIndex(ProductManagerContract.SupplierEntry.EMAIL));
            String suppPhone = cursor.getString(cursor.getColumnIndex(ProductManagerContract.SupplierEntry.PHONE));


            supplierList.add(new Supplier(suppID, suppName, suppAddress, suppEmail, suppPhone));
            cursor.moveToNext();
        }
        cursor.close();

        return supplierList;
    }
}
