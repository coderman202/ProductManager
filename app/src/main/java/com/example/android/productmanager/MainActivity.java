package com.example.android.productmanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

import com.example.android.productmanager.data.ProductManagerDBHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, MenuItem.OnMenuItemClickListener {

    @BindView(R.id.nav_view) NavigationView navView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    @BindView(R.id.fab) FloatingActionButton fab;

    @BindView(R.id.toolbar) Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(this);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

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

        } else if(id == 1){
            Log.e("heyheyhey", "testtetstets");
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
