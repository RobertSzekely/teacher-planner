package com.example.robertszekely.teacherplanner.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor

public class Iteration implements Serializable {
    private String iterationId;
    private String iterationName;
    private String content;
    private String studentId;
    private float progress;
    private boolean completed;

    public Iteration(String iterationId, String iterationName, String content, String studentId, boolean completed) {
        this.iterationId = iterationId;
        this.iterationName = iterationName;
        this.content = content;
        this.studentId = studentId;
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Iteration{" +
                "iterationId='" + iterationId + '\'' +
                ", iterationName='" + iterationName + '\'' +
                '}';
    }
}
