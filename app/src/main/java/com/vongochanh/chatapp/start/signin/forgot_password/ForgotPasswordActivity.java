package com.vongochanh.chatapp.start.signin.forgot_password;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vongochanh.chatapp.R;

public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordContract.View{
    //mvp
    ForgotPasswordContract.Presenter mPresenter;

    //views
    EditText mEmailField;
    Button mSendEmailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        init();
    }

    private void init() {
        //presenter
        mPresenter = new ForgotPasswordPresenter(this);

        //home button
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_forgotPassword);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //inflate views
        mEmailField = (EditText) findViewById(R.id.email_forgotPassword);
        mSendEmailButton = (Button) findViewById(R.id.sendEmailResetPassword);

        //set listeners
        mSendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailResetPassword();
            }
        });
    }

    private void sendEmailResetPassword() {
        String email = mEmailField.getText().toString();
        if (mPresenter != null) {
            mPresenter.validateAndSendRequest(email);
        }
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

    /*View interface methods*/
    ProgressDialog mProgressDialog = null;

    @Override
    public void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Sending email...");
            mProgressDialog.setCancelable(false);
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
    public void showEmailValidateError(String error) {
        if (mEmailField != null) {
            mEmailField.setError(error);
        }
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mSendEmailButton, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRequestSuccess() {
        showMessage("Send request to your email successfully! Please wait few minute to received email from firebase");
    }

    @Override
    public void onRequestFailed() {
        showMessage("Send request to your email failed! Please try again");
    }
    /*End View interface*/
}
