package com.example.robertszekely.teacherplanner.models;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor

public class Feature {
    private String featureId;
    private String featureName;
    private String content;
    private String iterationId;
    private boolean completed;

    public Feature(String featureId, String featureName, String content, String iterationId, boolean completed) {
        this.featureId = featureId;
        this.featureName = featureName;
        this.content = content;
        this.iterationId = iterationId;
        this.completed = completed;
    }
}
