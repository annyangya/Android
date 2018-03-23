#### 在Android上发送网络请求用HttpURLConnection
点击button获取百度数据

```java
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUrlConnectionActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_url_connection);
        button=(Button)findViewById(R.id.getInfoFromNet);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.getInfoFromNet:
                sendRequestWithHtppUrlConnection();
                break;
        }

    }

    private void sendRequestWithHtppUrlConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url=new URL("https://www.baidu.com");
                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setReadTimeout(8000);
                    //httpURLConnection.connect();

                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder=new StringBuilder();
                    String line;
                    while((line=bufferedReader.readLine())!=null)
                    {
                        stringBuilder.append(line);
                    }

                    Looper.prepare();
                    Toast.makeText(HttpUrlConnectionActivity.this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    inputStream.close();
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}

```
