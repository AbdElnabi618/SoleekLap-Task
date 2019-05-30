package com.kh618.soleektask.Home;

import com.kh618.soleektask.Module.CountryItem;

import java.util.ArrayList;

public interface HomeView {

    void loadList(ArrayList<CountryItem> items);
    void showProgress();
    void hideProgress();
    void onFialed(String msg);

}
