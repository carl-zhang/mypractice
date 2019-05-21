package com.example.my.eventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

/**
 * 新建一个服务，用于不断生成随机数，并通过 EventBus 在主界面上显示该随机数
 */
public class MainActivity extends AppCompatActivity {

    private TextView random;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        random = findViewById(R.id.random);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        random.setText(String.format(Locale.CANADA, "%15f", event.getRandom()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, RandomService.class);
        startService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
