package com.kh618.soleektask.Module;

import com.google.gson.annotations.SerializedName;

public class CountryItem {
    @SerializedName("nativeName")
    private String counterName;
    @SerializedName("flag")
    private String countryUrlFlag;
    @SerializedName("capital")
    private String counterCapital;

    public CountryItem(){
        counterCapital= null;
        counterName = null;
        countryUrlFlag = null;
    }

    public String getCounterName() {
        return counterName;
    }

    public String getCountryUrlFlag() {
        return countryUrlFlag;
    }

    public String getCounterCapital() {
        return counterCapital;
    }

    public CountryItem(String counterName, String countryUrlFlag, String counterCapital) {
        this.counterName = counterName;
        this.countryUrlFlag = countryUrlFlag;
        this.counterCapital = counterCapital;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public void setCountryUrlFlag(String countryUrlFlag) {
        this.countryUrlFlag = countryUrlFlag;
    }

    public void setCounterCapital(String counterCapital) {
        this.counterCapital = counterCapital;
    }
}
