package com.example.my.practice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LoadMoreListView loadMore;
    private List<String> data = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMore = findViewById(R.id.load_more);
        init();
    }

    private void init() {
        data.add("aaa");
        data.add("bbb");
        data.add("ccc");
        data.add("ddd");
        data.add("eee");
        data.add("fff");
        data.add("ggg");
        data.add("hhh");
        data.add("iii");
        data.add("jjj");
        data.add("kkk");
        data.add("lll");
        data.add("mmm");
        data.add("nnn");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        loadMore.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMore.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(1000 * 2);
                            data.add("zzz");
                            data.add("yyy");
                            data.add("xxx");
                            data.add("www");
                            data.add("vvv");
                            data.add("uuu");
                            data.add("ttt");
                            data.add("sss");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                    loadMore.setLoadCompleted();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            }
        });
    }
}
