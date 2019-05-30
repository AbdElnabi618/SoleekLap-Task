package com.kh618.soleektask.Registration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kh618.soleektask.R;

import java.util.Objects;

import static android.support.constraint.Constraints.TAG;

public class RegistrationModel {

    /**
     * interface that used in update the UI with presenter
     */
    public interface OnRegistration{
        void OnRegistrationFaild(String msg);
        void OnRegistrationSucced();
        void ShowProgress();
        void HideProgress();

        void GoogleAuthOpenIntent(GoogleSignInClient client);

    }

    private Context context;

    public RegistrationModel(Context context) {
        this.context = context;
    }

    /**
     * @EmailAndPassword Authenticate
     */
    /**
     *
     * @param email , the email witch user will use to login
     * @param password ,user password
     * @param firebaseAuth , Firebase Authentiacte to sinup with email and password
     * @param onRegistration, presenter that will update the ui
     */

    public void AuthenticateWithEmail(String email, String password, String confirmPassword
            , FirebaseAuth firebaseAuth, final OnRegistration onRegistration) {
        onRegistration.ShowProgress();

        if (FieldsEmpty(email, password)){
            onRegistration.HideProgress();
            onRegistration.OnRegistrationFaild("user name and password can\'t be empty");
            return;
        }

        if (passwordChecker(password, confirmPassword)){
            onRegistration.HideProgress();
            onRegistration.OnRegistrationFaild("two passwords aren\'t the same");
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password).
                addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                saveState();
                onRegistration.HideProgress();
                onRegistration.OnRegistrationSucced();
            }
        }).
                addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                onRegistration.HideProgress();
                onRegistration.OnRegistrationFaild(e.getMessage());
            }
        });
    }



    /**
     * @Facebook Authenticate
     */
    private CallbackManager manager;

    /**
     * Login and sign in firebase with facebook account
     * @param loginButton , facebook login button
     * @param manager, Callback Manager to back data from activity result
     * @param auth, firebase authenticate
     * @param registration, interface to update UI
     */
    public void AuthenticateWithFacebook(LoginButton loginButton, CallbackManager manager
            , final FirebaseAuth auth, final OnRegistration registration){
        this.manager = manager;
        loginButton.setPermissions("email", "public_profile");
        loginButton.registerCallback(manager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                LoginWithFacebook(loginResult.getAccessToken(),auth, registration);
            }

            @Override
            public void onCancel() {
                registration.OnRegistrationFaild("Authenticate canceled");
            }

            @Override
            public void onError(FacebookException error) {
                registration.OnRegistrationFaild(error.getMessage());
            }
        });
    }

    /**
     * get data from onActivityResult
     * @param requestCode ,
     * @param resultCode ,
     * @param data ,
     */
    public void onBackManger(int requestCode, int resultCode , Intent data){
        this.manager.onActivityResult(requestCode, resultCode,data);
    }

    /**
     * use facebook access token to get id token to login with
     * @param accessToken , facebook AccessToken
     * @param firebaseAuth ,
     * @param registration ,
     */
    private void LoginWithFacebook(AccessToken accessToken, FirebaseAuth firebaseAuth
            ,final OnRegistration registration) {
            registration.ShowProgress();
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());

        firebaseAuth.signInWithCredential(credential).
        addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                saveState();
                registration.HideProgress();
                registration.OnRegistrationSucced();
            }
        }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        registration.HideProgress();
                        registration.OnRegistrationFaild(e.getMessage());
                    }
                });
    }



    /**
     * @Google Authenticate
     */

    /**
     * this method used to build sign in option and sign in client
     * sin in client send to view by the presenter to open google
     * authenticate window....
     *
     * @param registration, the presenter witch used to update user interface
     */
    public void AuthenticateWithGMail(OnRegistration registration){
       GoogleSignInOptions signInOptions = BuildGoogleSignInOptions();
       GoogleSignInClient client = GoogleSignIn.getClient(context, signInOptions);
       registration.GoogleAuthOpenIntent(client);
    }

    /**
     *
     * @param data , content of authenticated email 's data
     * @param auth , Firebase Authenticate witch used to sign in  Credential on Id token
     * @param registration , the presenter witch used to update user interface
     */
    public void SignInWithAuthData(Intent data, FirebaseAuth auth, final OnRegistration registration){
        registration.ShowProgress();
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {

            GoogleSignInAccount account = task.getResult(ApiException.class);

            AuthCredential credential = GoogleAuthProvider.getCredential(Objects.requireNonNull(account).getIdToken(),null);

            auth.signInWithCredential(credential).
                    addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            saveState();
                            registration.HideProgress();
                            registration.OnRegistrationSucced();
                        }
                    }).
                    addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            registration.HideProgress();
                            registration.OnRegistrationFaild(e.getMessage());
                        }
                    });
        } catch (Exception e) {

            Log.w(TAG, "Google sign in failed "+ e.getMessage());
            registration.OnRegistrationFaild("Some error has happened please try again later");
        }
    }

    /**
     * build GoogleSignInOptions Object
     * @return GoogleSignInOptions Object
     */
    private GoogleSignInOptions BuildGoogleSignInOptions(){
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }



    /**
     * save the user 's login states
     */
    private void saveState(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.LoginState), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.isLogin),true);
        editor.apply();
    }

    /**
     * method used to compare two password
     * @param password, first password entered
     * @param confirmPassword, second password entered
     * @return true if the two passwords are equals
     */
    private boolean passwordChecker(String password, String confirmPassword){
        return !password.equals(confirmPassword);
    }

    /**
     * check if this words are empty
     * @param email, user email
     * @param password, user password
     * @return true if any one of the two words is empty
     */
    private boolean FieldsEmpty(String email, String password){
        return (email.isEmpty() || password.isEmpty());
    }
}