package com.vongochanh.chatapp.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.vongochanh.chatapp.R;
import com.vongochanh.chatapp.home.search_user.SearchingUserActivity;
import com.vongochanh.chatapp.start.signin.SigninActivity;


public class HomeActivity extends AppCompatActivity implements HomeContract.View {
    private HomeContract.Presenter mPresenter;

    //views
    EditText mOpenSearchingUserScreenView;
    TextView mConnectedView;
    RecyclerView mUserListView;

    /*LIFECYCLE methods*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPresenter = new HomePresenter(this);

        initViews();
    }

    private void initViews() {
        //init toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_home_menu);
        toolbar.setOverflowIcon(drawable);

        //inflate views
        mOpenSearchingUserScreenView = (EditText) findViewById(R.id.open_searchingUserAct_homeAct);
        mUserListView = (RecyclerView) findViewById(R.id.users_listview_home);

        //initialize
        mOpenSearchingUserScreenView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchingUserScreen();
            }
        });
    }

    private void openSearchingUserScreen() {
        Intent intent = new Intent(this, SearchingUserActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting_homeMenu:

                return true;

            case R.id.logout_homeMenu:
                signOut();
                return true;

            default:
                return true;
        }
    }

    private void signOut() {
        if (mPresenter != null) {
            showProgress("Loging out...");
            mPresenter.signOut();
        }
    }

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
}
