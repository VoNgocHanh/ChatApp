package com.vongochanh.chatapp.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Vo Ngoc Hanh on 8/26/2017.
 */

public class NetworkChangeManager extends BroadcastReceiver {
    public interface Listener {
        void onChangeConnectionStatus(int status);
    }

    public final static int TYPE_NONE = -1;

    private final String TAG = getClass().getSimpleName();
    private static Listener mlistener;

    public static void addListener(Listener listener) {
        mlistener = listener;
    }

    public static void removeListener() {
        mlistener = null;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int status = checkAndGetTypeConnection(context);

            mlistener.onChangeConnectionStatus(status);
        }
    }

    public static int checkAndGetTypeConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return networkInfo.getType();
        }

        /*Case unnconected*/
        return TYPE_NONE;
    }
}
