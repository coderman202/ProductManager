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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

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

import static com.example.android.productmanager.R.id.price;

public class AddNewProductActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = AddNewProductActivity.class.getSimpleName();

    private ActionBar actionBar;

    @BindView(R.id.quantity_picker)
    NumberPicker quantityPicker;
    @BindView(R.id.name_edit_text)
    EditText nameEditText;
    @BindView(R.id.price_setter)
    SeekBar priceSetter;
    @BindView(price)
    TextView priceView;
    @BindView(R.id.unit_selector)
    NumberPicker unitSelector;
    @BindView(R.id.supplier_selector)
    Spinner supplierSelector;
    @BindView(R.id.category_selector)
    Spinner categorySelector;
    @BindView(R.id.add_new_product)
    TextView addProductButton;

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

    // List of possible quantity unit options to populate the unitSelector.
    final String[] unitOptions = ProductManagerContract.ProductEntry.POSSIBLE_UNITS_ARRAY;


    // List of all variables needed to add a new product to the database
    private String productName;
    private int categoryID;
    private int supplierID;
    private int quantity;
    private String quantityUnit;
    private float salePrice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        ButterKnife.bind(this);

        actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.add_product_title));

        actionBar.setDisplayHomeAsUpEnabled(true);

        addProductButton.setOnClickListener(this);

        initQuantitySelectors();
        initCategorySpinner();
        initSupplierSpinner();

        initPriceSetter();

        initNameEditText();


    }

    private void initNameEditText() {
        /*nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard();
                }
            }
        });*/

    }

    private void initPriceSetter() {
        priceSetter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                salePrice = (float) i / 100;
                String priceText = "€" + salePrice;
                priceView.setText(priceText);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add_new_product:
                getAllProductValues();
                break;
        }
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
        quantityPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                quantity = newVal * 5;
                Log.e("quantity", quantity + "");
            }
        });


        unitSelector.setMinValue(0);
        unitSelector.setMaxValue(unitOptions.length - 1);
        unitSelector.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return unitOptions[i];
            }
        });
        unitSelector.setWrapSelectorWheel(true);
        unitSelector.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                quantityUnit = unitOptions[newVal];
                Log.e("unit", quantityUnit);
            }
        });
    }

    /**
     * Set up the category spinner to choose category. Get category list from the db.
     */
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
                categoryID = categoryList.get(i).getCategoryID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * Set up the supplier spinner to choose supplier. Get supplier list from the db.
     */
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
                supplierID = supplierList.get(i).getSupplierID();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getAllProductValues() {

        productName = nameEditText.getText().toString();


    }

    /**
     * Hide keyboard.
     */
    public void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.e(LOG_TAG, getString(R.string.keyboard_hide_exception), e);
        }
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
