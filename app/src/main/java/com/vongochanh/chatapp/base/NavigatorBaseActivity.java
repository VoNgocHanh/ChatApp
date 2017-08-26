package com.vongochanh.chatapp.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Vo Ngoc Hanh on 8/25/2017.
 */

public class NavigatorBaseActivity extends AppCompatActivity {
    protected Navigator mNavigator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mNavigator = new Navigator();
    }
}
