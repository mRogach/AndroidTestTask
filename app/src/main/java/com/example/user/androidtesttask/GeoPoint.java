package com.example.user.androidtesttask;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 28.11.2014.
 */
public class GeoPoint {
    @SerializedName("code")  private float geoPtLatitude;
    @SerializedName("code")  private float geoPtLongitude;

    public GeoPoint(){}

    public float getGeoPtLatitude() {
        return geoPtLatitude;
    }

    public void setGeoPtLatitude(float geoPtLatitude) {
        this.geoPtLatitude = geoPtLatitude;
    }

    public float getGeoPtLongitude() {
        return geoPtLongitude;
    }

    public void setGeoPtLongitude(float geoPtLongitude) {
        this.geoPtLongitude = geoPtLongitude;
    }
}
