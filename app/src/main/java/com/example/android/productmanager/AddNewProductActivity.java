package com.example.android.productmanager;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.example.android.productmanager.adapters.CategorySpinnerAdapter;
import com.example.android.productmanager.adapters.SupplierSpinnerAdapter;
import com.example.android.productmanager.data.ProductManagerContract;
import com.example.android.productmanager.model.Category;
import com.example.android.productmanager.model.Supplier;
import com.example.android.productmanager.utils.ProductManagerUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNewProductActivity extends AppCompatActivity {

    private ActionBar actionBar;

    @BindView(R.id.quantity_picker)
    NumberPicker quantityPicker;
    @BindView(R.id.name_edit_text)
    EditText nameEditText;
    @BindView(R.id.price_setter)
    SeekBar priceSetter;
    @BindView(R.id.unit_selector)
    NumberPicker unitSelector;
    @BindView(R.id.supplier_selector)
    Spinner supplierSelector;
    @BindView(R.id.category_selector)
    Spinner categorySelector;

    List<Category> categoryList = new ArrayList<>();

    public static final String[] CATEGORY_ENTRY_COLUMNS = {
            ProductManagerContract.CategoryEntry.PK_CATEGORY_ID,
            ProductManagerContract.CategoryEntry.NAME,
            ProductManagerContract.CategoryEntry.ICON_ID
    };

    private CategorySpinnerAdapter categoryAdapter;

    // Variables for the list of suppliers
    List<Supplier> supplierList = new ArrayList<>();

    public static final String[] SUPPLIER_ENTRY_COLUMNS = {
            ProductManagerContract.SupplierEntry.PK_SUPPLIER_ID,
            ProductManagerContract.SupplierEntry.NAME,
            ProductManagerContract.SupplierEntry.ADDRESS,
            ProductManagerContract.SupplierEntry.EMAIL,
            ProductManagerContract.SupplierEntry.PHONE,
    };

    private SupplierSpinnerAdapter supplierAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        ButterKnife.bind(this);

        actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.add_product_title));

        actionBar.setDisplayHomeAsUpEnabled(true);

        initQuantitySelectors();
        initCategorySpinner();
        initSupplierSpinner();


    }

    /**
     * First set the number picker to select the stock level to count up by 5.
     * Set a second picker to choose the unit of quantity by passing in an array of strings which
     * equals the possible units.
     */
    private void initQuantitySelectors() {
        quantityPicker.setMinValue(0);
        quantityPicker.setMaxValue(30);
        quantityPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.valueOf(i * 5);
            }
        });

        quantityPicker.setWrapSelectorWheel(true);

        final String[] unitOptions = ProductManagerContract.ProductEntry.POSSIBLE_UNITS_ARRAY;
        unitSelector.setMinValue(0);
        unitSelector.setMaxValue(unitOptions.length - 1);
        unitSelector.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return unitOptions[i];
            }
        });
    }

    private void initCategorySpinner() {
        String sortOrder = ProductManagerContract.CategoryEntry.NAME + " ASC";

        Uri categoryTableUri = ProductManagerContract.CategoryEntry.CONTENT_URI;

        Cursor cursor = getContentResolver().query(categoryTableUri, CATEGORY_ENTRY_COLUMNS, null, null, sortOrder);

        categoryList = ProductManagerUtils.getCategoriesFromCursor(this, cursor);
        categoryAdapter = new CategorySpinnerAdapter(this, categoryList);
        categorySelector.setAdapter(categoryAdapter);

        categorySelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("cat", categoryList.get(i).getCategoryName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initSupplierSpinner() {
        String sortOrder = ProductManagerContract.SupplierEntry.NAME + " ASC";

        Uri uri = ProductManagerContract.SupplierEntry.CONTENT_URI;

        Cursor cursor = getContentResolver().query(uri, SUPPLIER_ENTRY_COLUMNS, null, null, sortOrder);

        supplierList = ProductManagerUtils.getSuppliersFromCursor(this, cursor);
        supplierAdapter = new SupplierSpinnerAdapter(this, supplierList);
        supplierSelector.setAdapter(supplierAdapter);

        supplierSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("supp", supplierList.get(i).getSupplierName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
