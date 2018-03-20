package com.example.apple.imookdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.example.apple.imookdemo.adapter.cost_adapter;
import com.example.apple.imookdemo.entity.costInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mlistView;
    private List<costInfo> mcostInfoList;
    //private costInfo costInfo;
    private SQliteCostHelper sQliteCostHelper;
    private LayoutInflater layoutInflater;
    private cost_adapter cost_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mcostInfoList=new ArrayList<>();
        sQliteCostHelper=new SQliteCostHelper(this);
        mlistView=(ListView) findViewById(R.id.mlistView);
        initData();
        cost_adapter=new cost_adapter(this,mcostInfoList);
        mlistView.setAdapter(cost_adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutInflater=LayoutInflater.from(MainActivity.this);//内部类不用this
                //自定义dailog
                View dialog=layoutInflater.inflate(R.layout.write_info,null);
                final EditText add_title=dialog.findViewById(R.id.add_title);
                final EditText add_money=dialog.findViewById(R.id.add_money);
                final DatePicker add_date=dialog.findViewById(R.id.add_date);
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this)
                        .setTitle("欢迎使用记账本")
                        .setView(dialog)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                costInfo costInfo=new costInfo();
                                costInfo.cost_title=add_title.getText().toString();
                                costInfo.cost_money=add_money.getText().toString();
                                costInfo.cost_date=add_date.getYear()+"-"+(add_date.getMonth()+1)+"-"+add_date.getDayOfMonth();
                                sQliteCostHelper.insertCost(costInfo);
                                mcostInfoList.add(costInfo);
                                cost_adapter.notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton("cancel",null);
                builder.create();
                builder.show();

            }
        });


    }

    private void initData() {
       
        Cursor cursor=sQliteCostHelper.getAllInfo();
        if(cursor!=null)
        {
            while(cursor.moveToNext())
            {
                costInfo costInfo=new costInfo();
                costInfo.cost_title=cursor.getString(cursor.getColumnIndex("cost_title"));
                costInfo.cost_date=cursor.getString(cursor.getColumnIndex("cost_date"));
                costInfo.cost_money=cursor.getString(cursor.getColumnIndex("cost_money"));
                mcostInfoList.add(costInfo);
            }
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_chart) {
            Intent intent = new Intent(MainActivity.this, chartActivity.class);
            intent.putExtra("daily_cost", (Serializable) mcostInfoList);//通过key可以取出mcostInfoList
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
