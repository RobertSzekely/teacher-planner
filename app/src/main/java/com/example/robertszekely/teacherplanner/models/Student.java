package com.example.robertszekely.teacherplanner.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor

public class Student implements Serializable{
    public String uid;
    private String name;
    private String email;
    private String image;
    private String password;
    private String teacherId;
    private float progress;

    public Student(String uid, String name, String email, String password, String teacherId, float progress) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.teacherId = teacherId;
        this.progress = progress;
    }
}
