package com.vongochanh.chatapp.start.signin;

import android.content.Context;

import com.vongochanh.chatapp.base.BasePresenter;
import com.vongochanh.chatapp.base.BaseProgressView;

/**
 * Created by Vo Ngoc Hanh on 8/22/2017.
 */

public interface SigninContract {
    interface View extends BaseProgressView{
        Context getContext();

        void showConnectionState(boolean isConnected, String connectionState, int bgStateViewColor);

        void onEmailError(String error);

        void onPasswordError(String error);

        void onSuccess();

        void onFailed();
    }

    interface Presenter extends BasePresenter{
        void checkConnection();

        void validateAndSignin(String email, String password);
    }

    interface Interactor {
        interface OnSigninFinishedListener {
            void onEmailError(String error);

            void onPasswordError(String error);

            void onSuccess();

            void onFailed();
        };

        void login(String email, String password);
    }
}
