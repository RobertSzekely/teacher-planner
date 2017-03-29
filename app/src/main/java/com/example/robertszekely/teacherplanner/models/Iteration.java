package com.example.robertszekely.teacherplanner.models;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor

public class Iteration {
    private String iterationId;
    private String content;
    private String studentId;
    private boolean completed;

    public Iteration(String iterationId, String content, String studentId, boolean completed) {
        this.iterationId = iterationId;
        this.content = content;
        this.studentId = studentId;
        this.completed = completed;
    }
}
