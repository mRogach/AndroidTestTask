package com.example.user.androidtesttask;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by User on 28.11.2014.
 */
public class CountryDetail implements Serializable {
    @SerializedName("capital")  private String mCapital;
    @SerializedName("region")  private String mRegion;
    @SerializedName("area") private double mArea;
    @SerializedName("callingCodes") private int mCallingCode;
    @SerializedName("latlng") private ArrayList<Double> geoPoints;
    private String flagCode;


    public String getFlagCode() {
        return flagCode;
    }

    public void setFlagCode(String flagCode) {
        this.flagCode = flagCode;
    }

    public CountryDetail(){}

    public String getmCapital() {
        return mCapital;
    }

    public void setmCapital(String mCapital) {
        this.mCapital = mCapital;
    }

    public String getmRegion() {
        return mRegion;
    }

    public void setmRegion(String mRegion) {
        this.mRegion = mRegion;
    }

    public double getmArea() {
        return mArea;
    }

    public void setmArea(double mArea) {
        this.mArea = mArea;
    }

    public int getmCallingCode() {
        return mCallingCode;
    }

    public void setmCallingCode(int mCallingCode) {
        this.mCallingCode = mCallingCode;
    }

    public ArrayList<Double> getGeoPoints() {
        return geoPoints;
    }

    public void setGeoPoints(ArrayList<Double> geoPoints) {
        this.geoPoints = geoPoints;
    }
}
