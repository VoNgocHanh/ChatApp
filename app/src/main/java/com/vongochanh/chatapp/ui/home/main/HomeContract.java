package com.vongochanh.chatapp.ui.home.main;

import com.vongochanh.chatapp.base.BasePresenter;
import com.vongochanh.chatapp.base.BaseProgressView;
import com.vongochanh.chatapp.base.BaseView;

/**
 * Created by Vo Ngoc Hanh on 8/24/2017.
 */

public interface HomeContract {
    interface View extends BaseProgressView, BaseView{
        void updateUI();

        void backToSigninScreen();
    }

    interface Presenter extends BasePresenter {
        void selectMenuItem(int currentItem);

        int getCurrentItem();

        void logout();
    }
}
