package com.example.my.fastjson;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class DeserialTest {
    private String code;
    private String city;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
