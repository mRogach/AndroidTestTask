package com.example.user.androidtesttask;

/**
 * Created by User on 02.12.2014.
 */
import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

class DatabaseManager {
    private static volatile DatabaseManager instance;
    private DataBaseHelper mHelper;

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context){
        if(mHelper == null)
            mHelper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
    }

    public void release(){
        if(mHelper != null)
            OpenHelperManager.releaseHelper();
    }

    public DataBaseHelper getmHelper() {
        return mHelper;
    }
}