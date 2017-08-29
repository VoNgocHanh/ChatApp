package com.vongochanh.chatapp.ui.start.signup;

import android.content.Context;

import com.vongochanh.chatapp.base.BasePresenter;
import com.vongochanh.chatapp.base.BaseProgressView;

/**
 * Created by Vo Ngoc Hanh on 8/22/2017.
 */

public interface SignupContract {
    interface SignupView extends BaseProgressView{
        Context getContext();

        void clearText();

        void onSignupSuccess();

        void onSignupFailed();

        void emailValidateFailed(String error);

        void passwordValidateFailed(String error);

        void confirmPasswordValidateFailed(String error);
    }

    interface SignupPresenter extends BasePresenter{
        void signup(String name, String password, String confirmPassword);

    }

    interface SignupInteractor {
        interface SignupProgressListener{
            void onSuccess();

            void onFailed();
        }

        void createAccount(String userEmail, String password);
    }
}
