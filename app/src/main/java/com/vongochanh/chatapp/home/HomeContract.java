package com.vongochanh.chatapp.home;

import com.vongochanh.chatapp.base.BasePresenter;
import com.vongochanh.chatapp.base.BaseProgressView;

/**
 * Created by Vo Ngoc Hanh on 8/24/2017.
 */

public interface HomeContract {
    public interface View extends BaseProgressView{
        void backToSigninScreen();
    }

    public interface Presenter extends BasePresenter {
        void signOut();
    }
}
