package com.example.my.volley.singleton;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.example.my.volley.R;

public class SingletonQueueActivity extends AppCompatActivity {

    private ImageView loader;
    private RequestQueue queue;
    private ImageRequest request;
    private String url = "https://cn.bing.com/th?id=OIP.NCOcLXHo1XAEwycfcM8BaQHaFj&pid=Api&rs=1&p=0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleton_queue);

        loader = findViewById(R.id.loader);
        queue = MySingleton.getInstance(getApplicationContext()).getRequestQueue();
        request = new ImageRequest(
                url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        loader.setImageBitmap(response);
                    }
                }, 2048, 2048,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(request);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageLoader imageLoader = MySingleton.getInstance(this).getImageLoader();
        Bitmap bitmap = imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Log.i("karl", "response:"+ response.getRequestUrl());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("karl", "error:"+ error.getMessage());
            }
        }).getBitmap();
//        loader.setImageBitmap(bitmap);
    }
}
