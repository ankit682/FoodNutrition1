package com.app.foodnutritionapp.Util;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.app.foodnutritionapp.R;
import com.onesignal.OneSignal;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by admin on 12-08-2017.
 */

public class CalligraphyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .init();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/poppins_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }

    }
}
