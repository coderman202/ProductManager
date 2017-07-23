package com.example.android.productmanager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import com.example.android.productmanager.adapters.ProductAdapter;
import com.example.android.productmanager.data.ProductManagerContract;
import com.example.android.productmanager.model.Category;
import com.example.android.productmanager.utils.ProductManagerUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
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
            ProductManagerContract.ProductEntry.PIC_ID,
            ProductManagerContract.ProductEntry.FK_CATEGORY_ID,
            ProductManagerContract.ProductEntry.FK_SUPPLIER_ID
    };

    public static final String[] CATEGORY_ENTRY_COLUMNS = {
            ProductManagerContract.CategoryEntry.PK_CATEGORY_ID,
            ProductManagerContract.CategoryEntry.NAME,
            ProductManagerContract.CategoryEntry.ICON_ID
    };

    // Navigation drawer items and associated variables
    @BindView(R.id.nav_view) NavigationView navView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    List<Category> categoryList = new ArrayList<>();

    @BindView(R.id.fab) FloatingActionButton fab;

    @BindView(R.id.toolbar) Toolbar toolbar;
    ActionBar actionBar;
    @BindString(R.string.main_subtitle)
    String actionBarSubTitle;

    // Variables for the CursorAdapter for product list.
    @BindView(R.id.product_lists)
    RecyclerView productListView;
    LinearLayoutManager layoutManager;
    ProductAdapter productAdapter;

    // This will be set when a category is chosen in the nav drawer. Set default as -1.
    private int categoryID = -1;

    // Key for saving the scroll position of the RecyclerView.
    public static final String BUNDLE_RECYCLER_LAYOUT_KEY = "RecyclerView Layout";

    // Key for saving action bar subtitle
    public static final String ACTION_BAR_SUBTITLE_KEY = "Subtitle";

    // Loader manager for handling the loader(s)
    LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        loaderManager = getSupportLoaderManager();

        initProductAdapter();

        fab.setOnClickListener(this);

        initNavDrawer();

        if (savedInstanceState != null) {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT_KEY);
            layoutManager.onRestoreInstanceState(savedRecyclerLayoutState);
            actionBarSubTitle = savedInstanceState.getString(ACTION_BAR_SUBTITLE_KEY);
        }

        actionBar.setSubtitle(actionBarSubTitle);

        loaderManager.restartLoader(PRODUCT_LOADER_ID, null, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT_KEY, layoutManager.onSaveInstanceState());
        outState.putString(ACTION_BAR_SUBTITLE_KEY, actionBarSubTitle);
    }

    @Override
    public void onRestoreInstanceState(Bundle inState) {
        Parcelable savedRecyclerLayoutState = inState.getParcelable(BUNDLE_RECYCLER_LAYOUT_KEY);
        layoutManager.onRestoreInstanceState(savedRecyclerLayoutState);
        actionBarSubTitle = inState.getString(ACTION_BAR_SUBTITLE_KEY);
    }

    /**
     * For the {@link RecyclerView}, set the {@link ProductAdapter} and use a LinearLayoutManager
     * to set it to vertical.
     */
    private void initProductAdapter() {
        productAdapter = new ProductAdapter(this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        productListView.setLayoutManager(layoutManager);
        productListView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).
                color(Color.LTGRAY).sizeResId(R.dimen.list_item_divider_width).
                marginResId(R.dimen.list_item_divider_margin, R.dimen.list_item_divider_margin).build());
        productListView.setAdapter(productAdapter);
    }

    /**
     * Set up the Nav drawer. Populate with a list of categories also.
     */
    private void initNavDrawer() {
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        String sortOrder = ProductManagerContract.CategoryEntry.NAME + " ASC";

        Uri categoryTableUri = ProductManagerContract.CategoryEntry.CONTENT_URI;

        Cursor cursor = getContentResolver().query(categoryTableUri, CATEGORY_ENTRY_COLUMNS, null, null, sortOrder);

        categoryList = ProductManagerUtils.getCategoriesFromCursor(this, cursor);


        final Menu menu = navView.getMenu();
        final SubMenu subMenu = menu.addSubMenu(getString(R.string.nav_category_menu_header));
        for (int i = 0; i < categoryList.size(); i++) {
            String name = categoryList.get(i).getCategoryName();
            MenuItem item = subMenu.add(Menu.NONE, i, 0, name);
            item.setOnMenuItemClickListener(this);
        }


        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item){
        actionBarSubTitle = item.getTitle().toString();
        actionBar.setSubtitle(actionBarSubTitle);
        categoryID = categoryList.get(item.getItemId()).getCategoryID();
        loaderManager.restartLoader(PRODUCT_LOADER_ID, null, this);

        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    @Override
    public void onClick(View view){
        int id = view.getId();
        switch (id) {
            case R.id.fab:
                Intent intent = new Intent(this, AddNewProductActivity.class);
                startActivity(intent);
                break;
        }
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


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String sortOrder = ProductManagerContract.ProductEntry.NAME + " ASC";

        Uri productTableUri = ProductManagerContract.ProductEntry.CONTENT_URI;

        // Check if category id has been set. If so, show only products in selected category.
        if (categoryID != -1) {
            String whereClause = ProductManagerContract.ProductEntry.FK_CATEGORY_ID + " = " + categoryID;
            return new CursorLoader(this, productTableUri, PRODUCT_ENTRY_COLUMNS, whereClause, null, sortOrder);
        }

        return new CursorLoader(this, productTableUri, PRODUCT_ENTRY_COLUMNS, null, null, sortOrder);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        productAdapter.swapCursor(data);


        if (productAdapter.getItemCount() == 0) {

        }



    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        productAdapter.swapCursor(null);
    }
}
