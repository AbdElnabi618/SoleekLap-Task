package com.kh618.soleektask.Home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.kh618.soleektask.Login.LoginActivity;
import com.kh618.soleektask.Module.CountryAdabter;
import com.kh618.soleektask.Module.CountryItem;
import com.kh618.soleektask.R;

import java.util.ArrayList;

public class HomeActivity extends Activity implements HomeView{
    FirebaseAuth firebaseAuth;

    private ArrayList<CountryItem> items;
    private RecyclerView recyclerView;
    private CountryAdabter countryAdabter;
    private ProgressDialog loadingDialog;
    private AlertDialog errorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activty);

        recyclerView = findViewById(R.id.country_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(false);

        firebaseAuth = FirebaseAuth.getInstance();

        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("Please wait ...");
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        HomePresenter presenter = new HomePresenter(this, new HomeModel());
        presenter.startLoading();
    }

    /**
     * logout from firebase and facebook
     * @param view
     */
    public void LogOut(View view){
        firebaseAuth.signOut();
        LoginManager.getInstance().logOut();
        saveState();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    /**
     * save log out status
     */
    private void saveState(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                this.getString(R.string.LoginState), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(this.getString(R.string.isLogin),false);
        editor.apply();
    }

    @Override
    public void loadList(ArrayList<CountryItem> items) {
        this.items = items;
        countryAdabter = new CountryAdabter(this,this.items);
        recyclerView.setAdapter(countryAdabter);
    }

    @Override
    public void showProgress() {
        loadingDialog.show();
    }

    @Override
    public void hideProgress() {
        loadingDialog.hide();
    }

    @Override
    public void onFialed(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Login Error")
                .setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        errorDialog = builder.create();
        errorDialog.show();
    }
}
