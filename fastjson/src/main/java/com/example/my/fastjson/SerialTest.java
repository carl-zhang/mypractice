package com.example.my.fastjson;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class SerialTest {
    private String name;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
