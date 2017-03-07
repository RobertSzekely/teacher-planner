package com.example.robertszekely.teacherplanner.Domain;

import org.joda.time.LocalDate;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Note extends RealmObject {
    @PrimaryKey
    private long id;
    private String content;
    private String date;

    public Note(long id, String content, String date) {
        this.id = id;
        this.content = content;
        this.date = date;
    }

    public Note() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
