package com.example.user.androidtesttask;

import java.util.ArrayList;

/**
 * Created by User on 27.11.2014.
 */
public class Country {
    private String code;
    private String name;
    private ArrayList<State> mStates;

    @Override
    public String toString() {
        return getName();
    }

    public Country (){}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<State> getmStates() {
        return mStates;
    }

    public void setmStates(ArrayList<State> mStates) {
        this.mStates = mStates;
    }
}
