package com.app.foodnutritionapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.app.foodnutritionapp.Adapter.ProductDetailAdapter;
import com.app.foodnutritionapp.R;
import com.app.foodnutritionapp.Util.Constant_Api;
import com.app.foodnutritionapp.Util.Method;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProductDetail extends AppCompatActivity {

    private ViewPager viewPager;
    private Toolbar toolbar;
    private ProductDetailAdapter roomDetailAdapter;
    private int selectPosition = 0;
    private LinearLayout linearLayout;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Method.forceRTLIfSupported(getWindow(), ProductDetail.this);

        Intent in = getIntent();
        selectPosition = in.getIntExtra("position", 0);

        toolbar = findViewById(R.id.toolbar_room_detail);
        viewPager = findViewById(R.id.viewPager_room_detail);

        toolbar.setTitle(Constant_Api.roomLists.get(selectPosition).getRoom_name());
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        linearLayout = findViewById(R.id.linearLayout_room_detail);

        roomDetailAdapter = new ProductDetailAdapter(Constant_Api.roomLists, ProductDetail.this);
        viewPager.setAdapter(roomDetailAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        viewPager.setOffscreenPageLimit(Constant_Api.roomLists.size());
        setCurrentItem(selectPosition);

    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
    }

    //	page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            toolbar.setTitle(Constant_Api.roomLists.get(position).getRoom_name());
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

