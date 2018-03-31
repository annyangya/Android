package com.example.apple.wechatdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mlistview;
    private List<weChatData> weChatDataList=new ArrayList<>();
    private weChatAdapter weChatAdapter;
    private List<String > mtitle=new ArrayList<>();
    private List<String> murl=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getDataFromNet();
    }

    private void getDataFromNet() {

        mlistview=(ListView)findViewById(R.id.mlistView);
        String url="http://v.juhe.cn/weixin/query?key=7f8ce5e600d72c58d504eaf1d3c428c9"+"&ps=100";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                JsonData(t);
            }
        });

        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(MainActivity.this,webViewActivity.class);
                intent.putExtra("title",mtitle.get(position));
                intent.putExtra("url",murl.get(position));
                startActivity(intent);
            }
        });

    }

    private void JsonData(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONObject jsonResult=jsonObject.getJSONObject("result");
            JSONArray jsonArray=jsonResult.getJSONArray("list");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject json=(JSONObject)jsonArray.get(i);
                String title=json.getString("title");
                String source=json.getString("source");
                String url=json.getString("url");

                weChatData weChatData=new weChatData();
                weChatData.setTitle(title);
                weChatData.setSource(source);
                weChatData.setFirstImg(json.getString("firstImg"));

                mtitle.add(title);
                murl.add(url);

                weChatDataList.add(weChatData);

                weChatAdapter=new weChatAdapter(MainActivity.this,weChatDataList);
                mlistview.setAdapter(weChatAdapter);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
