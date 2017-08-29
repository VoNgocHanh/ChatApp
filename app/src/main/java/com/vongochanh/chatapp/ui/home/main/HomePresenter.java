package com.vongochanh.chatapp.ui.home.main;

/**
 * Created by Vo Ngoc Hanh on 8/24/2017.
 */

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.vongochanh.chatapp.R;
import com.vongochanh.chatapp.ui.home.information_user.InformationUserFragment;
import com.vongochanh.chatapp.ui.home.photo_profile.PhotoProfileFragment;
import com.vongochanh.chatapp.ui.home.reset_password.ResetPasswordFragment;
import com.vongochanh.chatapp.ui.home.room.list_rooms.RoomListFragment;

public class HomePresenter implements HomeContract.Presenter {
    private final String TAG = getClass().getSimpleName();

    //CONSTANT
    public static final int ROOM_ITEM = 0;
    public static final int INFORMATION_ITEM = 1;
    public static final int RESET_PASSWORD_ITEM = 2;
    public static final int PHOTO_PROFILE_ITEM = 3;

    String mCurrentTag = "";
    final String ROOMS_TAG = "HOME TAG";
    final String INFORMATION_TAG = "INFORMATION TAG";
    final String RESET_PASSWORD_TAG = "RESET PASSWORD TAG";
    final String PHOTO_PROFILE_TAG = "PHOTO PROFILE TAG";

    private int mCurrentItem = 0;

    private AppCompatActivity mActivity;
    private HomeContract.View mView;

    private FirebaseAuth mAuth;

    private Handler mHandler;

    public HomePresenter(AppCompatActivity activity, HomeContract.View view) {
        this.mActivity = activity;
        this.mView = view;
        mHandler = new Handler();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void selectMenuItem(int currentItem) {
        if (currentItem <= 0 || currentItem > 3) {
            mCurrentItem = 0;
        }else{
            mCurrentItem = currentItem;
        }
        if (mView == null) {
            Log.d(TAG, "Couldn't found HomeContract.View implement");
        }

        mView.updateUI();

        loadFragment();
    }

    @Override
    public int getCurrentItem() {
        return mCurrentItem;
    }

    private void loadFragment() {
        final Fragment fragment = getFragment();
        if (fragment != null) {
            final FragmentManager manager = mActivity.getSupportFragmentManager();

            if (manager.findFragmentByTag(mCurrentTag) != null) {
                //do nothing
                return;
            }

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    transaction.replace(R.id.frame_main_content_activity_home, fragment, mCurrentTag);
                    transaction.commitAllowingStateLoss();
                }
            };

            if (mHandler != null) {
                mHandler.post(runnable);
            }
        }
    }

    @Override
    public void logout() {
        if (mAuth != null) {
            if (mView != null) {
                mView.showProgress("Loging out...");
            }

            mAuth.signOut();

            if (mView != null) {
                mView.hideProgress();
                mView.backToSigninScreen();
            }
        }
    }

    private Fragment getFragment() {
        switch (mCurrentItem) {
            case ROOM_ITEM:
                mCurrentTag = ROOMS_TAG;
                return new RoomListFragment();

            case INFORMATION_ITEM:
                mCurrentTag = INFORMATION_TAG;
                return new InformationUserFragment();

            case RESET_PASSWORD_ITEM:
                mCurrentTag = RESET_PASSWORD_TAG;
                return new ResetPasswordFragment();

            case PHOTO_PROFILE_ITEM:
                mCurrentTag = PHOTO_PROFILE_TAG;
                return new PhotoProfileFragment();

            default:
                mCurrentTag = "";
                return null;
        }
    }

    @Override
    public void destroy() {
        if (mView != null) {
            mView = null;
        }
    }
}
