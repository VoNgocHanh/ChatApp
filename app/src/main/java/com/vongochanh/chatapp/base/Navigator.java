package com.vongochanh.chatapp.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Vo Ngoc Hanh on 8/25/2017.
 */

public class Navigator {
    public void navigatorTo(NavigatorBaseActivity viewSource, IDestinationAct destination, Object object) {
        Intent intent = destination.getCallingIntent(viewSource, object);
        viewSource.startActivity(intent);
    }
}
