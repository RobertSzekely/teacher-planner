package com.example.robertszekely.teacherplanner.models;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

import lombok.*;


@IgnoreExtraProperties

public class Iteration {

    private String title;
    private String body;
    private double progress = 0;
    private Date deadline;
//    private final String status;

    public Iteration() {
        //Default constructor required for calls to DataSnapshot.getValue(Iteration.class)
    }

    public Iteration(String title, String body, Date deadline) {
        this.title = title;
        this.body = body;
        this.deadline = deadline;
//        this.status = status;
    }


//    private static final String STATUS_OPEN = "Open";
//    private static final String STATUS_INPROGRESS = "Inprogress";
//    private static final String STATUS_CLOSED = "Closed";
//
//    @Retention(RetentionPolicy.SOURCE)
//    @StringDef({STATUS_OPEN, STATUS_INPROGRESS, STATUS_CLOSED})
//    public @interface IterationStatusDef{ }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
