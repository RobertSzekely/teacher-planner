package com.example.robertszekely.teacherplanner.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class Task {
    private String featureId;
    private String body;
    private boolean completed = false;

    public Task() {
    }

    public Task(String featureId, String body) {
        this.featureId = featureId;
        this.body = body;
    }

    public String getFeatureId() {
        return featureId;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
