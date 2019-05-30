package com.kh618.soleektask.Splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.kh618.soleektask.Home.HomeActivity;
import com.kh618.soleektask.Login.LoginActivity;
import com.kh618.soleektask.R;

public class SplashActivity extends Activity implements SplashView {

    private SplashPresenter presenter;
    private Intent intent;

    private ImageView logo;
    private TextView welcom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        intent = new Intent();

        logo = findViewById(R.id.img_logo);
        welcom = findViewById(R.id.tv_welcome);
        presenter = new SplashPresenter(this, new SplashModel(this));
        presenter.ActivityStart();

    }

    @Override
    public void startAnimation() {
        Animation animationImg = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_logo);
        Animation animationText = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_text);
        logo.startAnimation(animationImg);
        welcom.startAnimation(animationText);
    }

    @Override
    public void openLoginScreen() {
        intent.setClass(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void openHomeScreen() {
        intent.setClass(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
