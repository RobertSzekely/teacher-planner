package com.example.robertszekely.teacherplanner.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor

public class Student implements Parcelable{
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

    protected Student(Parcel in) {
        uid = in.readString();
        name = in.readString();
        email = in.readString();
        image = in.readString();
        password = in.readString();
        teacherId = in.readString();
        progress = in.readFloat();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(image);
        dest.writeString(password);
        dest.writeString(teacherId);
        dest.writeFloat(progress);
    }
}
