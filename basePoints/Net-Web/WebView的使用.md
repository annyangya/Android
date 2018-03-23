### 用法解释：
1. WebView的getSetting()方法可以设置浏览器的属性，可通过调用setJavaScriptEnabled(true)来使其支持javaScri的脚本
2. 用setWebViewClient()并引入WebViewClient()实例：当需要跳转网页时，目标网页仍显示在该界面中而不是打开系统浏览器
```java
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView=(WebView)findViewById(R.id.mwebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.baidu.com");
    }
}

```
