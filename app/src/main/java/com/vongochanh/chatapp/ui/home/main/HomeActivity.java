package com.vongochanh.chatapp.ui.home.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vongochanh.chatapp.R;
import com.vongochanh.chatapp.ui.start.signin.SigninActivity;


public class HomeActivity extends AppCompatActivity implements HomeContract.View {
    private final String TAG = getClass().getSimpleName();
    private HomeContract.Presenter mPresenter;

    //Views
    Toolbar mToolbar;
    NavigationView mNavigationView;
    DrawerLayout mDrawerLayout;

    //Navigation View menu
    View mNavHeader;
    ImageView mBgImage_headerNav, mProfileImage_headerNav;
    TextView mUsername_headerNav, mEmailUser_headerNav;

    //toolbar titles respected to selected nav menu item
    String[] mToolbarTitles;

    //flag to load home fragment (roomlistfragment) when user click back button
    boolean shouldLoadHomeFragOnBackPress = true;

    /*LIFECYCLE methods*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPresenter = new HomePresenter(this /*AppcompatActivity argument*/, this/*HomeContract.View interface*/);

        initToolbar();

        initViews();

        loadNavHeader();

        initNavigationMenu();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.selectMenuItem(HomePresenter.ROOM_ITEM);
        }
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_home_activity);
        setSupportActionBar(mToolbar);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_home_menu);
        mToolbar.setOverflowIcon(drawable);

        mToolbarTitles = getResources().getStringArray(R.array.home_act_action_titles);
    }

    private void initViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_act_home);
        mNavigationView = (NavigationView) findViewById(R.id.nav_act_home);

        //NavigationView header
        mNavHeader = mNavigationView.getHeaderView(0);
        mBgImage_headerNav = (ImageView) mNavHeader.findViewById(R.id.image_bg_nav_header_act_home);
        mProfileImage_headerNav = (ImageView) mNavHeader.findViewById(R.id.image_profile_nav_header_act_home);
        mUsername_headerNav = (TextView) mNavHeader.findViewById(R.id.username_nav_header_act_home);
        mEmailUser_headerNav = (TextView) mNavHeader.findViewById(R.id.email_user_nav_header_act_home);
    }

    private void loadNavHeader() {
        mUsername_headerNav.setText("nghiamy15");
        mEmailUser_headerNav.setText("nghiamy15@gmail.com");

        //Loading header background image
        mBgImage_headerNav.setBackgroundResource(R.drawable.nav_menu_header_bg);

        //Loading profile image
        mProfileImage_headerNav.setBackgroundResource(R.drawable.nav_menu_profile_image);
    }

    private void initNavigationMenu() {
        NavigationView.OnNavigationItemSelectedListener navItemSelectedListener =
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (mPresenter == null) {
                    Log.d(TAG, "Couldn't find presenter instance");
                    return false;
                }

                mNavigationView.getMenu().getItem(mPresenter.getCurrentItem()).setActionView(null);

                mDrawerLayout.closeDrawers();

                //set infor to open equivalent fragment in main content
                int itemId = item.getItemId();
                int curNavItem;
                switch (itemId) {
                    case R.id.nav_rooms_act_home:
                        curNavItem = HomePresenter.ROOM_ITEM;
                        break;

                    case R.id.nav_information_act_home:
                        curNavItem = HomePresenter.INFORMATION_ITEM;
                        break;

                    case R.id.nav_reset_password_act_home:
                        curNavItem = HomePresenter.RESET_PASSWORD_ITEM;
                        break;

                    case R.id.nav_photo_profile_act_home:
                        curNavItem = HomePresenter.PHOTO_PROFILE_ITEM;
                        break;

                    case R.id.nav_logout_act_home:
                        mDrawerLayout.closeDrawers();
                        logout();
                        return true;

                    default:
                        Log.d(TAG, "Couldn't know menu item id");
                        return false;
                }

                mPresenter.selectMenuItem(curNavItem);

                return true;
            }
        };

        mNavigationView.setNavigationItemSelectedListener(navItemSelectedListener);

        //Set ActionBarDrawerToogle listener
        ActionBarDrawerToggle abDrawerToggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer){
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                    }
                };

        mDrawerLayout.addDrawerListener(abDrawerToggle);

        abDrawerToggle.syncState();
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(mToolbarTitles[mPresenter.getCurrentItem()]);
    }

    private void setCurrentMenuItemView(int curNavItem) {
        MenuItem curMenuItem = mNavigationView.getMenu().getItem(curNavItem);

        curMenuItem.setChecked(true);
        curMenuItem.setActionView(R.layout.action_view_nav_item_act_home);
    }

    /*IMPLEMENT HomeContract.View interface*/
    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void updateUI() {
        if (mPresenter == null) {
            Log.d(TAG, "Couldn't update UI because not found HomeContract.Presenter instance.");
            return;
        }

        int curNavItem = mPresenter.getCurrentItem();

        setCurrentMenuItemView(curNavItem);
        setToolbarTitle();

        //refresh toolbar menu
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return;
        }

        if (shouldLoadHomeFragOnBackPress) {
            if (mPresenter.getCurrentItem() != HomePresenter.ROOM_ITEM) {
                mPresenter.selectMenuItem(HomePresenter.ROOM_ITEM);
                return;
            }
        }

        super.onBackPressed();
    }
    /*END IMPLEMENT*/


    /*TOOLBAR MENU*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_menu_item_act_home:

                return true;

            case R.id.logout_menu_item_act_home:
                logout();
                return true;

            default:
                return true;
        }
    }

    //Need to be write logout usecase to avoid error
    private void logout() {
        if (mPresenter != null) {
            mPresenter.logout();
            finish();
        }else{
            Log.d(TAG, "Couldn't log out.");
        }
    }
    /*End TOOLBAR MENU*/

    /*End Lifecyle methods*/

    /*---------- View IMPLEMENT ----------*/
    @Override
    public void backToSigninScreen() {
        Intent intent = new Intent(this, SigninActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        finish();
    }

    ProgressDialog mProgressDialog;

    @Override
    public void showProgress(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(message);
        }

        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "HomeActivity onStop().");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }
}
