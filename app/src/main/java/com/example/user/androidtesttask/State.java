package com.example.user.androidtesttask;

/**
 * Created by User on 28.11.2014.
 */
public class State {
    public State (){}
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

    private String mCode;
    private String mName;
}