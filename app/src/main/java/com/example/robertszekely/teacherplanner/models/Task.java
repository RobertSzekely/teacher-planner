package com.example.robertszekely.teacherplanner.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Task {
    private String taskName;
    private String taskId;
    private String content;
    private boolean completed;
    private String featureId;

    public Task(String taskId, String taskName, String content, String featureId, boolean completed) {
        this.taskName = taskName;
        this.taskId = taskId;
        this.content = content;
        this.completed = completed;
        this.featureId = featureId;
    }
}
