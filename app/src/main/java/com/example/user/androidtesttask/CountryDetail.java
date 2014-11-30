package com.example.user.androidtesttask;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by User on 28.11.2014.
 */
public class CountryDetail {
    @SerializedName("West")  private float geoRectangleWest;
    @SerializedName("East")  private float getRectangleEast;
    @SerializedName("North") private float getRectangleNorth;
    @SerializedName("South") private float getRectangleSouth;
    @SerializedName("GeoPt") private ArrayList<Float> geoPoints;

    public CountryDetail(){}

    public float getGeoRectangleWest() {
        return geoRectangleWest;
    }

    public void setGeoRectangleWest(float geoRectangleWest) {
        this.geoRectangleWest = geoRectangleWest;
    }

    public float getGeoRectangleEast() {
        return getRectangleEast;
    }

    public void setGeoRectangleEast(float getRectangleEast) {
        this.getRectangleEast = getRectangleEast;
    }

    public float getGeoRectangleNorth() {
        return getRectangleNorth;
    }

    public void setGeoRectangleNorth(float getRectangleNorth) {
        this.getRectangleNorth = getRectangleNorth;
    }

    public float getGeoRectangleSouth() {
        return getRectangleSouth;
    }

    public void setGeoRectangleSouth(float getRectangleSouth) {
        this.getRectangleSouth = getRectangleSouth;
    }

    public ArrayList<Float> getGeoPoints() {
        return geoPoints;
    }

    public void setGeoPoints(ArrayList<Float> geoPoints) {
        this.geoPoints = geoPoints;
    }

}
