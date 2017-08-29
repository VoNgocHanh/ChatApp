package com.vongochanh.chatapp.ui.home.search_user;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vongochanh.chatapp.R;
import com.vongochanh.chatapp.model.User;

/**
 * Created by Vo Ngoc Hanh on 8/25/2017.
 */

public class SearchingUserHolder extends RecyclerView.ViewHolder {

    private SearchingUserContract.Listener.HolderClickListener mListener;
    private int mHolderPosition = -1;

    ImageView userIconView;
    TextView emailUserView;

    public SearchingUserHolder(View itemView, SearchingUserContract.Listener.HolderClickListener listener) {
        super(itemView);
        mListener = listener;

        initViews();
        initListeners();
        //set listener for itemView ?
    }

    private void initViews() {
        this.userIconView = (ImageView) itemView.findViewById(R.id.user_icon_founded_user_item);
        this.emailUserView = (TextView) itemView.findViewById(R.id.email_user_founded_user_item);
    }

    private void initListeners() {
        userIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickThisHolder();
            }
        });

        emailUserView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickThisHolder();
            }
        });
    }

    private void onClickThisHolder() {
        if (mListener != null && mHolderPosition > 0) {
            mListener.onUserHolderClick(mHolderPosition);
        }
    }

    public void render(User user) {
        emailUserView.setText(user.getEmailUser());

        //load icon from server

    }

    public void updatePosition(int holderPosition) {
        mHolderPosition = holderPosition;
    }
}
