package com.example.user.androidtesttask;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by User on 27.11.2014.
 */
public class Country {

    @SerializedName("name")  private String mName;

    public Country(String mName, String mCode, ArrayList<State> mStates) {
        this.mName = mName;
        this.mCode = mCode;
        this.mStates = mStates;
    }

    @SerializedName("code")  private String mCode;
    @SerializedName("states") private ArrayList<State> mStates;

    public Country() {
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public ArrayList<State> getmStates() {
        return mStates;
    }

    public void setmStates(ArrayList<State> mStates) {
        this.mStates = mStates;
    }




    @Override
    public String toString() {
        return getmName();
    }


}
