package com.dotwait.excel.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用于制造假数据的User
 *
 * @author DotWait
 * @Date 2019-11-10
 */
@Data
public class User {
    private String name;
    private int age;
    private String job;
    private Date date;
    private long timestamp;
    private String data;

    public User() {
    }

    public User(String name, int age, String job, Date date, long timestamp, String data) {
        this.name = name;
        this.age = age;
        this.job = job;
        this.date = date;
        this.timestamp = timestamp;
        this.data = data;
    }
}
