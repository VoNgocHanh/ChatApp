package com.vongochanh.chatapp.ui.start.signup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vongochanh.chatapp.R;
import com.vongochanh.chatapp.ui.start.signin.SigninActivity;

public class SignupActivity extends AppCompatActivity implements SignupContract.SignupView{
    private final String TAG = getClass().getSimpleName();

    private SignupContract.SignupPresenter mPresenter;

    //views
    EditText mEmailEdt, mPasswordEdt, mConfirmPasswordEdt;
    Button mSignupButton;
    TextView mSigninLink;

    /*Lifecycle*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();
    }

    private void init() {
        //presenter
        mPresenter = new SignupPresenterImpl(this);

        //inflate views
        mEmailEdt = (EditText) findViewById(R.id.emailUser_signup);
        mPasswordEdt = (EditText) findViewById(R.id.password_signup);
        mConfirmPasswordEdt = (EditText) findViewById(R.id.confirmPassword_signup);
        mSignupButton = (Button) findViewById(R.id.submit_signup);
        mSigninLink = (TextView) findViewById(R.id.signin_link);

        //set listeners
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        mSigninLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSigninScreen();
            }
        });
    }

    private void signup() {
        if (mPresenter != null) {
            String userEmail = mEmailEdt.getText().toString();
            String password = mPasswordEdt.getText().toString();
            String confirmPassword = mConfirmPasswordEdt.getText().toString();

            mPresenter.signup(userEmail, password, confirmPassword);
        }
    }

    private void openSigninScreen() {
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
    }

    /*End lifecycle*/

    /*SignupView methods*/
    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void clearText() {
        mPasswordEdt.setText("");
        mConfirmPasswordEdt.setText("");
    }

    ProgressDialog mCreatingDialog = null;
    @Override
    public void showProgress(String mesage) {
        if (mCreatingDialog == null) {
            mCreatingDialog = new ProgressDialog(this);
            mCreatingDialog.setMessage(mesage);
            mCreatingDialog.setIndeterminate(true);
            mCreatingDialog.setCancelable(false);
        }

        mCreatingDialog.show();
    }

    @Override
    public void hideProgress() {
        if (mCreatingDialog != null) {
            mCreatingDialog.dismiss();
        }
    }

    @Override
    public void onSignupSuccess() {
        if (mCreatingDialog != null) {
            mCreatingDialog.dismiss();
        }

        String message = getResources().getString(R.string.signup_successfully);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, SigninActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSignupFailed() {
        if (mCreatingDialog != null) {
            mCreatingDialog.dismiss();
        }

        String error = getResources().getString(R.string.signup_failed);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void emailValidateFailed(String error) {
        if (mEmailEdt != null) {
            mEmailEdt.setError(error);
        }
    }

    @Override
    public void passwordValidateFailed(String error) {
        if (mPasswordEdt != null) {
            mPasswordEdt.setError(error);
        }
    }

    @Override
    public void confirmPasswordValidateFailed(String error) {
        if (mConfirmPasswordEdt != null) {
            mConfirmPasswordEdt.setError(error);
        }
    }
    /*End SignupView*/
}
