package com.vongochanh.chatapp.ui.start.signin.forgot_password;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Vo Ngoc Hanh on 8/23/2017.
 */

public class ForgotPasswordPresenter implements ForgotPasswordContract.Presenter {
    //mvp
    private ForgotPasswordContract.View mView;

    //Firebase
    FirebaseAuth mAuth;


    public ForgotPasswordPresenter(ForgotPasswordContract.View view) {
        this.mView = view;
        mAuth = FirebaseAuth.getInstance();
    }

    /*Presenter interface methods*/
    @Override
    public void validateAndSendRequest(String email) {
        if (validateEmail(email)) {
            if (mView != null) {
                mView.showProgress();
        }

        //send request
        mAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    onCompleteRequestRestPassword(task);
                }
            });
        }
    }

    private void onCompleteRequestRestPassword(Task<Void> task) {
        if (task.isSuccessful()) {
            if (mView != null) {
                mView.onRequestSuccess();
            }
        } else {
            if (mView != null) {
                mView.onRequestFailed();
            }
        }

        if (mView != null) {
            mView.hideProgress();
        }
    }

    private boolean validateEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            showEmailError("Email is empty");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showEmailError("Email is invalid");
            return false;
        }

        return true;
    }

    private void showEmailError(String error) {
        if (mView != null) {
            mView.showEmailValidateError(error);
        }
    }

    @Override
    public void destroy() {
        if (mView != null) {
            mView = null;
        }
    }
    /*End Presenter interface methods*/
}
