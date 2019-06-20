package com.kh618.soleektask.Countrys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.kh618.soleektask.Home.HomeModel;
import com.kh618.soleektask.Home.HomePresenter;
import com.kh618.soleektask.Home.HomeView;
import com.kh618.soleektask.Login.LoginActivity;
import com.kh618.soleektask.Module.CharAdapter;
import com.kh618.soleektask.Module.CountryAdabter;
import com.kh618.soleektask.Module.CountryItem;
import com.kh618.soleektask.R;

import java.util.ArrayList;

public class CountrysActivity extends Activity implements CountryView {

    private RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activty);

        recyclerView = findViewById(R.id.country_recycle);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),4));
        recyclerView.setHasFixedSize(false);

        firebaseAuth = FirebaseAuth.getInstance();

        CountryPresenter presenter = new CountryPresenter(this, new CountryModel());
        presenter.startLoading();
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
    @Override
    public void loadList(char[] items) {
        CharAdapter adapter = new CharAdapter(this, items);
        recyclerView.setAdapter(adapter);
    }
}
