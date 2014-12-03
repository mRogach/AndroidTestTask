package com.example.user.androidtesttask;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 02.12.2014.
 */
public class DBCountryFragment extends ListFragment implements AdapterView.OnItemClickListener{
    private List<CountryDetail> list;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = DatabaseManager.getInstance().getmHelper().GetData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.db_list_fragment, container, false);
        listView = (ListView) view.findViewById(android.R.id.list);
        listView.setAdapter(new CountryAdapter(getActivity(), R.layout.row, list));
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final Fragment fragment = new CountryDetailFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.addToBackStack("tag");
        ft.commitAllowingStateLoss();
    }


    public class CountryAdapter extends ArrayAdapter<CountryDetail> {
        private Context mContext;
        private int row;
        private List<CountryDetail> list;

        public CountryAdapter(Context context, int textViewResourceId, List<CountryDetail> list) {
            super(context, textViewResourceId, list);
            this.mContext = context;
            this.row = textViewResourceId;
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            ViewHolder holder;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(row, null);
                holder = new ViewHolder();
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            if ((list == null) || ((position + 1) > list.size()))
                return view;
            CountryDetail obj = list.get(position);
            holder.name = (TextView) view.findViewById(R.id.tv_name);
            holder.name.setText(obj.getmName());

            return view;
        }

        public class ViewHolder {
            public TextView name;
        }
    }
}
