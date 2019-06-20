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

    public void startLoading(char c){
        model.getDate(c, this);
    }

    @Override
    public void onSucceed(ArrayList<CountryItem> items, char c) {
        ArrayList<CountryItem> chosenItems = new ArrayList<>();

        for (CountryItem item: items) {
            if(item.getCounterName().contains(String.valueOf(c))){
                chosenItems.add(item);
            }
        }
        activity.loadList(chosenItems);
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
