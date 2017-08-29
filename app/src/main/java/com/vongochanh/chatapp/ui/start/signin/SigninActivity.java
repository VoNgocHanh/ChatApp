package com.vongochanh.chatapp.ui.start.signin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.vongochanh.chatapp.R;
import com.vongochanh.chatapp.ui.home.main.HomeActivity;
import com.vongochanh.chatapp.ui.start.signup.SignupActivity;
import com.vongochanh.chatapp.ui.start.signin.forgot_password.ForgotPasswordActivity;

public class SigninActivity extends AppCompatActivity implements SigninContract.View {
    private String TAG = getClass().getSimpleName();

    private FirebaseAuth mAuth;

    private SigninContract.Presenter mPresenter;

    //Views
    private TextView mConnectionStateView;
    private EditText mEmailField, mPasswordField;
    private CheckBox mShowPassCheckbox;
    private Button mLoginButton;
    private LinearLayout mProgressBar;
    private TextView mSignupLink, mForgotPasswordLink;

    /*LIFECYCLE*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        checkConnection();
    }

    /**
     * After Presenter implement check connection finish, it will call {@link #showConnectionState(boolean, String, int)}
     * to show the current connection state on top the login screen
     */
    private void checkConnection() {
        if (mPresenter != null) {
            mPresenter.checkConnection();
        }
    }

    private void init() {
        //Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        //init presenter
        mPresenter = new SigninPresenter(this);

        //inflate views
        mConnectionStateView = (TextView) findViewById(R.id.connectionState_signin);
        mEmailField = (EditText) findViewById(R.id.emailField);
        mPasswordField = (EditText) findViewById(R.id.passwordField);
        mShowPassCheckbox = (CheckBox) findViewById(R.id.showPassword_chk_signinAct);
        mLoginButton = (Button) findViewById(R.id.loginButton);
        mProgressBar = (LinearLayout) findViewById(R.id.progress_bar_activity_signin);
        mSignupLink = (TextView) findViewById(R.id.signupLink);
        mForgotPasswordLink = (TextView) findViewById(R.id.forgotPassword);

        //set listeners
        mEmailField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return enterSignin(keyCode, event);
            }
        });

        mPasswordField.setOnKeyListener(new android.view.View.OnKeyListener() {
            @Override
            public boolean onKey(android.view.View v, int keyCode, KeyEvent event) {
                return enterSignin(keyCode, event);
            }
        });

        mShowPassCheckbox.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                setPasswordShow(mShowPassCheckbox.isChecked());
            }
        });

        mLoginButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                signIn();
            }
        });

        mSignupLink.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                openSignupScreen();
            }
        });

        mForgotPasswordLink.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                openForgotPasswordScreen();
            }
        });
    }

    private boolean enterSignin(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            signIn();
            return true;
        }
        return false;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }
    /*End LIFECYCLER*/

    /*SigninContract.View interface IMPLEMENT*/
    @Override
    public Context getContext() {
        return getApplicationContext();
    }
    boolean mIsConnected;
    @Override
    public void showConnectionState(final boolean isConnected, final String connectionState, int bgStateViewColor) {
        mConnectionStateView.setVisibility(View.VISIBLE);
        mConnectionStateView.setText(connectionState);
        mConnectionStateView.setBackgroundColor(bgStateViewColor);

        mIsConnected = isConnected;
        if (isConnected) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(mIsConnected)
                        mConnectionStateView.setVisibility(android.view.View.GONE);
                }
            }, 3000 /*After 3s mConnectionStateView will be hide*/);
        }
    }

    ProgressDialog mLoginingDialog;

    @Override
    public void showProgress(String message) {
        if(mLoginButton != null)
            mLoginButton.setEnabled(false);

        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
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
}
