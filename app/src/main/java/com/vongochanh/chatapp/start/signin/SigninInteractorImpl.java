package com.vongochanh.chatapp.start.signin;


import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SigninInteractorImpl implements SigninContract.SigninInteractor {
    private String TAG = getClass().getSimpleName();

    private FirebaseAuth mAuth;

    private OnSigninFinishedListener mLoginFinishedListener;

    public SigninInteractorImpl(OnSigninFinishedListener loginFinishedListener) {
        mLoginFinishedListener = loginFinishedListener;
    }

    @Override
    public void login(String email, String password) {
        if(!validateValues(email, password))
            return;

        signIn(email, password);
    }

    private boolean validateValues(String email, String password) {
        boolean isValid = true;
        if (TextUtils.isEmpty(email)) {
            isValid = false;
            if (mLoginFinishedListener != null) {
                mLoginFinishedListener.onEmailError("Email is empty");
            }
        }

        if (TextUtils.isEmpty(password)) {
            isValid = false;
            if (mLoginFinishedListener != null) {
                mLoginFinishedListener.onPasswordError("Error is empty");
            }
        }

        return isValid;
    }

    private void signIn(String email, String password) {
        //Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        Log.d(TAG, "Start login...");

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(onCompleteSigninListener);
    }

    private OnCompleteListener<AuthResult> onCompleteSigninListener = new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success.");

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (mLoginFinishedListener != null) {
                    mLoginFinishedListener.onSuccess();
                }
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.getException());
                if (mLoginFinishedListener != null) {
                    mLoginFinishedListener.onFailed();
                }
            }
        }
    };
}
