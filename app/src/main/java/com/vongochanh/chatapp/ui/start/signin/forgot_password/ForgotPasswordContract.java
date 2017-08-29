package com.vongochanh.chatapp.ui.start.signin.forgot_password;

/**
 * Created by Vo Ngoc Hanh on 8/23/2017.
 */

public interface ForgotPasswordContract {
    public interface View{
        void showProgress();

        void hideProgress();

        void showEmailValidateError(String error);

        void showMessage(String message);

        void onRequestSuccess();

        void onRequestFailed();
    }

    public interface Presenter{
        void validateAndSendRequest(String email);

        void destroy();
    }
}
