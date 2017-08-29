package com.vongochanh.chatapp.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

/**
 * Created by Vo Ngoc Hanh on 8/25/2017.
 */

public class Navigator {

    public static void navigateTo(Context context, Class des) {
        Intent intent = new Intent(context, des);
        context.startActivity(intent);
    }

    public static void navigateTo(Context context, Class des, Map<String,Object> extrasMap) {
        //Log.d(TAG, "Couldn't navigate to other activity. Maybe in toJson() of Gson class. Detail: " + e.getMessage());

        Intent intent = new Intent(context, des);
        for (String key : extrasMap.keySet()) {
            intent.putExtra(key, new Gson().toJson(extrasMap.get(key)));
        }
        context.startActivity(intent);
    }
}
