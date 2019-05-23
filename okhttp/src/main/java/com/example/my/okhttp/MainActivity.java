package com.example.my.okhttp;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnGet, btnPost, btnFile;
    private TextView show;

    private OkHttpClient client;
    private static final String GET_URL = "http://bz.budejie.com/?typeid=2" +
            "&ver=3.4.3" +
            "&no_cry=1" +
            "&client=android" +
            "&c=wallPaper" +
            "&a=wallPaperNew" +
            "&index=1" +
            "&size=60" +
            "&bigid=0";
    private static final String TYPE = "application/octet-stream";
    // 请求条件：platform=2&gifttype=2&compare=60841c5b7c69a1bbb3f06536ed685a48
    public static final String POST_URL = "http://zhushou.72g.com/app/gift/gift_list/";
    // 请求参数：page=1&code=news&pageSize=20&parentid=0&type=1
    private static final String POST_URL2 = "http://admin.wap.china.com/user/NavigateTypeAction.do?processID=getNavigateNews";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initOkHttp();
    }

    private void init() {
        btnGet = findViewById(R.id.get);
        btnPost = findViewById(R.id.post);
        btnFile = findViewById(R.id.file);
        show = findViewById(R.id.show);
    }

    private void initOkHttp() {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnGet.setOnClickListener(this);
        btnPost.setOnClickListener(this);
        btnFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get:
                // 创建“请求条件”对象
                Request request = new Request.Builder()
                        .get()// 请求方式
                        .url(GET_URL)// 请求地址
                        .build();
                client.newCall(request)// 传入“请求条件”，获取 call 对象
                        .enqueue(new Callback() {// 异步请求
                            @Override
                            public void onFailure(Call call, IOException e) {
                                runOnUiThread(() -> show.setText("exception:"+ e.getLocalizedMessage()));
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String res = response.body().string();
                                runOnUiThread(() -> show.setText("response:"+ res));
                            }
                        });
                break;
            case R.id.post:
                // 请求条件：platform=2&gifttype=2&compare=60841c5b7c69a1bbb3f06536ed685a48
                // 请求参数：page=1&code=news&pageSize=20&parentid=0&type=1
                RequestBody postBody = new FormBody.Builder()
                        .add("page", "1")
                        .add("code", "news")
                        .add("pageSize", "20")
                        .add("parentid", "0")
                        .add("type", "1")
                        .build();
                Request postRequest = new Request.Builder()
                        .url(POST_URL)
                        .post(postBody)// post 请求方式，传入请求体
                        .build();
                client.newCall(postRequest).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(() -> show.setText("exception:"+ e.getLocalizedMessage()));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String string = response.body().string();
                        runOnUiThread(() -> show.setText("response:\n"+ string));
                    }
                });
                break;
            case R.id.file:
                // 获取到要上传的文件
                File file = new File(Environment.getExternalStorageDirectory(), "abc.mp4");
                if(!file.exists()) {
                    show.setText("path:"+ Environment.getExternalStorageDirectory().getAbsolutePath());
                } else {
                    // 创建请求体
                    RequestBody fileBody = RequestBody.create(MediaType.parse(TYPE), file);
                    RequestBody requestBody = new MultipartBody.Builder()
                            .addFormDataPart("filename", file.getName(), fileBody)
                            .build();
                    // 根据请求体构件请求条件对象
                    Request postFileRequest = new Request.Builder()
                            .url("http://10.11.64.50/upload/UploadServlet")
                            .post(requestBody)
                            .build();
                    client.newCall(postFileRequest).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(() -> show.setText("exception:"+ e.getLocalizedMessage()));
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String string = response.body().string();
                            runOnUiThread(() -> show.setText("response"+ string));
                        }
                    });
                }
                break;
        }

    }
}
