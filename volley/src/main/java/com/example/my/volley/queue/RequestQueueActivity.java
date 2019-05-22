package com.example.my.volley.queue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.example.my.volley.R;

public class RequestQueueActivity extends AppCompatActivity {

    private TextView formulate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_queue);
        formulate = findViewById(R.id.formulate);
        formulateQueue();
    }

    // 自定义 RequestQueue
    private void formulateQueue() {
        RequestQueue queue;
        // 初始化缓存对象
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);// 1M 缓存
        // 使用 HttpURLConnection 作为 http 客户端，初始化网络对象
        Network network = new BasicNetwork(new HurlStack());

        // 使用缓存对象与网络对象，初始化 RequestQueue
        queue = new RequestQueue(cache, network);

        // 启动 queue
        queue.start();

        String url = "https://github.com/carl-zhang/mypractice";

        // 自定义请求对象，处理请求回应
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        formulate.setText("response:"+ response.substring(0, 500));
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                formulate.setText("error:"+ error.getMessage());
            }
        });

        // 将请求对象添加到请求队列中
        queue.add(request);
    }
}
