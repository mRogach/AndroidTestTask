package com.example.user.androidtesttask;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
* Created by User on 30.11.2014.
*/
public class CountryDetailFragment extends Fragment {
    public static final String COUNTRY_DETAIL_URL = "http://restcountries.eu/rest/v1/alpha/";
    private CountryDetail countryDetail;
    private Country country;
    private ExpandableListView expandableListView;
    private ImageView imageViewFlag;
    private ImageView imageViewButton;
    private TextView textLatitude;
    private TextView textLongitude;
    private TextView textCapital;
    private TextView textRegion;
    private TextView textArea;
    private TextView textCallingCode;


    public static CountryDetailFragment newInstance(String code) {
        Bundle args = new Bundle();
        args.putString("code", code);
        CountryDetailFragment countryDetailFragment = new CountryDetailFragment();
        countryDetailFragment.setArguments(args);
        return countryDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        countryDetail = new CountryDetail();
        country = new Country();
        country.setmCode(getArguments().getString("code"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.country_detail_fragment, container, false);
        imageViewFlag = (ImageView) view.findViewById(R.id.img_Flag);
        textLatitude = (TextView) view.findViewById(R.id.tv_Latitude);
        textLongitude = (TextView) view.findViewById(R.id.tv_Longitude);
        textCapital = (TextView) view.findViewById(R.id.tv_Capital);
        textRegion = (TextView) view.findViewById(R.id.tv_Region);
        textArea = (TextView) view.findViewById(R.id.tv_Area);
        textCallingCode = (TextView) view.findViewById(R.id.tv_CallingCode);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        CountryDetaiDownloadFromJSonAsynkTask countryDetaiDownloadFromJSonAsynkTask = new CountryDetaiDownloadFromJSonAsynkTask();
        countryDetaiDownloadFromJSonAsynkTask.execute(country);
        getActivity().getActionBar().setTitle(country.getmName());
    }


    private class CountryDetaiDownloadFromJSonAsynkTask extends AsyncTask<Country, Void, CountryDetail> {
        private Gson mGson;

        public CountryDetaiDownloadFromJSonAsynkTask() {
            mGson = new GsonBuilder()
                    .registerTypeAdapter(CountryDetail.class, new JsonCaseDeserializer())
                    .create();
        }

        @Override
        protected CountryDetail doInBackground(Country... params) {
            Country item = params[0];
            CountryDetail countryDetail = null;
            HttpRequest request = HttpRequest.get(COUNTRY_DETAIL_URL + item.getmCode());
            if (request.code() == 200) {
                String response = request.body();
              countryDetail = mGson.fromJson(response, CountryDetail.class);
            }
            return countryDetail;
        }

        @Override
        protected void onPostExecute(CountryDetail result) {
            super.onPostExecute(result);
            countryDetail = result;
            textLatitude.setText(countryDetail.getGeoPoints().get(0).toString());
            textLongitude.setText(countryDetail.getGeoPoints().get(1).toString());
            textCapital.setText(String.valueOf(countryDetail.getmCapital()));
            textRegion.setText(String.valueOf(countryDetail.getmRegion()));
            textArea.setText(String.valueOf(countryDetail.getmArea()));
            textCallingCode.setText(String.valueOf(countryDetail.getmCallingCode()));

        }
    }
}
