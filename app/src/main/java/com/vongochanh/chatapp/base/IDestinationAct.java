package com.vongochanh.chatapp.base;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Vo Ngoc Hanh on 8/25/2017.
 */

public interface IDestinationAct {
    public Intent getCallingIntent(AppCompatActivity from, Object object);
}
