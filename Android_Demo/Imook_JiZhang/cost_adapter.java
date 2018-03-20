package com.example.apple.imookdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.apple.imookdemo.R;
import com.example.apple.imookdemo.entity.costInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by apple on 2018/3/19.
 */

public class cost_adapter extends BaseAdapter {
    private Context context;
    private List<costInfo> mcostInfoList;
    private costInfo costInfo;
    LayoutInflater layoutInflater;

    public cost_adapter(Context context,List<costInfo>mcostInfoList)
    {
        this.context=context;
        this.mcostInfoList=mcostInfoList;
        this.layoutInflater=layoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mcostInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mcostInfoList.get(position);
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
            convertView=layoutInflater.inflate(R.layout.list_item,null);
            viewHolder.cost_title=(TextView) convertView.findViewById(R.id.tv_titile);
            viewHolder.cost_date=(TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.cost_money=(TextView) convertView.findViewById(R.id.tv_money);
            convertView.setTag(viewHolder);
        }else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }

        //
        costInfo=mcostInfoList.get(position);
        viewHolder.cost_title.setText(costInfo.cost_title);
        viewHolder.cost_date.setText(costInfo.cost_date);
        viewHolder.cost_money.setText(costInfo.cost_money);
        return convertView;
    }



    class ViewHolder
    {
        TextView cost_title;
        TextView cost_date;
        TextView cost_money;

    }
}
