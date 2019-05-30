package com.kh618.soleektask.Login;

import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter implements LoginModel.OnAction{

    private LoginActivity loginActivity;
    private LoginModel loginModel;

    public LoginPresenter(LoginActivity loginActivity, LoginModel loginModel) {
        this.loginActivity = loginActivity;
        this.loginModel = loginModel;
    }

    public void tryLogin(String email, String password, FirebaseAuth firebaseAuth){
        loginModel.Login(email, password, firebaseAuth,this);
    }

    public void openRegistrationActivity(){
        loginModel.OpenRegistrationActivity(this);
    }

    @Override
    public void OnLoginFaild(String msg) {
        loginActivity.LoginFailed(msg);
    }

    @Override
    public void OnLoginSucceed() {
        loginActivity.StartHomeScreen();
    }

    @Override
    public void OpenRegistration() {
        loginActivity.StartRegistrationScreen();
    }

    @Override
    public void ShowProgress() {
        loginActivity.ShowLoginProgress();
    }

    @Override
    public void HideProgress() {
        loginActivity.HideLoginProgress();
    }
}
