import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.apple.chatdemo.R;
import com.example.apple.chatdemo.entity.chatData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2018/3/30.
 */

public class chatAdapter extends BaseAdapter {
    public static final int VALUE_LEFT=1;
    public static final int VALUE_RIGHT=2;
    private Context context;
    LayoutInflater layoutInflater;
    List<chatData> chatDataList;

    public chatAdapter(Context context,List<chatData> chatDataList)
    {
        this.chatDataList=chatDataList;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return chatDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderLeft viewHolderLeft=null;
        ViewHolderRight viewHolderRight=null;

        int type=getItemViewType(position);
        if(convertView==null)
        {
            switch (type)
            {
                case VALUE_LEFT:
                    viewHolderLeft=new ViewHolderLeft();
                    convertView=layoutInflater.inflate(R.layout.left_item,null);
                    //TextView tv_text_left=(TextView)convertView.findViewById(R.id.tv_text_left);
                    viewHolderLeft.tv_text_left=(TextView)convertView.findViewById(R.id.tv_text_left);
                    convertView.setTag(viewHolderLeft);
                    break;
                case VALUE_RIGHT:
                    viewHolderRight=new ViewHolderRight();
                    convertView=layoutInflater.inflate(R.layout.right_item,null);
                   // TextView tv_text_right=(TextView)convertView.findViewById(R.id.tv_text_right);
                    viewHolderRight.tv_text_right=(TextView)convertView.findViewById(R.id.tv_text_right);
                    convertView.setTag(viewHolderRight);
                    break;
            }
        }else
        {
            switch (type)
            {
                case VALUE_LEFT:
                    viewHolderLeft=(ViewHolderLeft)convertView.getTag();
                    break;
                case VALUE_RIGHT:
                    viewHolderRight=(ViewHolderRight)convertView.getTag();
                    break;
            }
        }


        chatData data=chatDataList.get(position);
        switch (type)
        {
            case VALUE_LEFT:
                viewHolderLeft.tv_text_left.setText(data.getText());
                break;
            case VALUE_RIGHT:
                viewHolderRight.tv_text_right.setText(data.getText());
                break;
        }
        return convertView;
    }

    class ViewHolderRight
    {
        TextView tv_text_right;
    }

    class ViewHolderLeft
    {
        TextView tv_text_left;
    }

    @Override
    public int getItemViewType(int position) {
        chatData data=chatDataList.get(position);
        int type=data.getType();
        return type;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }
}
