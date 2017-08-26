package com.vongochanh.chatapp.start.sign_up;

import android.util.Patterns;

import com.vongochanh.chatapp.R;

/**
 * Created by Vo Ngoc Hanh on 8/22/2017.
 */

public class SignupPresenterImpl implements SignupContract.SignupPresenter,
                                            SignupContract.SignupInteractor.SignupProgressListener{

    private SignupContract.SignupView mView;
    private SignupContract.SignupInteractor mInteractor;

    public SignupPresenterImpl(SignupContract.SignupView mView) {
        this.mView = mView;
        mInteractor = new SignupInteractorImpl(this);
    }

    /*Presenter interface methods*/
    @Override
    public void signup(String userEmail, String password, String confirmPassword) {
        if (mView != null) {
            mView.showProgress("Creating account...");

            if (validate(userEmail, password, confirmPassword)) {
                if (mInteractor != null) {
                    //tao handle thuc hien signup progress
                    mInteractor.createAccount(userEmail, password);
                }
            }
        }
    }

    private boolean validate(String userEmail, String password, String confirmPassword) {
        boolean validate = true;

        //Validate email
        boolean isMatch = Patterns.EMAIL_ADDRESS.matcher(userEmail).matches();
        String emailError = "";
        if (userEmail == null ) {
            emailError = mView.getContext().getString(R.string.empty_email_validate_error);
            validate = false;
        }else if(!isMatch){
            emailError = mView.getContext().getString(R.string.email_validate_error);
            validate = false;
        }

        if (!validate && mView != null) {
            mView.emailValidateFailed(emailError);
        }

        //Validate password
        if (password == null || password.length() < 3) {
            validate = false;
            if (mView != null) {
                String passwordValidateError = mView.getContext().getString(R.string.password_validate_error);
                mView.passwordValidateFailed(passwordValidateError);
            }
        }

        //Validate confirm password
        if (confirmPassword == null || !confirmPassword.equals(password)) {
            validate = false;
            if (mView != null) {
                String confirmPassValidateError = mView.getContext().getString(R.string.confirm_password_validate_error);
                mView.confirmPasswordValidateFailed(confirmPassValidateError);
            }
        }

        return validate;
    }

    @Override
    public void destroy() {
        if (mView != null) {
            mView = null;
        }
    }
    /*End*/

    /*SignupInteractorImpl interface methods*/
    @Override
    public void onSuccess() {
        if (mView != null) {
            mView.onSignupSuccess();
        }
    }

    @Override
    public void onFailed() {
        if (mView != null) {
            mView.onSignupFailed();
        }
    }
    /*End*/
}
