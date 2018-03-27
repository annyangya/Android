### 生成一个手风琴下拉列表，手动添加数据

#### 列表数据
```java
package com.example.apple.expandablelistview.entity;

import java.util.ArrayList;
import java.util.List;


public class GroupItem {

    public static List<String> getGroup()
    {
        List<String> groups=new ArrayList<>();//存放父列表的数据
        groups.add("java");
        groups.add("android");
        return groups;
    }

    public static List<List<String>> getChild()
    {
        List<List<String>> childs=new ArrayList<>();
        /*
        每一个item存放的是父列表的数据，将其添加到子列表中
        */
        List<String> item1=new ArrayList<>();
        item1.add("接口");
        item1.add("多态");
        item1.add("封装");
        childs.add(item1);


        List<String> item2=new ArrayList<>();
        item2.add("ListView");
        item2.add("cardView");
        item2.add("gridView");
        childs.add(item2);
        return childs;
    }
}

```

#### 适配器
ExpandableListView的适配器为BaseExpandableListAdapter和CursorThreeAdapter，下面的例子采用BaseExpandableListAdapter。

```java
package com.example.apple.expandablelistview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

/**
 * Created by apple on 2018/3/27.
 实现适配器的封装
 */

public abstract class adapter extends BaseExpandableListAdapter {

    LayoutInflater layoutInflater;
    Context context;
    List<String> groups;
    List<List<String>> childs;

//构造方法
    public adapter(Context context)
    {
        this.context=context;
        layoutInflater=LayoutInflater.from(context);

    }
//添加数据
    public void addData(List<String> groups,List<List<String>> childs)
    {
        this.groups=groups;
        this.childs=childs;
    }
//求组的数量
    @Override
    public int getGroupCount() {
        return groups.size();
    }
//求子列表的数量
    @Override
    public int getChildrenCount(int groupPosition) {
        return childs.get(groupPosition).size();
    }
//获得每一个组的数据，这里是String类型，原方法为Object类型
    @Override
    public String getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }
//获得每一个子列表数据，先获得子列表的组的位置，再根据childPosition获得数据
    @Override
    public String getChild(int groupPosition, int childPosition) {
        return childs.get(groupPosition).get(childPosition);
    }
//求组的id
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }
//子列表的id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }
//判断列表的内容是否有效，根据id可获得数据
    @Override
    public boolean hasStableIds() {
        return false;
    }
//获得组的view
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return myGroupView(groupPosition,convertView);
    }
//抽象方法
    public abstract View myGroupView(int groupPosition,View convertView);
//获得子列表的view
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return myChildView(groupPosition,childPosition,convertView);
    }

    public abstract View myChildView(int groupPosition, int childPosition,View convertView);
//每一个子列表项是否是可以点击的
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

```
接下来创建一个适配器类继承前面封装了的适配器类，并实现其中的抽象方法
```java
package com.example.apple.expandablelistview.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.apple.expandablelistview.R;

/**
 * Created by apple on 2018/3/27.
 对组和子列表进行初始化，并绑定相应的view
 */

public class MyBaseAdapter extends adapter {
    public MyBaseAdapter(Context context) {
        super(context);
    }

    @Override
    public View myGroupView(int groupPosition, View convertView) {
        View view=layoutInflater.inflate(R.layout.group_view,null);
        TextView group_text=(TextView)view.findViewById(R.id.group_text);
        //设置组的内容，通过获取groupPosition来获取内容
        group_text.setText(getGroup(groupPosition));
        return view;
    }

    @Override
    public View myChildView(int groupPosition, int childPosition, View convertView) {
        View view=layoutInflater.inflate(R.layout.child_view,null);
        TextView child_text=(TextView)view.findViewById(R.id.child_text);
        child_text.setText(getChild(groupPosition,childPosition));
        return view;
    }
}

```

接下来再MainActivity中对ExpandableListView进行初始化，并绑定适配器，这里有两种方法
1. MainActivity继承AppCompatActivity
```java
package com.example.apple.expandablelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.apple.expandablelistview.adapter.MyBaseAdapter;
import com.example.apple.expandablelistview.entity.GroupItem;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableListView=(ExpandableListView)findViewById(R.id.expandableListView);
        
        loadText();
    }

    private void loadText() {
        MyBaseAdapter myBaseAdapter=new MyBaseAdapter(this);
        myBaseAdapter.addData(GroupItem.getGroup(),GroupItem.getChild());
        expandableListView.setAdapter(myBaseAdapter);
    }
}

```
2. MainActivity继承ExpandableListActivity
```java
package com.example.apple.expandablelistview;

import android.app.ExpandableListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.apple.expandablelistview.adapter.MyBaseAdapter;
import com.example.apple.expandablelistview.entity.GroupItem;

public class MainActivity extends ExpandableListActivity {

    private ExpandableListView expandableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        
        expandableListView=this.getExpandableListView();

        loadText();
    }

    private void loadText() {
        MyBaseAdapter myBaseAdapter=new MyBaseAdapter(this);
        myBaseAdapter.addData(GroupItem.getGroup(),GroupItem.getChild());
        expandableListView.setAdapter(myBaseAdapter);
    }
}

```
