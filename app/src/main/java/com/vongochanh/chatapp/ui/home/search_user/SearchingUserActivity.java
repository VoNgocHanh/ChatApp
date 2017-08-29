package com.vongochanh.chatapp.ui.home.search_user;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vongochanh.chatapp.R;
import com.vongochanh.chatapp.model.User;
import com.vongochanh.chatapp.ui.home.room.chat_room.ChatRoomActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchingUserActivity extends AppCompatActivity implements SearchingUserContract.View,
        SearchingUserContract.Listener.ItemClickListener {
    //mvp
    SearchingUserContract.Presenter mPresenter;

    //views
    EditText mSearchUserField;
    ImageView mSearchButton;
    ProgressBar mProgressBar;

    RecyclerView mUserListView;
    SU_RecyclerViewAdapter mAdapter;

    public SearchingUserActivity() {
        this.mPresenter = new SearchingUserPresenter(this);
    }

    /*------- LIFECYCLE ACTIVITY -------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_user);

        initToolbar();
        initViews();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_searchingUserAct);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        //inflate views
        mSearchUserField = (EditText) findViewById(R.id.search_user_field);
        mSearchButton = (ImageView) findViewById(R.id.search_user_button);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_searching_user);
        mUserListView = (RecyclerView) findViewById(R.id.list_finding_user);

        //set listeners
        View.OnKeyListener enterKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUserByEmail();
                }
                return false;
            }
        };

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUserByEmail();
            }
        });

        List<User> userList = new ArrayList<>();
        mAdapter = new SU_RecyclerViewAdapter(this /*Context*/, this /*listener*/, userList /*users list*/);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mUserListView.setAdapter(mAdapter);
        mUserListView.setLayoutManager(manager);
        mUserListView.setHasFixedSize(true);
    }

    private void searchUserByEmail() {
        String emailUserKey = mSearchUserField.getText().toString();
        if (mPresenter != null) {
            mPresenter.searchUserByEmail(emailUserKey);
        }
    }

    /*------- END LIFECYCLE ACTIVITY -------*/

    /*--------- View IMPLEMENT ---------*/
    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(String message) {
        mProgressBar.setVisibility(View.VISIBLE);
        mUserListView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        mUserListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFoundedUsers(List<User> userList) {
        if (mAdapter != null) {
            mAdapter.resetData(userList);
        }
    }

    /*--------- SearchingUserLitener.ItemClickListener IMPLEMENT ---------*/
    @Override
    public void onUserItemClick(User user) {
        //onBackPress to Home Activity
        Intent intent = new Intent(this, ChatRoomActivity.class);
        intent.putExtra(ChatRoomActivity.EXTRA_EMAIL_USER, new Gson().toJson(user));
        startActivity(intent);
    }
}
