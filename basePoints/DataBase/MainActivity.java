package com.example.apple.sqlitetext;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_01,btn_02,btn_03,btn_04,btn_05;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_01=(Button)findViewById(R.id.btn_01);
        btn_01.setOnClickListener(this);
        btn_02=(Button)findViewById(R.id.btn_02);
        btn_02.setOnClickListener(this);
        btn_03=(Button)findViewById(R.id.btn_03);
        btn_03.setOnClickListener(this);
        btn_04=(Button)findViewById(R.id.btn_04);
        btn_04.setOnClickListener(this);
        btn_05=(Button)findViewById(R.id.btn_05);
        btn_05.setOnClickListener(this);
        dataBaseHelper=new DataBaseHelper(this,"database.db",null,2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_01:
                dataBaseHelper.getWritableDatabase();
                break;

            case R.id.btn_02:
                SQLiteDatabase sb=dataBaseHelper.getWritableDatabase();
                ContentValues contentValues=new ContentValues();
                contentValues.put("pages",200);
                contentValues.put("price",10);
                contentValues.put("name","java");
                sb.insert("book_info",null,contentValues);

                contentValues.clear();
                //组装第二条数据
                contentValues.put("name","ann");
                contentValues.put("pages",200);
                contentValues.put("price",10.5);
                sb.insert("book_info",null,contentValues);
                break;

            case R.id.btn_03:
                SQLiteDatabase sb2=dataBaseHelper.getWritableDatabase();
                ContentValues contentValues2=new ContentValues();
                contentValues2.put("price",30);
                sb2.update("book_info",contentValues2,"name=?",new String[]{"java"});
                break;

            case R.id.btn_04:
                SQLiteDatabase sb3=dataBaseHelper.getWritableDatabase();
                sb3.delete("book_info","pages>?",new String[]{"500"});
            case R.id.btn_05:
                SQLiteDatabase sb4=dataBaseHelper.getWritableDatabase();
                Cursor cursor=sb4.query("book_info",null,null,null,null,null,null);
                if(cursor.moveToFirst())
                {
                    do
                    {
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        double price=cursor.getDouble(cursor.getColumnIndex("price"));
                        int pages=cursor.getInt(cursor.getColumnIndex("pages"));

                        Log.d("MainACtivity","book name is"+name);
                        Log.d("MainActivity","book price is"+price);
                        Log.d("MainActivity","book pages is"+pages);
                    }while(cursor.moveToNext());
                }

                cursor.close();
                break;
        }

    }
}
