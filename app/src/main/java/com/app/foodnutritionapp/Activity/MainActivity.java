package com.app.foodnutritionapp.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.foodnutritionapp.Fragment.ProductFragment;
import com.app.foodnutritionapp.R;
import com.app.foodnutritionapp.Util.Events;
import com.app.foodnutritionapp.Util.GlobalBus;
import com.app.foodnutritionapp.Util.Method;

import org.greenrobot.eventbus.Subscribe;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @SuppressLint("StaticFieldLeak")
    public static Toolbar toolbar;
    boolean doubleBackToExitPressedOnce = false;
    private Method method;
    private DrawerLayout drawer;
    private LinearLayout linearLayout;
    private NavigationView navigationView;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalBus.getBus().register(this);

        Method.forceRTLIfSupported(getWindow(), MainActivity.this);

        method = new Method(MainActivity.this);

        toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.product));

        linearLayout = findViewById(R.id.linearLayout_adView_main);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_side_nav);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (method.pref.getBoolean(method.pref_login, false)) {
            navigationView.getMenu().getItem(2).setIcon(R.drawable.logout);
            navigationView.getMenu().getItem(2).setTitle(getResources().getString(R.string.action_logout));
        }

        ProductFragment roomFragment;
        if (savedInstanceState != null) {
            roomFragment = (ProductFragment) getSupportFragmentManager().findFragmentByTag("room");
        } else {
            roomFragment = new ProductFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, roomFragment, "room").commit();
            Method.onBackPress = true;
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getResources().getString(R.string.Please_click_BACK_again_to_exit), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //Checking if the item is in checked state or not, if not make it in checked state
        if (item.isChecked())
            item.setChecked(false);
        else
            item.setChecked(true);

        //Closing drawer on item click
        drawer.closeDrawers();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {

            case R.id.product:
                getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new ProductFragment(), "room").commit();
                return true;

            case R.id.profile:
                if (Method.isNetworkAvailable(MainActivity.this)) {
                    if (method.pref.getBoolean(method.pref_login, false)) {
                        startActivity(new Intent(MainActivity.this, Profile.class));
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.you_have_not_login), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, getResources().getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.login:
                if (method.pref.getBoolean(method.pref_login, false)) {
                    method.editor.putBoolean(method.pref_login, false);
                    method.editor.commit();
                    startActivity(new Intent(MainActivity.this, Login.class));
                } else {
                    startActivity(new Intent(MainActivity.this, Login.class));
                    drawer.closeDrawers();
                }
                finishAffinity();
                return true;

            default:
                return true;
        }

    }

    @Subscribe
    public void getLogin(Events.Login login) {
        if (method != null) {
            if (method.pref.getBoolean(method.pref_login, false)) {
                if (navigationView != null) {
                    navigationView.getMenu().getItem(8).setIcon(R.drawable.logout);
                    navigationView.getMenu().getItem(8).setTitle(getResources().getString(R.string.action_logout));
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        GlobalBus.getBus().unregister(this);
        super.onDestroy();
    }

}
