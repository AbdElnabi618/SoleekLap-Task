package com.kh618.soleektask.Registration;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.kh618.soleektask.Countrys.CountrysActivity;
import com.kh618.soleektask.Home.HomeActivity;
import com.kh618.soleektask.R;

public class RegistrationActivity extends Activity implements RegistrationView {

    private EditText etEmail, etPassword, etConfirmPassword;
    private RegistrationPresenter presenter;
    private ProgressDialog loadingDialog;
    private Intent intent;
    private FirebaseAuth firebaseAuth;
    private LoginButton facebookLoginButton;
    private final static int GOOGLE_AUTH_CODE = 2;

    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        etEmail = findViewById(R.id.et_registration_userName);
        etPassword = findViewById(R.id.et_registration_password);
        etConfirmPassword = findViewById(R.id.et_registration_confirm_password);

        /*
         * Handle Google sign in "on click" listener
         */
        SignInButton signInButton = findViewById(R.id.google_auth);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.GoogleAuthenticate();
            }
        });

        facebookLoginButton = findViewById(R.id.facebook_auth);
        callbackManager = CallbackManager.Factory.create();


        presenter = new RegistrationPresenter(this, new RegistrationModel(getApplicationContext()));

        intent = new Intent();

        firebaseAuth = FirebaseAuth.getInstance();

        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("Please wait ...");
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    /**
     * start google auth intent
     * @param client , used to get Intent
     */
    @Override
    public void openGoogleSignInWindow(GoogleSignInClient client) {
        intent = client.getSignInIntent();
        startActivityForResult(intent, GOOGLE_AUTH_CODE);
    }

    /**
     * auth with facebook
     * @param view ,
     */
    public void AuthenticateWithFacebook(View view) {
        presenter.FacebookAuthenticate(facebookLoginButton, callbackManager , firebaseAuth);
    }

    /**
     * auth with email and password
     * @param view ,
     */
    public void RegistrationClick(View view) {
        presenter.EmailAuthenticate(etEmail.getText().toString(), etPassword.getText().toString()
        , etConfirmPassword.getText().toString(), firebaseAuth);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || resultCode == 0)
            return;
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == GOOGLE_AUTH_CODE){
            presenter.IntentAuthData(data,firebaseAuth);
        }else {
            presenter.callBackMagerData(resultCode, requestCode, data);
        }
    }

    @Override
    public void OnRegistrationFailed(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Registration Error")
                .setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etEmail.setText("");
                etConfirmPassword.setText("");
                etPassword.setText("");
                dialog.dismiss();
            }
        });
        AlertDialog errorDialog = builder.create();
        errorDialog.show();
    }

    @Override
    public void OnRegistrationSucceed() {
        intent.setClass(this , CountrysActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void ShowProgress() {
        loadingDialog.show();
    }

    @Override
    public void HideProgress() {
        loadingDialog.hide();
    }


    public void BackToLogin(View view) {
        finish();
    }
}
