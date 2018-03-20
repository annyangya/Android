package com.example.apple.imookdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.apple.imookdemo.entity.costInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;

public class chartActivity extends AppCompatActivity {

    private LineChartView mchart;
    private Map<String,Integer> table=new TreeMap<>();//排序的结构
    private LineChartData mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_view);

        mchart=(LineChartView) findViewById(R.id.action_chart);

        List<costInfo> allData= (List<costInfo>) getIntent().getSerializableExtra("daily_cost");

        generateValues(allData);
        generateData();
    }

    private void generateData() {
        List<Line> lines=new ArrayList<>();
        List<PointValue> values=new ArrayList<>();//折线上的点
        int indeX=0;
        //取出所有value，即对应的金额和时间，将其保存
        for(Integer value: table.values())
        {
            values.add(new PointValue(indeX,value));
            indeX++;//处理点
        }

        Line line=new Line(values);
        line.setColor(ChartUtils.COLOR_BLUE);//线的颜色
        line.setShape(ValueShape.CIRCLE);//点的形状，圆点
        line.setPointColor(ChartUtils.COLOR_RED);//点的颜色
        lines.add(line);//整合所有的line
        mData=new LineChartData(lines);
        mchart.setLineChartData(mData);
    }

    private void generateValues(List<costInfo> allData) {

        if(allData!=null)
        {
            for(int i=0;i<allData.size();i++)
            {
                costInfo costInfo=allData.get(i);
                String costData=costInfo.cost_date;
                int costMoney=Integer.parseInt(costInfo.cost_money);
                if(!table.containsKey(costData))//如果不存在这样的key就加入
                {
                    table.put(costData,costMoney);
                }else
                {
                    int originMoney=table.get(costData);//原始值
                    table.put(costData,originMoney+costMoney);//累加
                }
            }
        }

    }


}
