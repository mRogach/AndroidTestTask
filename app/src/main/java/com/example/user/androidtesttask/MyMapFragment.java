package com.example.user.androidtesttask;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by User on 01.12.2014.
 */
public class MyMapFragment extends Fragment implements LocationListener{
    private CountryDetail mCountryDetail;
    private MapView mMapView;
    private GoogleMap mMap;
    private Circle mCircle;
    private LatLng mPoint;
    private LatLng mMypoint;
    private double mRadius;
    private Location mLocation;
    private Location mLocation2;
    private Polyline mPolyline;
    private float mDistance;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    public static MyMapFragment newInstance(CountryDetail code) {
        Bundle args = new Bundle();
        args.putSerializable("code", code);
        MyMapFragment myMapFragment = new MyMapFragment();
        myMapFragment.setArguments(args);
        return myMapFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocation2 = new Location("");
        mLocation = new Location("");
        mCountryDetail = (CountryDetail) getArguments().getSerializable("code");
        mPoint = new LatLng(mCountryDetail.getGeoPoints().get(0), mCountryDetail.getGeoPoints().get(1));
        mLocation2.setLatitude(mCountryDetail.getGeoPoints().get(0));
        mLocation2.setLongitude(mCountryDetail.getGeoPoints().get(1));
        mRadius = Math.sqrt(mCountryDetail.getmArea() / Math.PI);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        getActivity().getActionBar().setTitle("MAP");
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMap = mMapView.getMap();
        mMap.setMyLocationEnabled(true);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        mDistance = mLocation.distanceTo(mLocation2)/1000;
        showCircle(mPoint, mRadius);
        mMap.addMarker(new MarkerOptions().position(mPoint).title(mCountryDetail.getmCapital()).snippet(""));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    public void showCircle(LatLng point, double r) {
        CircleOptions circleOptions = new CircleOptions()
                .center(point)
                .radius(r * 1000)
                .fillColor(Color.GRAY)
                .strokeColor(Color.BLUE)
                .strokeWidth(5);

        mCircle = mMap.addCircle(circleOptions);
    }
    private void drawLine(Location location) {

        if (mPolyline == null) {
            PolylineOptions polylineOptions = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
            polylineOptions.add(new LatLng(location.getLatitude(), location.getLongitude()));
            polylineOptions.add(mPoint);


            mPolyline = mMap.addPolyline(polylineOptions);
        }
        else {
            if (mPolyline.isVisible()) {
                mPolyline.remove();
                mPolyline = null;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null) {
            mLocation.setLatitude(location.getLatitude());
            mLocation.setLongitude(location.getLongitude());
            mMypoint = new LatLng(location.getLatitude(), location.getLongitude());
            drawLine(location);
            mMap.addMarker(new MarkerOptions().position(mMypoint)
                    .title("from me to " + mCountryDetail.getmName() + " " + mDistance + "km"))
                    .showInfoWindow();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
