package com.example.chuboy.sqlite;

/**
 * Created by ChuBoy on 2017/7/24.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_BOOK = "create table Book ("
            + "id integer primary key autoincrement,"
            + "author text,"
            + "price real,"
            + "pages integer,"
            + "name text)";
    public static final String CREATE_CATEGORY = "create table Category("
            + "id integer primary key autoincrement,"
            + "category_name text,"
            + "category_code integer)";
    private Context mContext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext, "Create succeeded",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        /*粗暴寫法
            db.execSQL("drop table if exists Book");
            db.execSQL("drop table if exists Category");
            onCreate(db);
            */
        switch(oldVersion){
            case 1:
                db.execSQL(CREATE_CATEGORY);
                //不寫Break是因為要讓前面的版本都會被執行，並保證資料庫的表結構是最新的
            case 2:
                db.execSQL("alter table Book add column category_id integer");
            default:
        }

    }
}
