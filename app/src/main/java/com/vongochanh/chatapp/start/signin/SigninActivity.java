package com.vongochanh.chatapp.start.signin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.vongochanh.chatapp.R;
import com.vongochanh.chatapp.base.CheckingInternetConnection;
import com.vongochanh.chatapp.home.HomeActivity;
import com.vongochanh.chatapp.start.sign_up.SignupActivity;
import com.vongochanh.chatapp.start.signin.forgot_password.ForgotPasswordActivity;

public class SigninActivity extends AppCompatActivity implements SigninContract.SigninView {
    private String TAG = getClass().getSimpleName();

    private FirebaseAuth mAuth;

    private SigninContract.SigninPresenter mPresenter;

    //Views
    private TextView mConnectionStateView;
    private EditText mEmailField, mPasswordField;
    private CheckBox mShowPassCheckbox;
    private Button mLoginButton;
    private TextView mSignupLink, mForgotPasswordLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        checkConnection();
    }

    private void checkConnection() {
        if (mConnectionStateView == null) {
            return;
        }

        mConnectionStateView.setVisibility(View.VISIBLE);
        String connectedState;
        int bgColor = -1;

        int connectType = CheckingInternetConnection.checkAndGetTypeConnection(this);

        switch (connectType) {
            case ConnectivityManager.TYPE_WIFI:
                connectedState = getResources().getString(R.string.connected_by_wifi);
                bgColor = getResources().getColor(R.color.blue_800);
                break;

            case ConnectivityManager.TYPE_ETHERNET:
                connectedState = getResources().getString(R.string.connected_by_ethernet);
                bgColor = getResources().getColor(R.color.blue_800);
                break;

            case ConnectivityManager.TYPE_MOBILE:
                connectedState = getResources().getString(R.string.connected_by_mobile_data);
                bgColor = getResources().getColor(R.color.orange_800);
                break;

            case ConnectivityManager.TYPE_WIMAX:
                connectedState = getResources().getString(R.string.connected_by_wimax);
                bgColor = getResources().getColor(R.color.purple_800);
                break;

            case -1:
                connectedState = getResources().getString(R.string.unconnected_state);
                bgColor = getResources().getColor(R.color.gray_700);
                break;

            default:
                connectedState = getResources().getString(R.string.connected_state);
                bgColor = getResources().getColor(R.color.light_blue_800);
                break;
        }

        mConnectionStateView.setText(connectedState);
        mConnectionStateView.setBackgroundColor(bgColor);

        if (connectType != -1) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mConnectionStateView.setVisibility(View.GONE);
                }
            }, 3000);
        }
    }

    private void init() {
        //Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        //init presenter
        mPresenter = new SigninPresenterImpl(this);

        //inflate views
        mConnectionStateView = (TextView) findViewById(R.id.connectionState_signin);
        mEmailField = (EditText) findViewById(R.id.emailField);
        mPasswordField = (EditText) findViewById(R.id.passwordField);
        mShowPassCheckbox = (CheckBox) findViewById(R.id.showPassword_chk_signinAct);
        mLoginButton = (Button) findViewById(R.id.loginButton);
        mSignupLink = (TextView) findViewById(R.id.signupLink);
        mForgotPasswordLink = (TextView) findViewById(R.id.forgotPassword);

        //set listeners
        mShowPassCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPasswordShow(mShowPassCheckbox.isChecked());
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        mSignupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignupScreen();
            }
        });

        mForgotPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgotPasswordScreen();
            }
        });
    }

    private void setPasswordShow(boolean checked) {
        if (checked) {
            mPasswordField.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            mPasswordField.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private void openForgotPasswordScreen() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void signIn() {
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        if (mPresenter != null) {
            mPresenter.validateAndSignin(email, password);
        }
    }

    private void openSignupScreen() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    ProgressDialog mLoginingDialog;

    @Override
    public void showProgress() {
        if(mLoginButton != null)
            mLoginButton.setEnabled(false);

        if (mLoginingDialog == null) {
            mLoginingDialog = new ProgressDialog(this);
            mLoginingDialog.setIndeterminate(true);
            mLoginingDialog.setMessage("Login....");
            mLoginingDialog.setCancelable(false);
        }

        mLoginingDialog.show();
    }

    @Override
    public void hideProgress() {
        if (mLoginingDialog != null) {
            mLoginingDialog.hide();
        }

        if (mLoginButton != null) {
            mLoginButton.setEnabled(true);
        }
    }

    @Override
    public void onEmailError(String error) {
        if (mLoginingDialog != null) {
            mLoginingDialog.dismiss();
        }

        if (mEmailField != null) {
            mEmailField.setError(error);
        }
    }

    @Override
    public void onPasswordError(String error) {
        if (mLoginingDialog != null) {
            mLoginingDialog.dismiss();
        }

        if (mPasswordField != null) {
            mPasswordField.setError(error);
        }
    }

    @Override
    public void onSuccess() {
        hideProgress();

        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailed() {
        hideProgress();

        if (mEmailField != null) {
            mEmailField.setText("");
        }

        if (mPasswordField != null) {
            mPasswordField.setText("");
        }

        Toast.makeText(this, "Login failed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
