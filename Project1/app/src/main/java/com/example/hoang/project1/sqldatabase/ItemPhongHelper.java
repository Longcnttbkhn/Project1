package com.example.hoang.project1.sqldatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hoang on 12/5/2015.
 */
public class ItemPhongHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "timnhatro.db";
    private static final int SCHEMA_VERSION=1;

    public ItemPhongHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists itemphong (" +
                "idP varchar(5) primary key not null," +
                "kieuPhong varchar(20) not null," +
                "khuVuc varchar(50) not null," +
                "dienTich varchar(10) not null," +
                "giaCa varchar(10) not null);";
        Log.d("Long", sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
