package com.app.foodnutritionapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.foodnutritionapp.Item.ProductList;
import com.app.foodnutritionapp.R;
import com.app.foodnutritionapp.Util.Constant_Api;
import com.app.foodnutritionapp.Util.Method;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by admin on 08-08-2017.
 */

public class ProductDetailAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private List<ProductList> roomDetailLists;
    private Activity activity;
    private int columnWidth;
    private Method method;
    private String msg = "";
    private ProgressDialog progressDialog;

    public ProductDetailAdapter(List<ProductList> roomDetailLists, Activity activity) {
        this.roomDetailLists = roomDetailLists;
        this.activity = activity;
        method = new Method(activity);
        columnWidth = method.getScreenWidth();
        progressDialog = new ProgressDialog(activity);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.product_detail_adapter, container, false);

        ArrayList<String> arrayList = new ArrayList<>();

        TextView textView_roomName = view.findViewById(R.id.textView_roomName_room_detail_adapter);
        TextView textView_description = view.findViewById(R.id.textView_roomDescription_room_detail_adapter);
        SliderLayout mDemoSlider = view.findViewById(R.id.slider_room_detail_adapter);

        mDemoSlider.setLayoutParams(new LinearLayout.LayoutParams(columnWidth, columnWidth / 2 + 80));

        roomDetail(position, arrayList, mDemoSlider);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);

        textView_roomName.setText(roomDetailLists.get(position).getRoom_name());
        textView_description.setText(Html.fromHtml(roomDetailLists.get(position).getRoom_description()));

        textView_roomName.setTypeface(method.typeface);

        String mimeType = "text/html";
        String encoding = "utf-8";
        String htmlText = roomDetailLists.get(position).getRoom_rules();

        String text = "<html><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/fonts/poppins_regular.ttf\")}body{font-family: MyFont;}"
                + "</style></head>"
                + "<body>"
                + htmlText
                + "</body></html>";

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return roomDetailLists.size();
    }

    private void roomDetail(final int position, final ArrayList<String> arrayList, final SliderLayout mDemoSlider) {

        String url = Constant_Api.roomDetail + roomDetailLists.get(position).getId();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.d("Response_Room", new String(responseBody));
                String res = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(res);

                    JSONArray jsonArray = jsonObject.getJSONArray(Constant_Api.tag);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        String image_name = null;

                        JSONObject object = jsonArray.getJSONObject(i);

                        JSONArray image_array = object.getJSONArray("galley_image");
                        for (int j = 0; j < image_array.length(); j++) {
                            JSONObject image = image_array.getJSONObject(j);
                            image_name = image.getString("image_name");
                            arrayList.add(image_name);
                        }
                        roomDetailLists.get(position).setArrayImage(arrayList);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < roomDetailLists.get(position).getArrayImage().size(); i++) {
                    DefaultSliderView defaultSliderView = new DefaultSliderView(activity);
                    // initialize a SliderLayout
                    defaultSliderView
                            .image(roomDetailLists.get(position).getArrayImage().get(i))
                            .setScaleType(BaseSliderView.ScaleType.Fit);
                    mDemoSlider.addSlider(defaultSliderView);
                }
                mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                mDemoSlider.getPagerIndicator().setDefaultIndicatorColor(activity.getResources().getColor(R.color.selectedColor)
                        , activity.getResources().getColor(R.color.unselectedColor));
                mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
