package com.example.robertszekely.teacherplanner.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Task {
    private String taskId;
    private String content;
    private boolean completed;
    private String featureId;
}
