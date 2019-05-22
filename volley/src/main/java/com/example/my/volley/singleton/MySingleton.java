package com.example.my.volley.singleton;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton instance;
    private static Context context;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private MySingleton(Context context) {
        MySingleton.context = context;
        requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {

                    private final LruCache<String, Bitmap> cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);// 从缓存中取出指定的文件
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);// 缓存获取到的文件
                    }
                });
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new MySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // 使用 getApplicationContext() 就可以避免因为 Activity 或者 BroadcastReceiver 造成的内存泄漏
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }

    /**
     * 网络请求
     *
     * @param request 网络请求对象
     * @param <T>     请求类型
     */
    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    /**
     * 图片加载器
     *
     * @return 图片加载器对象
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
