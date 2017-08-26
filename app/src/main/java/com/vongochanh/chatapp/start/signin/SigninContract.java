package com.vongochanh.chatapp.start.signin;

/**
 * Created by Vo Ngoc Hanh on 8/22/2017.
 */

public interface SigninContract {
    public interface SigninView {
        void showProgress();

        void hideProgress();

        void onEmailError(String error);

        void onPasswordError(String error);

        void onSuccess();

        void onFailed();
    }

    public interface SigninPresenter {
        void validateAndSignin(String email, String password);

        void onDestroy();
    }

    public interface SigninInteractor {
        interface OnSigninFinishedListener {
            void onEmailError(String error);

            void onPasswordError(String error);

            void onSuccess();

            void onFailed();


        };

        void login(String email, String password);
    }
}
