package com.example.user.androidtesttask;

import android.app.Fragment;
import android.graphics.Color;
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

/**
 * Created by User on 01.12.2014.
 */
public class MyMapFragment extends Fragment {
    private CountryDetail countryDetail;
    private MapView mapView;
    private GoogleMap map;
    private Circle mCircle;
    private LatLng point;
    private double radius;


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
        countryDetail = (CountryDetail) getArguments().getSerializable("code");
        point = new LatLng(countryDetail.getGeoPoints().get(0), countryDetail.getGeoPoints().get(1));
        radius = Math.sqrt(countryDetail.getmArea() / Math.PI);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        getActivity().getActionBar().setTitle("MAP");
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        map = mapView.getMap();
        map.setMyLocationEnabled(true);
        showCircle(point, radius);
        map.addMarker(new MarkerOptions().position(point).title(countryDetail.getmCapital()).snippet(""));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    public void showCircle(LatLng point, double r) {
        CircleOptions circleOptions = new CircleOptions()
                .center(point)
                .radius(r * 1000)
                .fillColor(Color.GRAY)
                .strokeColor(Color.BLUE)
                .strokeWidth(5);

        mCircle = map.addCircle(circleOptions);
    }
}
