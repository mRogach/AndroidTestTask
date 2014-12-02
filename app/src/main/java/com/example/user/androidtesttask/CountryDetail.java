package com.example.user.androidtesttask;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by User on 28.11.2014.
 */
@DatabaseTable(tableName="country")
public class CountryDetail implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(dataType= DataType.STRING)
    @SerializedName("name")
    private String mName;

    @DatabaseField(dataType= DataType.STRING)
    @SerializedName("capital")
    private String mCapital;

    @DatabaseField(dataType=DataType.STRING)
    @SerializedName("region")
    private String mRegion;

    @DatabaseField(dataType=DataType.DOUBLE)
    @SerializedName("area")
    private double mArea;

    @DatabaseField(dataType=DataType.INTEGER)
    @SerializedName("callingCodes")
    private int mCallingCode;

    @SerializedName("latlng")
    private ArrayList<Double> geoPoints;

    @DatabaseField(dataType=DataType.STRING)
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
