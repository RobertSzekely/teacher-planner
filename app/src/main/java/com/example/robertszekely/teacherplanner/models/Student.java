package com.example.robertszekely.teacherplanner.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.*;


public class Student {
    private String phoneNumber;
    private String email;
    private String name;
    private String password;
    private double progress;
    private String teacherId;
    private String uid;

    public Student() {
    }

    public Student(String email, String name, String password, double progress, String teacherId, String uid) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.progress = progress;
        this.teacherId = teacherId;
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
