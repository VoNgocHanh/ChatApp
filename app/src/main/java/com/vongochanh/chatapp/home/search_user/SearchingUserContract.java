package com.vongochanh.chatapp.home.search_user;

import com.vongochanh.chatapp.base.BasePresenter;
import com.vongochanh.chatapp.base.BaseProgressView;
import com.vongochanh.chatapp.base.BaseView;
import com.vongochanh.chatapp.model.User;

import java.util.List;

/**
 * Created by Vo Ngoc Hanh on 8/25/2017.
 */

public interface SearchingUserContract {
    interface View extends BaseView, BaseProgressView {
        void showError(String error);

        void showFoundedUsers(List<User> userList);
    }

    interface Presenter extends BasePresenter{
        void searchUserByEmail(String emailUser);
    }

    interface Listener{
        interface HolderClickListener{
            void onUserHolderClick(int holderPosition);
        }

        interface ItemClickListener{
            void onUserItemClick(User user);
        }
    }
}
