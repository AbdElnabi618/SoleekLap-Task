package com.kh618.soleektask.Countrys;

import com.kh618.soleektask.Module.CountryItem;
import com.kh618.soleektask.Uitil.ApiClient;
import com.kh618.soleektask.Uitil.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CountryModel {

    interface LoadItems{
        void onSucceed(char[] items);
    }


    public void getDate(final LoadItems loadItems){
        char[] items = new char[26];
        for (int i = 65, j =0; i <= 90; i++, j++) {
            items[j] = (char) i;
        }
        loadItems.onSucceed(items);
    }




}
