package com.vongochanh.chatapp.start.signin;


public class SigninPresenterImpl implements SigninContract.SigninPresenter, SigninContract.SigninInteractor.OnSigninFinishedListener {
    private SigninContract.SigninView mSigninView;
    private SigninContract.SigninInteractor mSigninInteractor;

    public SigninPresenterImpl(SigninContract.SigninView signinView) {
        mSigninView = signinView;
        mSigninInteractor = new SigninInteractorImpl(this) ;
    }

    /*SigninContract.oginPresenter interface*/
    @Override
    public void validateAndSignin(String email, String password) {
        if (mSigninView != null) {
            mSigninView.showProgress();
        }

        mSigninInteractor.login(email, password);
    }

    @Override
    public void onDestroy() {
        if (mSigninView != null) {
            mSigninView = null;
        }
    }
    /*End*/


    /* interface*/
    @Override
    public void onEmailError(String error) {
        if (mSigninView != null) {
            mSigninView.onEmailError(error);
        }
    }

    @Override
    public void onPasswordError(String error) {
        if (mSigninView != null) {
            mSigninView.onPasswordError(error);
        }
    }

    @Override
    public void onSuccess() {
        if (mSigninView != null) {
            mSigninView.onSuccess();
        }
    }

    @Override
    public void onFailed() {
        if (mSigninView != null) {
            mSigninView.onFailed();
        }
    }
    /*End*/
}
