package com.vongochanh.chatapp.home.search_user;

import com.vongochanh.chatapp.model.User;

/**
 * Created by Vo Ngoc Hanh on 8/25/2017.
 */

public interface SearchingUserLitener {
    interface HolderClickListener{
        void onUserHolderClick(int holderPosition);
    }

    interface ItemClickListener{
        void onUserItemClick(User user);
    }
}
