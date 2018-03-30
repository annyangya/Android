import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.apple.chatdemo.adapter.chatAdapter;
import com.example.apple.chatdemo.entity.chatData;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mlistView;
    private Button btn_send;
    private EditText et_text;
    List<chatData> chatDataList = new ArrayList<>();
    chatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mlistView = (ListView) findViewById(R.id.mlistView);
        et_text = (EditText) findViewById(R.id.et_text);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        chatAdapter = new chatAdapter(this, chatDataList);
        mlistView.setAdapter(chatAdapter);

        addLeftText("hello,我是ann");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                String text = et_text.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    if (text.length() > 30) {
                        Toast.makeText(this, "内容长度不能大于30", Toast.LENGTH_SHORT).show();
                    } else {
                        et_text.setText("");
                        addRightText(text);
                        String url = "http://op.juhe.cn/robot/index?info=" + text + "&key=7a48539921338ef90866922b21e25f6d";
                        RxVolley.get(url, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                JsonData(t);
                            }
                        });
                    }
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    public void addRightText(String text) {
        chatData chatData = new chatData();
        chatData.setText(text);
        chatData.setType(chatAdapter.VALUE_RIGHT);
        chatDataList.add(chatData);

        chatAdapter.notifyDataSetChanged();

        mlistView.setSelection(mlistView.getBottom());
    }

    public void addLeftText(String text) {
        chatData chatData = new chatData();
        chatData.setText(text);
        chatData.setType(chatAdapter.VALUE_LEFT);
        chatDataList.add(chatData);

        chatAdapter.notifyDataSetChanged();
        mlistView.setSelection(mlistView.getBottom());
    }

    public void JsonData(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String text = jsonResult.getString("text");
            addLeftText(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
