package com.example.user.androidtesttask;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class CountryListFragment extends ListFragment {
    public static final String COUNTRY_URL = "https://api.theprintful.com/countries/";
    private CountryList countries;
    private ExpandableListView expandableListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        countries = new CountryList();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expandable_list_fragment, container, false);
        expandableListView =(ExpandableListView) view.findViewById(android.R.id.list);
        if (!isNetworkConnected()){
            expandableListView.setEmptyView(view.findViewById(R.id.list_empty_view));
            return view;
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle(R.string.thetitle);
        CountryDownloadFromJSonAsynkTask countryDownloadFromJSonAsynkTask = new CountryDownloadFromJSonAsynkTask();
        countryDownloadFromJSonAsynkTask.execute();
        expandableListView.setOnItemLongClickListener(myListGroupLongClicked);
    }

    private ExpandableListView.OnItemLongClickListener myListGroupLongClicked =  new ExpandableListView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView <?> parent, View view, int position, long id) {
            if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                final Fragment fragment = CountryDetailFragment.newInstance(countries.getmCountries().get(position));
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, fragment);
                ft.addToBackStack("tag");
                ft.commitAllowingStateLoss();
                return true;
            }

            return false;
        }
    };

    private class CountryDownloadFromJSonAsynkTask extends AsyncTask<Void, Void, CountryList> {

        private GsonBuilder mBuilder;
        private Gson mGson;
        private ProgressDialog progressDialog;
        public CountryDownloadFromJSonAsynkTask() {
            mBuilder = new GsonBuilder();
            mGson = mBuilder.create();
;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(),"Wait","Downloading....");
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
            progressDialog.dismiss();
            countries = list;
            ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(getActivity(), countries);
            expandableListView.setAdapter(expandableListAdapter);

        }
    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context context;
        private CountryList list;

        public ExpandableListAdapter(Context context, CountryList list) {
            this.context = context;
            this.list = list;

        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this.list.getmCountries().get(groupPosition).getmStates().get(childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            final State item = (State) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.child_view, null);
            }

            TextView txtListChild = (TextView) convertView.findViewById(R.id.textChild);
            txtListChild.setText(item.getmName());
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if (this.list.getmCountries().get(groupPosition).getmStates() != null) {
                return this.list.getmCountries().get(groupPosition).getmStates().size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this.list.getmCountries().get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this.list.getmCountries().size();
        }


        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
            Country item = (Country) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.group_view, null);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.textGroup);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setText(item.getmName());
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }


    }
    private boolean isNetworkConnected() {

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        } else
            return true;
    }
}
