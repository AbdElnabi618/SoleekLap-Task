package com.kh618.soleektask.Uitil;

import com.kh618.soleektask.Module.CountryItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET(".")
    Call<ArrayList<CountryItem>> getCountries();
}
