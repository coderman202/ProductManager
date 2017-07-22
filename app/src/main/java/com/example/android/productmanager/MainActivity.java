package com.example.android.productmanager;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

import com.example.android.productmanager.adapters.ProductAdapter;
import com.example.android.productmanager.data.ProductManagerContract;
import com.example.android.productmanager.data.ProductManagerDBHelper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>,
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
        MenuItem.OnMenuItemClickListener {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final int PRODUCT_LOADER_ID = 1;

    public static final String[] PRODUCT_ENTRY_COLUMNS = {
            ProductManagerContract.ProductEntry.PK_PRODUCT_ID,
            ProductManagerContract.ProductEntry.NAME,
            ProductManagerContract.ProductEntry.QUANTITY,
            ProductManagerContract.ProductEntry.QUANTITY_UNIT,
            ProductManagerContract.ProductEntry.SALE_PRICE,
            ProductManagerContract.ProductEntry.FK_CATEGORY_ID,
            ProductManagerContract.ProductEntry.FK_SUPPLIER_ID
    };

    // Navigation drawer items and associated variables
    @BindView(R.id.nav_view) NavigationView navView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    @BindView(R.id.fab) FloatingActionButton fab;

    @BindView(R.id.toolbar) Toolbar toolbar;

    // Variables for the CursorAdapter for product list.
    @BindView(R.id.product_lists)
    RecyclerView productListView;
    LinearLayoutManager layoutManager;
    ProductAdapter productAdapter;
    Cursor productListCursor;

    // Key for saving the scroll position of the RecyclerView.
    public static final String BUNDLE_RECYCLER_LAYOUT_KEY = "RecyclerView Layout";

    // Loader manager for handling the loader(s)
    LoaderManager loaderManager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        loaderManager = getSupportLoaderManager();

        loaderManager.restartLoader(PRODUCT_LOADER_ID, null, this);

        fab.setOnClickListener(this);

        initNavDrawer();


        // Just testing here to ensure db was populating properly
        ProductManagerDBHelper dbHelper = new ProductManagerDBHelper(this);

        int count = dbHelper.entryCount();

        Log.e("count", count + "");

        final Menu menu = navView.getMenu();
        final SubMenu subMenu = menu.addSubMenu("Category List");
        for (int i = 0; i < 3; i++) {
            subMenu.add(R.id.category_list, i, 0, "Test " + i).setIcon(R.drawable.add_product);
            Log.e("submenu", subMenu.getItem(i).toString());
            subMenu.getItem(i).setOnMenuItemClickListener(this);
        }
        // Test ends here
    }

    /**
     * For the {@link RecyclerView}, set the {@link ProductAdapter} and use a LinearLayoutManager
     * to set it to vertical.
     */
    private void initProductAdapter() {
        productAdapter = new ProductAdapter(this, productListCursor);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        productListView.setLayoutManager(layoutManager);
        productListView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).
                color(Color.LTGRAY).sizeResId(R.dimen.list_item_divider_width).
                marginResId(R.dimen.list_item_divider_margin, R.dimen.list_item_divider_margin).build());
        productListView.setAdapter(productAdapter);
    }

    private void initNavDrawer() {
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item){
        Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_LONG).show();

        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    @Override
    public void onClick(View view){

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_new_product) {

        } else if (id == R.id.add_new_supplier) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String sortOrder = ProductManagerContract.ProductEntry.QUANTITY + " ASC";

        Uri productTableUri = ProductManagerContract.ProductEntry.CONTENT_URI;

        return new CursorLoader(this, productTableUri, PRODUCT_ENTRY_COLUMNS, null, null, sortOrder);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        productListCursor = data;
        initProductAdapter();


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
