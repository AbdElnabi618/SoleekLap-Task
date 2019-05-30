package com.kh618.soleektask.Registration;

import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationPresenter implements RegistrationModel.OnRegistration{

    private RegistrationActivity activity;
    private RegistrationModel model;

    /**
     * constructor
     * @param activity , Registration Activity 'view' object
     * @param model , model object
     */
    public RegistrationPresenter(RegistrationActivity activity, RegistrationModel model) {
        this.activity = activity;
        this.model = model;
    }

    // Facebook
    /**
     * send authenticate parameter to model
     * @param loginButton ,facebook login button
     * @param callbackManager, Callback Manager to back data from activity result
     * @param auth, interface to update UI
     */
    public void FacebookAuthenticate(LoginButton loginButton, CallbackManager callbackManager, FirebaseAuth auth){
        model.AuthenticateWithFacebook(loginButton, callbackManager, auth, this);
    }

    /**
     * get data from onActivityResult and sent to model
     * @param resultCode ,
     * @param requestCode ,
     * @param data ,
     */
    public void callBackMagerData(int resultCode, int requestCode, Intent data){
        model.onBackManger(requestCode, resultCode, data);
    }

    //google

    /**
     *  gat data from google login window
     * @param data, user data
     * @param auth, authenticate object
     */
    public void IntentAuthData(Intent data, FirebaseAuth auth){
        model.SignInWithAuthData(data,auth, this);
    }

    /**
     * start auth with google
     */
    public void GoogleAuthenticate(){
        model.AuthenticateWithGMail(this);
    }

    /**
     * use google sign in client to start auth window
     * @param client, client to start activity
     */
    @Override
    public void GoogleAuthOpenIntent(GoogleSignInClient client) {
        activity.openGoogleSignInWindow(client);
    }


    // email and password

    /**
     * create an account with email and password
     * @param email ,
     * @param password ,
     * @param confirmPassword ,
     * @param firebaseAuth ,
     */
    public void EmailAuthenticate(String email, String password, String confirmPassword, FirebaseAuth firebaseAuth){
        model.AuthenticateWithEmail(email, password, confirmPassword,firebaseAuth, this);
    }




    @Override
    public void OnRegistrationFaild(String msg) {
        activity.OnRegistrationFailed(msg);
    }

    @Override
    public void OnRegistrationSucced() {
        activity.OnRegistrationSucceed();
    }

    @Override
    public void ShowProgress() {
        activity.ShowProgress();
    }

    @Override
    public void HideProgress() {
        activity.HideProgress();
    }


}
