package com.example.user.androidtesttask;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 29.11.2014.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Country> list;

    public ExpandableListAdapter(Context context, ArrayList<Country> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.list.get(groupPosition).getmStates().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final State item = (State) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_view, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.textChild);

        txtListChild.setText(item.getmName());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.list.get(groupPosition).getmStates().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.list.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.list.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Country item = (Country) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_view, null);
        }

        TextView textView = (TextView) convertView
                .findViewById(R.id.textGroup);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(item.getName());

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