package com.example.user.androidtesttask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private Context mContext;
    private static final String DATABASE_NAME = "db_country.db";
    private static final int DATABASE_VERSION = 1;
    private Dao mCountryDao = null;
    private RuntimeExceptionDao countryRuntimeDao = null;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DataBaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, CountryDetail.class);
        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DataBaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, CountryDetail.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }


    public Dao getDao() throws SQLException {
        if (mCountryDao == null) {
            mCountryDao = getDao(CountryDetail.class);
        }
        return mCountryDao;
    }

    public RuntimeExceptionDao  getSimpleDataDao() {
        if (countryRuntimeDao == null) {
            countryRuntimeDao = getRuntimeExceptionDao(CountryDetail.class);
        }
        return countryRuntimeDao;
    }

    public List<CountryDetail> GetData()
    {
        DataBaseHelper helper = new DataBaseHelper(mContext);
        RuntimeExceptionDao simpleDao = helper.getSimpleDataDao();
        List<CountryDetail> list = simpleDao.queryForAll();
        return list;
    }


    public int addData(CountryDetail countryDetail)
    {
        RuntimeExceptionDao  dao = getSimpleDataDao();
        int i = dao.create(countryDetail);
        return i;
    }

    @Override
    public void close() {
        super.close();
        mCountryDao = null;
    }
}