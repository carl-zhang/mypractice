package com.example.my.volley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.my.volley.queue.RequestQueueActivity;
import com.example.my.volley.simple.SimpleRequestActivity;
import com.example.my.volley.singleton.SingletonQueueActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.volley_simple);
        btn2 = findViewById(R.id.volley_request_quque);
        btn3 = findViewById(R.id.volley_1);
        btn4 = findViewById(R.id.volley_2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.volley_simple:
                intent.setClass(this, SimpleRequestActivity.class);
                startActivity(intent);
                break;
            case R.id.volley_request_quque:
                intent.setClass(this, RequestQueueActivity.class);
                startActivity(intent);
                break;
            case R.id.volley_1:
                intent.setClass(this, SingletonQueueActivity.class);
                startActivity(intent);
                break;
            case R.id.volley_2:
                break;
        }
    }
}
