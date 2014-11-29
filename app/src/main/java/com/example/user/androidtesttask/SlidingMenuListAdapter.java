package com.example.user.androidtesttask;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
* Created by User on 28.11.2014.
*/
    public class SlidingMenuListAdapter extends BaseAdapter {
        Context context;
        LayoutInflater inflater;
        ArrayList<SlidingMenuItem> objects;

    SlidingMenuListAdapter(Context context, ArrayList<SlidingMenuItem> items) {
        this.context = context;
            objects = items;
            this.inflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return objects.size();
        }


        @Override
        public Object getItem(int position) {
            return objects.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.slidingmenu_sectionitem, parent, false);
            }

            SlidingMenuItem slidingMenuItem = getSlidingMenuItem(position);
            TextView textView = (TextView) convertView
                    .findViewById(R.id.slidingmenu_sectionitem_label);
            textView.setText(slidingMenuItem.getTitle());

            final ImageView itemIcon = (ImageView) convertView
                    .findViewById(R.id.slidingmenu_sectionitem_icon);
            itemIcon.setImageDrawable(getDrawableByName(
                    slidingMenuItem.getIcon(), this.context));
            return convertView;
        }


        SlidingMenuItem getSlidingMenuItem(int position) {
            return ((SlidingMenuItem) getItem(position));
        }

    public static Drawable getDrawableByName( String name, Context context ) {
        int drawableResource = context.getResources().getIdentifier(
                name,
                "drawable",
                context.getPackageName());
        if ( drawableResource == 0 ) {
            throw new RuntimeException("Can't find drawable with name: " + name );
        }
        return context.getResources().getDrawable(drawableResource);
    }
}
