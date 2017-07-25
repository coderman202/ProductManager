package com.example.android.productmanager.data;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Reggie on 21/07/2017.
 * A custom DB Helper class to create, populate and update my db.
 */

public class ProductManagerDBHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = ProductManagerDBHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "ProductManagerDB.db";
    private static final int DATABASE_VERSION = 3;

    private Context context;

    // File names of the scripts stored in assets directory
    private static final String CREATE_TABLES = "create_tables.sql";
    private static final String DROP_TABLES = "drop_tables.sql";
    private static final String INSERT_DATA = "insert_data.sql";

    // Table names
    private static final String PRODUCT_TABLE = ProductManagerContract.ProductEntry.TABLE_NAME;
    private static final String CATEGORY_TABLE = ProductManagerContract.CategoryEntry.TABLE_NAME;
    private static final String SUPPLIER_TABLE = ProductManagerContract.SupplierEntry.TABLE_NAME;

    // Product table columns.
    private static final String PRODUCT_ID = ProductManagerContract.ProductEntry.PK_PRODUCT_ID;
    private static final String PRODUCT_NAME = ProductManagerContract.ProductEntry.NAME;
    private static final String PRODUCT_SALE_PRICE = ProductManagerContract.ProductEntry.SALE_PRICE;
    private static final String PRODUCT_QUANTITY = ProductManagerContract.ProductEntry.QUANTITY;
    private static final String PRODUCT_QUANTITY_UNIT = ProductManagerContract.ProductEntry.QUANTITY_UNIT;
    private static final String PRODUCT_PIC_ID = ProductManagerContract.ProductEntry.PIC_ID;
    private static final String PRODUCT_CATEGORY_ID = ProductManagerContract.ProductEntry.FK_CATEGORY_ID;
    private static final String PRODUCT_SUPPLIER_ID = ProductManagerContract.ProductEntry.FK_SUPPLIER_ID;

    // Category table columns
    private static final String CATEGORY_ID = ProductManagerContract.CategoryEntry.PK_CATEGORY_ID;
    private static final String CATEGORY_NAME = ProductManagerContract.CategoryEntry.NAME;
    private static final String CATEGORY_ICON_ID = ProductManagerContract.CategoryEntry.ICON_ID;

    // Supplier table columns
    private static final String SUPPLIER_ID = ProductManagerContract.SupplierEntry.PK_SUPPLIER_ID;
    private static final String SUPPLIER_NAME = ProductManagerContract.SupplierEntry.NAME;
    private static final String SUPPLIER_ADDRESS = ProductManagerContract.SupplierEntry.ADDRESS;
    private static final String SUPPLIER_EMAIL = ProductManagerContract.SupplierEntry.EMAIL;
    private static final String SUPPLIER_PHONE = ProductManagerContract.SupplierEntry.PHONE;




    public ProductManagerDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
        if(isEmptyDB(db)){
            runSQLScript(context, db, INSERT_DATA);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public Context getContext() {
        return context;
    }

    /**
     * A method to create tables in the db
     * @param db    the SQLite DB
     */
    public void createTables(SQLiteDatabase db){
        String query = "CREATE TABLE IF NOT EXISTS " + SUPPLIER_TABLE + "(" +
                SUPPLIER_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                SUPPLIER_NAME + " TEXT NOT NULL, " +
                SUPPLIER_ADDRESS + " TEXT NOT NULL," +
                SUPPLIER_EMAIL + " TEXT NOT NULL, " +
                SUPPLIER_PHONE + " TEXT NOT NULL);";
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS " + CATEGORY_TABLE + "(" +
                CATEGORY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                CATEGORY_NAME + " TEXT NOT NULL, " +
                CATEGORY_ICON_ID + " INTEGER);";
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS " + PRODUCT_TABLE + "(" +
                PRODUCT_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                PRODUCT_CATEGORY_ID + " INTEGER NOT NULL, " +
                PRODUCT_NAME + " TEXT NOT NULL, " +
                PRODUCT_SALE_PRICE + " REAL NOT NULL, " +
                PRODUCT_QUANTITY + " INTEGER NOT NULL, " +
                PRODUCT_QUANTITY_UNIT + " TEXT NOT NULL, " +
                PRODUCT_SUPPLIER_ID + " INTEGER NOT NULL, " +
                PRODUCT_PIC_ID + " INTEGER, " +
                "FOREIGN KEY(" + PRODUCT_CATEGORY_ID + ") REFERENCES " + CATEGORY_TABLE + "(" + CATEGORY_ID + ") ON DELETE SET NULL," +
                "FOREIGN KEY(" + PRODUCT_SUPPLIER_ID + ") REFERENCES " + SUPPLIER_TABLE + "(" + SUPPLIER_ID + ") ON DELETE SET NULL );";

        db.execSQL(query);
    }

    /**
     * A simple drop tables method
     * @param db    the SQLite DB
     */
    public void dropTables(SQLiteDatabase db){
        String query = "PRAGMA foreign_keys = OFF;\n" +
                "DROP TABLE IF EXISTS " + PRODUCT_TABLE + ";\n" +
                "DROP TABLE IF EXISTS " + CATEGORY_TABLE + ";\n" +
                "DROP TABLE IF EXISTS " + SUPPLIER_TABLE + ";\n" +
                "PRAGMA foreign_keys = ON;";
        db.execSQL(query);
    }


    /**
     * A method to check if the db is empty.
     * @param db    The db
     * @return      True if empty, false if not.
     */
    public boolean isEmptyDB(SQLiteDatabase db){
        String query = "SELECT COUNT(*) FROM " + PRODUCT_TABLE + ", " + CATEGORY_TABLE + ", " +
                SUPPLIER_TABLE + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null){
            c.moveToFirst();
            int count = c.getInt(0);
            c.close();
            return count == 0;
        }
        c.close();
        return false;
    }

    /**
     * A method to execute sql scripts. This can be called to run the create_tables.sql script in
     * the onCreate method, along with the insert_data.sql file. n the onUpgrade method, the
     * drop_tables.sql file can be called along with the create and insert scripts. My resource for
     * this method is found below.
     *
     * @param context   The context
     * @param db        The db that the script is run on
     * @param sqlScript The sql script
     * @see <a href="http://www.drdobbs.com/database/using-sqlite-on-android/232900584">Here</a>
     */
    private void runSQLScript(Context context, SQLiteDatabase db, String sqlScript) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;
        AssetManager assetManager = context.getAssets();
        InputStream inputStream;

        try {
            inputStream = assetManager.open(sqlScript);
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();

            String[] script = outputStream.toString().split(";");
            for (String sqlStatement : script) {
                sqlStatement = sqlStatement.trim();
                if (sqlStatement.length() > 0) {
                    db.execSQL(sqlStatement + ";");
                }
            }
        } catch (IOException e) {
            Log.e(e.toString(), sqlScript + "failed to load");
        } catch (SQLException e) {
            Log.e(e.toString(), sqlScript + "failed to execute");
        }
    }

    /**
     * A test method to just ensure the db has entries and to ensure all entries are there.
     * @return      number of total row in db across all tables multiplied by one another.
     */
    public int entryCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + PRODUCT_TABLE + ", " + CATEGORY_TABLE + ", " +
                SUPPLIER_TABLE + ";";
        Cursor c = db.rawQuery(query, null);
        if(c != null){
            c.moveToFirst();
            int count = c.getInt(0);
            c.close();
            return count;
        }
        c.close();
        return 0;
    }
}