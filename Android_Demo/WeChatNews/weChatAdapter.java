package com.example.apple.wechatdemo;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by apple on 2018/3/30.
 */

public class weChatAdapter extends BaseAdapter {

    private Context context;
    private List<weChatData> weChatDataList;
    LayoutInflater layoutInflater;
    public weChatAdapter(Context context,List<weChatData> weChatDataList)
    {
        this.context=context;
        this.weChatDataList=weChatDataList;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return weChatDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return weChatDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null)
        {
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.listview_item,null);
            viewHolder.tv_img=(ImageView)convertView.findViewById(R.id.tv_img);
            viewHolder.tv_title=(TextView)convertView.findViewById(R.id.tv_title);
            viewHolder.tv_source=(TextView)convertView.findViewById(R.id.tv_source);
            convertView.setTag(viewHolder);
        }else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }

        weChatData weChatData=weChatDataList.get(position);
        Picasso.with(context).load(weChatData.getFirstImg()).into(viewHolder.tv_img);
        viewHolder.tv_title.setText(weChatData.getTitle());
        viewHolder.tv_source.setText(weChatData.getSource());
        return convertView;
    }

    class ViewHolder
    {
        ImageView tv_img;
        TextView tv_title;
        TextView tv_source;
    }
}
