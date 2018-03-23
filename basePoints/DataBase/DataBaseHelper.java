package com.example.apple.sqlitetext;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

/**
 * Created by apple on 2018/3/23.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }


    public static final String CRATER_BOOK_INFO="create table Book_info(" +
            "id Integer primary key autoincrement," +
            "name varchar," +
            "pages Integer," +
            "price double)";

    public static final String CRATER_PERSON_INFO="create table Person_info(" +
            "id Integer primary key autoincrement" +
            "name varchar," +
            "age Integer," +
            "height double," +
            "address varchar)";

    //创建
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRATER_BOOK_INFO);
        db.execSQL(CRATER_PERSON_INFO);
        Toast.makeText(context, "successed", Toast.LENGTH_SHORT).show();

    }

    //更新
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book_info");
        db.execSQL("drop table if exists Person_info");
        onCreate(db);

    }

}
