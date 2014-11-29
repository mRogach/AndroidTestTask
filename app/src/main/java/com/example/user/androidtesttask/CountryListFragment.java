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

import org.json.JSONObject;

import java.util.ArrayList;


public class CountryListFragment extends Fragment {
    private Country country;
    private ArrayList<Country> countries;
    private ExpandableListView expandableListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        country = new Country();
        countries = new ArrayList<Country>();
        //setupAdapter(countries);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expandable_list_fragment, container, false);
        expandableListView =(ExpandableListView) view.findViewById(R.id.exListView);
        //setupAdapter(countries);
        ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(getActivity(), countries);
        expandableListView.setAdapter(expandableListAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(R.string.thetitle);
        CountryDownloadFromJSonAsynkTask countryDownloadFromJSonAsynkTask = new CountryDownloadFromJSonAsynkTask();
        countryDownloadFromJSonAsynkTask.execute();
    }


    private class CountryDownloadFromJSonAsynkTask extends AsyncTask<Void, Void, ArrayList<Country>> {
        private static final String TAG = "getList";
        private Gson mGson;

        public CountryDownloadFromJSonAsynkTask() {
            mGson = new GsonBuilder()
                    .registerTypeAdapter(Country.class, new JsonCaseDeserializer())
                    .create();
        }

        @Override
        protected ArrayList<Country> doInBackground(Void... params) {
            ArrayList<Country> list = new ArrayList<Country>();
            Country item = new Country();
            JSONObject jsonObject = new JSONObject();
            HttpRequest request = HttpRequest.get("https://api.theprintful.com/countries/");
            if (request.code() == 200) {
                String response = request.body();
                item = mGson.fromJson(response, Country.class);
//                try {
//                    jsonObject = new JSONObject(response);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    JSONArray jsonArray = jsonObject.getJSONArray("result");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject c = jsonArray.getJSONObject(i);
//                        String code = c.getString("code");
//                        String name = c.getString("name");
//                        //String states = c.getString("states");
//                        JSONArray states = c.getJSONArray("states");
//
//                        for (int j = 0; j < states.length(); j++) {
//                            JSONObject d = states.getJSONObject(j);
//                            String stateCode = d.getString("code");
//                            String stateName = d.getString("name");
//                            item.setStateCode(stateCode);
//                            item.setStateName(stateName);
//                        }

                list.add(item);
            }
            return list;
        }

//        private Country downloadInfo(String code, String name) {
//            Country item = new Country();
//            item.setCode(code);
//            item.setName(name);
//            return item;
//        }

        @Override
        protected void onPostExecute(ArrayList<Country> list) {
            super.onPostExecute(list);
            countries = list;
            //setupAdapter(list);
            ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(getActivity(), list);
            expandableListView.setAdapter(expandableListAdapter);
        }
    }

    public void setupAdapter(ArrayList<Country> list) {

        //ArrayAdapter<Country> adapter = new ArrayAdapter<Country>(getActivity(), android.R.layout.simple_list_item_1, countries);
        ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(getActivity(), list);
        expandableListView.setAdapter(expandableListAdapter);
    }
}
