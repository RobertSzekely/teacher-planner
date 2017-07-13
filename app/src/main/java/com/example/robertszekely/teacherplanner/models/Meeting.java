package com.example.robertszekely.teacherplanner.models;

/**
 * Created by robertszekely on 30/05/2017.
 */

public class Meeting {
    private String studentFirstName;
    private String studentLastName;
    private String date;
    private String studentProgress;
    private String body;

    public Meeting() {
    }

    public Meeting(String studentFirstName, String studentLastName, String date, String studentProgress, String body) {
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.date = date;
        this.studentProgress = studentProgress;
        this.body = body;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStudentProgress() {
        return studentProgress;
    }

    public void setStudentProgress(String studentProgress) {
        this.studentProgress = studentProgress;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
