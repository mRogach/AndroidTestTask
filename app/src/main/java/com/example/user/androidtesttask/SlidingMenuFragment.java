package com.example.user.androidtesttask;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
* Created by User on 28.11.2014.
*/
public class SlidingMenuFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListView slidingMenuListView;
    ArrayList<SlidingMenuItem> slidingMenuList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        slidingMenuList = createMenu();
        View view = inflater.inflate(R.layout.slidingmenu_fragment, container, false);
        slidingMenuListView = (ListView) view.findViewById(R.id.slidingmenu_view);
        SlidingMenuListAdapter slidingMenuListAdapter = new SlidingMenuListAdapter(this.getActivity(), slidingMenuList);
        slidingMenuListView.setAdapter(slidingMenuListAdapter);
        slidingMenuListView.setOnItemClickListener(this);
        return view;
    }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            switch(position) {
                case  0:
                    final Fragment fragment = new CountryListFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragmentContainer, fragment);
                    ft.commitAllowingStateLoss();
                    break;
                case  1:
                    final Fragment fragment2 = new DBCountryFragment();
                    FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                    ft2.replace(R.id.fragmentContainer, fragment2);
                    ft2.addToBackStack("tag");
                    ft2.commitAllowingStateLoss();
                   break;
                case  2:
                   getActivity().finish();
                    System.exit(0);
                    break;
            }
        }

    private ArrayList<SlidingMenuItem> createMenu() {
        ArrayList<SlidingMenuItem> list = new ArrayList<SlidingMenuItem>();
        SlidingMenuItem firstItem = new SlidingMenuItem(1, "All Countries", "img_countries");
        SlidingMenuItem secondItem = new SlidingMenuItem(2, "All Saved", "img_save");
        SlidingMenuItem thirdItem = new SlidingMenuItem(3, "Exit", "img_exit");
        list.add(firstItem);
        list.add(secondItem);
        list.add(thirdItem);
        return list;
    }

}
