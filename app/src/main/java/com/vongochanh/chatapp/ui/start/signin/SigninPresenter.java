package com.vongochanh.chatapp.ui.start.signin;


import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.util.Log;

import com.vongochanh.chatapp.R;
import com.vongochanh.chatapp.utils.NetworkChangeManager;

public class SigninPresenter implements SigninContract.Presenter, SigninContract.Interactor.OnSigninFinishedListener,
        NetworkChangeManager.Listener {
    private final String TAG = getClass().getSimpleName();
    private SigninContract.View mView;
    private SigninContract.Interactor mInteractor;

    public SigninPresenter(SigninContract.View view) {
        mView = view;
        mInteractor = new SigninInteractor(this) ;

        NetworkChangeManager.addListener(this);
    }

    /*SigninContract.oginPresenter interface*/
    @Override
    public void validateAndSignin(String email, String password) {
        if (mView != null) {
            mView.showProgress("Login....");
        }

        mInteractor.login(email, password);
    }

    @Override
    public void destroy() {
        if (mView != null) {
            NetworkChangeManager.removeListener();
            mView = null;
            mInteractor = null;
        }
    }
    /*End*/


    /*----------Presenter IMPLEMENT----------*/
    @Override
    public void checkConnection() {
        if (mView == null) {
            Log.d(TAG, "Error: Couldn't find SigninView implement");
            return;
        }

        int connectType = NetworkChangeManager.checkAndGetTypeConnection(mView.getContext());
        processConnectType(connectType);
    }

    private void processConnectType(int connectType) {
        //init
        String connectionState = "";
        int bgViewColor = -1;
        Context context = mView.getContext();
        Resources resources = context.getResources();

        switch (connectType) {
            case ConnectivityManager.TYPE_WIFI:
                connectionState = resources.getString(R.string.connected_by_wifi);
                bgViewColor = resources.getColor(R.color.blue_800);
                break;

            case ConnectivityManager.TYPE_ETHERNET:
                connectionState = resources.getString(R.string.connected_by_ethernet);
                bgViewColor = resources.getColor(R.color.blue_800);
                break;

            case ConnectivityManager.TYPE_MOBILE:
                connectionState = resources.getString(R.string.connected_by_mobile_data);
                bgViewColor = resources.getColor(R.color.orange_800);
                break;

            case ConnectivityManager.TYPE_WIMAX:
                connectionState = resources.getString(R.string.connected_by_wimax);
                bgViewColor = resources.getColor(R.color.purple_800);
                break;

            case -1:
                connectionState = resources.getString(R.string.unconnected_state);
                bgViewColor = resources.getColor(R.color.gray_700);
                break;

            default:
                connectionState = resources.getString(R.string.connected_state);
                bgViewColor = resources.getColor(R.color.light_blue_800);
                break;
        }

        mView.showConnectionState(connectType != -1?true:false, connectionState, bgViewColor);
    }

    @Override
    public void onEmailError(String error) {
        if (mView != null) {
            mView.onEmailError(error);
        }
    }

    @Override
    public void onPasswordError(String error) {
        if (mView != null) {
            mView.onPasswordError(error);
        }
    }

    @Override
    public void onSuccess() {
        if (mView != null) {
            mView.onSuccess();
        }
    }

    @Override
    public void onFailed() {
        if (mView != null) {
            mView.onFailed();
        }
    }
    /*End*/


    /*NetworkChangeReceiver.Listener IMPLEMENT*/
    @Override
    public void onChangeConnectionStatus(int connectType) {
        processConnectType(connectType);
    }
}
