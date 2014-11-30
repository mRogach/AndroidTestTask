package com.example.user.androidtesttask;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {
    private static final String MY_SETTINGS = "my_settings";
    private static final String NOT_VISITED_TAG = "hasNotVisited";
    private static final String VISITED_TAG = "hasVisitedApp";
    private static final String NOT_VISITED_MESSAGE = "Вы еще здесь не были!";
    private PendingIntent pendingIntent;
    private SlidingMenu slidingMenu;
    private SharedPreferences sharedPreferences;
    private String whenVisited = "Вы еще здесь не были!";
    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAlarmService();
        sharedPreferences = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(MY_SETTINGS)) {
            whenVisited = sharedPreferences.getString(MY_SETTINGS, null);
        }
        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.date_info_title);
        alertDialog.setMessage(whenVisited);
        alertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Fragment fragment = new CountryListFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, fragment);
                //ft.addToBackStack("tag");
                ft.commitAllowingStateLoss();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
        setSlidingMenuConfigurations();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd_HHmm");
        String currentDateandTime = sdf.format(new Date());

        sharedPreferences = getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
       // boolean hasVisited = (sharedPreferences.getBoolean("ooo", false));
       // if (!hasVisited) {
            // выводим нужную активность
         //   SharedPreferences.Editor e = sharedPreferences.edit();
        //    e.putString(NOT_VISITED_TAG, NOT_VISITED_MESSAGE);

        //    e.putBoolean("ooo", true);
       //     e.commit(); // не забудьте подтвердить изменения
      //  }else {
            SharedPreferences.Editor e = sharedPreferences.edit();
            e.putString(VISITED_TAG, currentDateandTime);
            e.commit();
       // }
    }

    @Override
    public void onBackPressed() {
        if ( slidingMenu.isMenuShowing()) {
            slidingMenu.toggle();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_MENU ) {
            this.slidingMenu.toggle();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.slidingMenu.toggle();
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
    private void setAlarmService (){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.AM_PM, Calendar.PM);

        Intent myIntent = new Intent(MainActivity.this, MyAlarmService.class);
        pendingIntent = PendingIntent.getService(MainActivity.this, 0, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingIntent);

    }
    private void setSlidingMenuConfigurations(){
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.slidingmenu_shadow);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        slidingMenu.setMenu(R.layout.slidingmenu);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}

