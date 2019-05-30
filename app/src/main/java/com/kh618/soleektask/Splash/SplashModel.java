package com.kh618.soleektask.Splash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.kh618.soleektask.R;

public class SplashModel {

    public interface OnActivityStart {
        void startLoginActivity();
        void startHomeActivity();
        void startAnimation();
    }
    private Context context;

    public SplashModel(Context context) {
        this.context = context;
    }

    public void OnAppStart(final OnActivityStart onActivityStart){
        onActivityStart.startAnimation();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(getState()) {
                    onActivityStart.startHomeActivity();
                }else {
                    onActivityStart.startLoginActivity();
                }
            }
        },5000);
    }

    private boolean getState(){
        SharedPreferences preferences = context.getSharedPreferences(
                context.getString(R.string.LoginState), Context.MODE_PRIVATE);
        return preferences.getBoolean(context.getString(R.string.isLogin), false);
    }
}
