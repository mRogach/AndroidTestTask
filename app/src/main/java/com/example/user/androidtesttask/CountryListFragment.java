package com.example.user.androidtesttask;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class CountryListFragment extends Fragment {
    public static final String COUNTRY_URL = "https://api.theprintful.com/countries/";
    private Country country;
    private CountryList countries;
    private ExpandableListView expandableListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        country = new Country();
        countries = new CountryList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expandable_list_fragment, container, false);
        expandableListView =(ExpandableListView) view.findViewById(R.id.exListView);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(R.string.thetitle);
        CountryDownloadFromJSonAsynkTask countryDownloadFromJSonAsynkTask = new CountryDownloadFromJSonAsynkTask();
        countryDownloadFromJSonAsynkTask.execute();
    }


    private class CountryDownloadFromJSonAsynkTask extends AsyncTask<Void, Void, CountryList> {

        private GsonBuilder mBuilder;
        private Gson mGson;
        public CountryDownloadFromJSonAsynkTask() {
            mBuilder = new GsonBuilder();
            mGson = mBuilder.create();
;
        }

        @Override
        protected CountryList doInBackground(Void... params) {
            CountryList item = null;
            HttpRequest request = HttpRequest.get(COUNTRY_URL);
            if (request.code() == 200) {
                String response = request.body();
                item = mGson.fromJson(response, CountryList.class);
            }
            return item;
        }


        @Override
        protected void onPostExecute(CountryList list) {
            super.onPostExecute(list);
            countries = list;
            ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(getActivity(), list);
            expandableListView.setAdapter(expandableListAdapter);
        }
    }

}
