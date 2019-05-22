package com.example.my.volley.simple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.my.volley.R;

/**
 * 可以在任意线程发起请求，但请求响应最终都会回到主线程</br>
 * 1. 创建 RequestQueue：Volley.newRequestQueue();</br>
 * 2. 创建 StringRequest：new StringRequest();</br>
 * (添加标签：request.setTag();)</br>
 * 3. 开启请求：queue.add(request);<br>
 * 4. 取消请求：queue.cancelAll();
 */
public class SimpleRequestActivity extends AppCompatActivity {

    private TextView simpleRequest;
    private String url = "https://developer.android.google.cn/training/volley/simple.html#java";
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_request);
        simpleRequest = findViewById(R.id.simple_request);
        queue = Volley.newRequestQueue(this);// 创建队列

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 创建请求体
        StringRequest request = new StringRequest(
                Request.Method.GET, // 请求方式
                url, // 请求地址
                new Response.Listener<String>() {// 请求成功回应
            @Override
            public void onResponse(String response) {
                simpleRequest.setText("response:"+ response.substring(0, 1000));
            }
        },
                new Response.ErrorListener() {// 请求失败回应
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleRequest.setText("error:"+ error.getMessage());
            }
        });

        request.setTag(this);// 添加标签，用于之后的取消请求操作

        queue.add(request);// 将请求体传入请求队列
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(queue != null) {
            queue.cancelAll(this);// 根据加入的标签或过滤器，取消请求
        }
    }
}
