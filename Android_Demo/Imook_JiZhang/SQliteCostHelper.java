package com.example.apple.imookdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.apple.imookdemo.entity.costInfo;

/**
 * Created by apple on 2018/3/19.
 */

public class SQliteCostHelper extends SQLiteOpenHelper {
    public SQliteCostHelper(Context context) {
        super(context, "daily_cost", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        db.execSQL("create table if not exists daily_cost(" +
                "id Integer primary key,"+
                "cost_title varchar,"+
                "cost_date varchar,"+
                "cost_money varchar)");

    }

    public void insertCost(costInfo costInfo)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("cost_title",costInfo.getCost_title());
        contentValues.put("cost_date",costInfo.getCost_date());
        contentValues.put("cost_money",costInfo.getCost_money());

        sqLiteDatabase.insert("daily_cost",null,contentValues);
    }

    //查询
    public Cursor getAllInfo()
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        return sqLiteDatabase.query("daily_cost",null,null,null,null,null,"cost_date " + "ASC");
    }

    //删除
    public void deleAll()
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.delete("daily_cost",null,null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
