package com.kh618.soleektask.Splash;

import android.widget.Toast;

public class SplashPresenter implements SplashModel.OnActivityStart {

    private SplashActivity splashActivity;
    private SplashModel splashModel;

    public SplashPresenter(SplashActivity splashActivity, SplashModel splashModel) {
        this.splashActivity = splashActivity;
        this.splashModel = splashModel;
    }

    public void ActivityStart(){
        splashModel.OnAppStart(this);
    }


    @Override
    public void startLoginActivity() {
        splashActivity.openLoginScreen();
    }

    @Override
    public void startHomeActivity() {
        splashActivity.openHomeScreen();
    }

    @Override
    public void startAnimation() {
        splashActivity.startAnimation();
    }
}
