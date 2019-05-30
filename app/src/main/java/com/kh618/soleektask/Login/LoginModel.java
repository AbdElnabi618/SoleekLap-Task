package com.kh618.soleektask.Login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kh618.soleektask.R;

public class LoginModel {

    /**
     * interface contain the method witch override in presenter
     */
    public interface OnAction{
        void OnLoginFaild(String msg);
        void OnLoginSucceed();
        void OpenRegistration();
        void ShowProgress();
        void HideProgress();
    }
    private Context context;

    public LoginModel(Context context) {
        this.context = context;
    }

    /**
     * Open Registration Activity
     * @param onAction , is a listener to change in UI
     */
    public void OpenRegistrationActivity(OnAction onAction){
        onAction.OpenRegistration();
    }

    /**
     * Login with email and password
     * @param email, the email witch user sign up with
     * @param password, the passwoed witch user sign up with
     * @param firebaseAuth, Firebase object used to login
     * @param onAction, is a listener to change in UI
     */
    public void Login(final String email, final String password, FirebaseAuth firebaseAuth, final OnAction onAction){
        onAction.ShowProgress();

        if(FieldsEmpty(email, password)){
            onAction.HideProgress();
            onAction.OnLoginFaild("Email and password can\'t be empty");
            return;
        }

        /**
         * send email and password to firebase  service to login
         */
        firebaseAuth.signInWithEmailAndPassword(email, password).
                addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        saveState();
                        onAction.HideProgress();
                        onAction.OnLoginSucceed();
                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        onAction.HideProgress();
                        onAction.OnLoginFaild(e.getMessage());
                    }
                });

    }


    /**
     * check if the email or password = null
     * @param email
     * @param password
     * @return true if on of the both is null or empty
     */
    private boolean FieldsEmpty(String email, String password){
        return (email.isEmpty() || password.isEmpty());
    }

    /**
     * save login status as a shared preference
     */
    private void saveState(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.LoginState), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.isLogin),true);
        editor.apply();
    }
}
