package com.example.robertszekely.teacherplanner.models;


import java.io.Serializable;

import lombok.*;

public class Feature {
    private String body;
    private String iterationId;
    private double progress = 0;

    public Feature() {
    }

    public Feature(String body, String iterationId) {
        this.body = body;
        this.iterationId = iterationId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getIterationId() {
        return iterationId;
    }

    public void setIterationId(String iterationId) {
        this.iterationId = iterationId;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }
}
