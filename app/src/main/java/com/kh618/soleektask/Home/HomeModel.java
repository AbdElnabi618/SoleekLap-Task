package com.kh618.soleektask.Home;

import com.kh618.soleektask.Module.CountryItem;
import com.kh618.soleektask.Uitil.ApiClient;
import com.kh618.soleektask.Uitil.ApiInterface;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeModel {

    interface LoadItems{

        void onSucceed(ArrayList<CountryItem> items, char c);
        void onFailed(String msg);
        void showProgress();
        void hideProgress();

    }


    public void getDate(final char c, final LoadItems loadItems){
        loadItems.showProgress();

        Retrofit retrofit = ApiClient.getClient();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ArrayList<CountryItem>> arrayListCall = apiInterface.getCountries();
        arrayListCall.enqueue(new Callback<ArrayList<CountryItem>>() {
            @Override
            public void onResponse(Call<ArrayList<CountryItem>> call, Response<ArrayList<CountryItem>> response) {
                if(response.body() != null)
                    loadItems.onSucceed(response.body(), c);
                loadItems.hideProgress();
            }

            @Override
            public void onFailure(Call<ArrayList<CountryItem>> call, Throwable t) {
                loadItems.hideProgress();
                loadItems.onFailed(t.getMessage());
            }
        });

    }




}
