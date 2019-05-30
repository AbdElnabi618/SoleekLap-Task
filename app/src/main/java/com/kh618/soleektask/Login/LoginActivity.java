package com.kh618.soleektask.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.kh618.soleektask.Home.HomeActivity;
import com.kh618.soleektask.R;
import com.kh618.soleektask.Registration.RegistrationActivity;

public class LoginActivity extends Activity implements LoginView {

    private EditText etEmail, etPassword;
    private LoginPresenter presenter;
    private ProgressDialog loadingDialog;
    private AlertDialog errorDialog;
    private Intent intent;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_acvtivity);
        etEmail = findViewById(R.id.et_registration_userName);
        etPassword = findViewById(R.id.et_registration_password);

        firebaseAuth = FirebaseAuth.getInstance();

        presenter = new LoginPresenter(this, new LoginModel(this));

        intent = new Intent();

        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("Please wait ...");
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    public void LoginFailed(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Login Error")
                .setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etEmail.setText("");
                etPassword.setText("");
                dialog.dismiss();
            }
        });
        errorDialog = builder.create();
        errorDialog.show();
    }

    @Override
    public void StartHomeScreen() {
        intent.setClass(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void StartRegistrationScreen() {
        intent.setClass(this, RegistrationActivity.class);
        startActivity(intent);
    }

    @Override
    public void ShowLoginProgress() {
        loadingDialog.show();
    }

    @Override
    public void HideLoginProgress() {
        loadingDialog.dismiss();
    }

    public void LoginClick(View view) {
        presenter.tryLogin(etEmail.getText().toString(), etPassword.getText().toString(), firebaseAuth);
    }

    public void CreateAccount(View view) {
        presenter.openRegistrationActivity();
    }


}
