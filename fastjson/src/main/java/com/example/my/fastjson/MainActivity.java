package com.example.my.fastjson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView serial;
    private TextView deserial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serial = findViewById(R.id.serial);
        deserial = findViewById(R.id.deserial);
    }

    @Override
    protected void onResume() {
        super.onResume();
        serialTest();
        deserialTest();
    }

    // 序列化测试
    private void serialTest() {
        SerialTest test = new SerialTest();
        test.setName("Morning");
        test.setDate(new Date());
        String testResult = JSON.toJSONString(test);
        serial.setText(testResult);
    }

    // 反序列化测试
    private void deserialTest() {
        String jsonString = "{\"date\":\"2018-08-17 14:38:38\",\"code\":11,\"city\":\"西安\"}";
        DeserialTest test = JSON.parseObject(jsonString, DeserialTest.class);
        deserial.setText(new StringBuilder()
                .append("date:").append(test.getDate()).append("\n")
                .append("code:").append(test.getCode()).append("\n")
                .append("name:").append(test.getCity()).toString());
    }
}
