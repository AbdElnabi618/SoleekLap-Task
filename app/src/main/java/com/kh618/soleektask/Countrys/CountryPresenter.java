package com.kh618.soleektask.Countrys;

import com.kh618.soleektask.Home.HomeActivity;
import com.kh618.soleektask.Module.CountryItem;

import java.util.ArrayList;

public class CountryPresenter implements CountryModel.LoadItems {

    private CountryView activity;
    private CountryModel model;

    public CountryPresenter(CountryView activity, CountryModel model) {
        this.activity = activity;
        this.model = model;
    }

    public void startLoading(){
        model.getDate(this);
    }

    @Override
    public void onSucceed(char[] items) {
        activity.loadList(items);
    }
}
