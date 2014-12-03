package com.example.user.androidtesttask;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity implements CountryDetailFragment.OnViewSelected{

    private static final String MY_SETTINGS = "my_settings";
    private static final String VISITED_TAG = "hasVisitedApp";
    private PendingIntent mPendingIntent;
    private SlidingMenu mSlidingMenu;
    private SharedPreferences mSharedPreferences;
    private String mWhenVisited;
    private AlertDialog.Builder mAlertDialog;
    private Country country;
    private String mDifferentTime;
    private CountryDetail mCountryDetail;
    private CountryDetail test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!(isNetworkConnected())){
        setContentView(R.layout.empty);
        }else {
            setContentView(R.layout.activity_main);
            DatabaseManager.getInstance().init(getApplicationContext());
            getActionBar().setDisplayShowHomeEnabled(false);
            setAlarmService();
            country = new Country();
            setAlertDialog();
            setSlidingMenuConfigurations();
            mCountryDetail = new CountryDetail();

            getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    int stackHeight = getFragmentManager().getBackStackEntryCount();
                    if (stackHeight > 0) {
                        getActionBar().setHomeButtonEnabled(true);
                        getActionBar().setDisplayHomeAsUpEnabled(true);
                    } else {
                        getActionBar().setDisplayHomeAsUpEnabled(false);
                        getActionBar().setHomeButtonEnabled(false);
                    }
                }

            });
        }
    }

    @Override
    protected void onDestroy() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());
        mSharedPreferences = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = mSharedPreferences.edit();
        e.putString(VISITED_TAG, currentDateandTime);
        e.commit();
        super.onDestroy();
    }

    @Override
    public void onViewSelected(CountryDetail viewId) {
        mCountryDetail = viewId;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
            case R.id.save:
                DatabaseManager.getInstance().getmHelper().addData(mCountryDetail);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void setAlarmService() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.AM_PM, Calendar.PM);

        Intent myIntent = new Intent(MainActivity.this, MyAlarmService.class);
        mPendingIntent = PendingIntent.getService(MainActivity.this, 0, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, mPendingIntent);

    }

    private void setSlidingMenuConfigurations() {
        mSlidingMenu = new SlidingMenu(this);
        mSlidingMenu.setMode(SlidingMenu.LEFT);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mSlidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        mSlidingMenu.setShadowDrawable(R.drawable.slidingmenu_shadow);
        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        mSlidingMenu.setFadeDegree(0.35f);
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        mSlidingMenu.setMenu(R.layout.slidingmenu);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private String getTimeDiff(String time, String curTime) throws ParseException {
        DateFormat formatter;
        Date curDate;
        Date oldDate;
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        curDate = (Date) formatter.parse(curTime);
        oldDate = (Date) formatter.parse(time);
        long oldMillis = oldDate.getTime();
        long curMillis = curDate.getTime();
        Log.d("CaseListAdapter", "Date-Milli:Now:" + curDate.toString() + ":" + curMillis + " old:" + oldDate.toString() + ":" + oldMillis);
        CharSequence text = DateUtils.getRelativeTimeSpanString(oldMillis, curMillis, 0);
        return text.toString();
    }

    private void setAlertDialog(){
        mSharedPreferences = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        mWhenVisited = mSharedPreferences.getString(VISITED_TAG, "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());
        try {
            mDifferentTime = getTimeDiff(mWhenVisited, currentDateandTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mAlertDialog = new AlertDialog.Builder(this);
        mAlertDialog.setTitle(R.string.date_info_title);
        mAlertDialog.setMessage(mWhenVisited + "(" + mDifferentTime + ")");
        mAlertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Fragment fragment = new CountryListFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, fragment);
                ft.commitAllowingStateLoss();
            }
        });
        mAlertDialog.setCancelable(false);
        mAlertDialog.show();
    }
    private boolean isNetworkConnected() {

        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        } else
            return true;
    }

}

