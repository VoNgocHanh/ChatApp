package com.vongochanh.chatapp.ui.home.search_user;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vongochanh.chatapp.utils.Constant;
import com.vongochanh.chatapp.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vo Ngoc Hanh on 8/25/2017.
 */

public class SearchingUserPresenter implements SearchingUserContract.Presenter {
    private final String TAG = getClass().getSimpleName();

    SearchingUserContract.View mView;

    //firebase
    DatabaseReference mDatabase;

    public SearchingUserPresenter(SearchingUserContract.View view) {
        this.mView = view;
        mDatabase = FirebaseDatabase.getInstance().getReference("/"+ Constant.USERS_NODE+"/");
    }


    /*------ Presenter IMPLEMENT ------*/
    @Override
    public void searchUserByEmail(String emailUser) {
        if (validate(emailUser)) {
            if (mView != null) {
                mView.showProgress("");
            }

            Query query = mDatabase.orderByChild(Constant.USER_EMAIL_NODE).startAt(emailUser).endAt(emailUser+"~");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<User> list = new ArrayList<User>();
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String email = childSnapshot.child(Constant.USER_EMAIL_NODE).getValue(String.class);
                        list.add(new User(email, ""));
                    }

                    if (mView != null) {
                        mView.showFoundedUsers(list);
                        mView.hideProgress();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "The read failed: " + databaseError.getMessage());
                }
            });
        }
    }

    private boolean validate(String emailUser) {
        if (TextUtils.isEmpty(emailUser)) {
            if (mView != null) {
                mView.showError("Please enter email you want to search...");
            }

            return false;
        }

        return true;
    }
    /*------ //Presenter IMPLEMENT ------*/

    @Override
    public void destroy() {
        if (mView != null) {
            mView = null;
        }
    }
}
