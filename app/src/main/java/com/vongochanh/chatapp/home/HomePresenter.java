package com.vongochanh.chatapp.home;

/**
 * Created by Vo Ngoc Hanh on 8/24/2017.
 */

import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.vongochanh.chatapp.home.HomeContract.View;

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;

    private FirebaseAuth mAuth;

    public HomePresenter(View view) {
        this.mView = view;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signOut() {
        if (mAuth != null) {
            mAuth.signOut();

            if (mView != null) {
                mView.hideProgress();
                mView.backToSigninScreen();
            }
        }
    }

    @Override
    public void destroy() {
        if (mView != null) {
            mView = null;
        }
    }
}
