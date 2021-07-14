package com.app.foodnutritionapp.Util;

import com.app.foodnutritionapp.BuildConfig;
import com.app.foodnutritionapp.Item.ProductList;

import java.util.ArrayList;
import java.util.List;

public class Constant_Api {

    public static String url = BuildConfig.My_api;
    public static String tag = "FOOD_NUTRITION";
    public static String login = url + "api.php?users_login";
    public static String register = url + "api.php?user_register";
    public static String forgetPassword = url + "api.php?forgot_pass&email=";
    public static String profile = url + "api.php?user_profile&id=";
    public static String profileUpdate = url + "api.php?user_profile_update&user_id=";
    public static String room = url + "api.php?product_list";
    public static String roomDetail = url + "api.php?prod_id=";
    public static String modifydata = url + "api.php?user_modify_data";
    public static List<ProductList> roomLists = new ArrayList<>();

}
