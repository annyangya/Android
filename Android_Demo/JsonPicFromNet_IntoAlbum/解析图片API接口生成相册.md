### 相册实例
#### 主要功能
1. 从网络解析图片放在gridView中显示出来
2. 支持图片缩放，可点击图片并放大缩小

#### 步骤实现：
##### 接口：“http://gank.io/api/data/%E7%A6%8F%E5%88%A9/50/1”
##### 实体类：存放图片的链接
```java
public class GirlData {
    private String imgUrl;//图片地址

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
```
##### 封装Picasso库，里面是主要的Picasso操作图片的方法
详情见网址：“http://square.github.io/picasso/“
```java
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


public class PicassoUtils {

//基本方法
    public static void loadImgView(Context context, String url, ImageView imageView)
    {
        Picasso.with(context).load(url).into(imageView);
    }
//可设置图片的宽，高
    public static  void loadImgViewResize(Context context,String url,int width,int height,ImageView imageView)
    {
        Picasso.with(context)
                .load(url)
                .resize(width, height)
                .centerCrop()
                .into(imageView);
    }

    public static  void loadImgViewCrop(Context context,String url,ImageView imageView)
    {
        Picasso.with(context).load(url).transform(new CropSquareTransformation()).into(imageView);
    }


    public static class CropSquareTransformation implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override public String key() { return "square()"; }
    }
//加载图片及错误图片加载情况
    public static void loadImgViewHolder(Context context,String url,int localImg,int errorimg,ImageView imageView)
    {
        Picasso.with(context)
                .load(url)
                .placeholder(localImg)
                .error(errorimg)
                .into(imageView);
    }
}

```
##### 适配器的使用
```java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class adapter extends BaseAdapter {
    private Context context;
    List<GirlData> girlDataList;
    LayoutInflater layoutInflater;
    private WindowManager wm;
    private int width;
    public adapter(Context context, List<GirlData> girlDataList)
    {
        this.context=context;
        this.girlDataList=girlDataList;
        layoutInflater=LayoutInflater.from(context);
        wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);//获取系统服务
        width=wm.getDefaultDisplay().getWidth();//获取屏幕宽度
    }
    @Override
    public int getCount() {
        return girlDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return girlDataList.get(position);
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
            convertView=layoutInflater.inflate(R.layout.img_item,null);
            viewHolder.imageView=(ImageView)convertView.findViewById(R.id.imgeView);
            convertView.setTag(viewHolder);//保存缓存
        }else
        {
            viewHolder=(ViewHolder)convertView.getTag();//获取缓存
        }

        GirlData girlData=girlDataList.get(position);
        //解析图片
        String url=girlData.getImgUrl();
        //核心代码
        //用Picasso封装的方法加载图片并设置大小
        PicassoUtils.loadImgViewResize(context,url,width/2,500,viewHolder.imageView);

        return convertView;
    }

    class ViewHolder
    {
        ImageView imageView;
    }
}

```
##### 点击图片的Dialog————自定义dialog
```java
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;


public class CustomDialog extends Dialog{

    //定义模板
    public CustomDialog(Context context,int layout,int style) {
        this(context, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,layout,style, Gravity.CENTER);
    }

    //定义属性
    /*
    layout:dialog的布局
    style：dialog的风格
    gravity：dialog的弹出方向，一般有居中，从下弹出或从上弹出
    anim：dialog弹出的动画
    */
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity,int anim){
        super(context,style);
        //设置属性
        setContentView(layout);
        //获取window，window能操作dialog的宽高等
        Window window = getWindow();
        //layoutParams能保存设置的宽高属性
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = gravity;
        //更改属性
        window.setAttributes(layoutParams);
        //设置动画
        window.setWindowAnimations(anim);
    }

    //实例
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity){
        this(context,width,height,layout,style,gravity,R.style.pop_anim_style);
    }
}

```

##### MainActivity
```java
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
/*
photoView:
1.监听事件
2.tishikuang
3。加载图片
4。photoView缩放功能
 */

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private List<GirlData>girlDataList=new ArrayList<>();//数据集
    adapter adapter;
    //提示框
    private CustomDialog customDialog;
    //预览图片
    private ImageView tv_img;
    //图片地址数据
    private List<String> mlistUrl=new ArrayList<>();
    //photoView
    private PhotoViewAttacher photoViewAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        gridView=(GridView)findViewById(R.id.mGridView);
//初始化dialog
        customDialog=new CustomDialog(this, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,R.layout.dialog_gilr,R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
      
       tv_img=(ImageView)customDialog.findViewById(R.id.tv_img);

        String url="http://gank.io/api/data/%E7%A6%8F%E5%88%A9/50/1";
        //解析图片数据
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                JsonData(t);
            }
        });

        //为每一个图片item添加监听事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //解析图片
                PicassoUtils.loadImgView(MainActivity.this,mlistUrl.get(position),tv_img);
                //缩放
                photoViewAttacher=new PhotoViewAttacher(tv_img);
                photoViewAttacher.update();//刷新
                customDialog.show();
            }
        });

    }
//解析数据，并将每一个图片地址存放
    public void JsonData(String t)
    {
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONArray jsonArray=jsonObject.getJSONArray("results");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject json=(JSONObject)jsonArray.get(i);
                String url=json.getString("url");

                mlistUrl.add(url);

                GirlData girlData=new GirlData();
                girlData.setImgUrl(url);
                girlDataList.add(girlData);
            }

            adapter=new adapter(this,girlDataList);
            gridView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

```




