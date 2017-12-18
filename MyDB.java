package com.example.wuanlan97.androidexam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by wuanlan97 on 2017/12/15.
 */

/*************************************************
 这个类用于数据库操作，被Book类调用，只要导入工程即可
 ************************************************/
public class MyDB extends SQLiteOpenHelper {
    private Context mContext;
    public MyDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;

    }
    public void SQL(String sql,SQLiteDatabase db)
    {
        db.execSQL(sql);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table user(username text primary key not null,name text not null,password text not null)");
        sqLiteDatabase.execSQL("create table book(username text not null,date text not null,money real not null)");
        final Toast toast = Toast.makeText(mContext,"数据库初始化完成",Toast.LENGTH_LONG);
        toast.show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final Toast toast = Toast.makeText(mContext,"数据库升级完成",Toast.LENGTH_LONG);
        toast.show();

    }
}
