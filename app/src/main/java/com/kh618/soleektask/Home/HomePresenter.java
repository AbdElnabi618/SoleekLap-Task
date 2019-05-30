package com.kh618.soleektask.Home;

import com.kh618.soleektask.Module.CountryItem;

import java.util.ArrayList;

public class HomePresenter implements HomeModel.LoadItems {

    private HomeActivity activity;
    private HomeModel model;

    public HomePresenter(HomeActivity activity, HomeModel model) {
        this.activity = activity;
        this.model = model;
    }

    public void startLoading(){
        model.getDate(this);
    }

    @Override
    public void onSucceed(ArrayList<CountryItem> items) {
        activity.loadList(items);
    }

    @Override
    public void onFailed(String msg) {
        activity.onFialed(msg);
    }

    @Override
    public void showProgress() {
        activity.showProgress();
    }

    @Override
    public void hideProgress() {
        activity.hideProgress();
    }
}
