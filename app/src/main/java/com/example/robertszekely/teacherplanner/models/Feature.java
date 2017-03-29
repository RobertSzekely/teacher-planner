package com.example.robertszekely.teacherplanner.models;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor

public class Feature {
    private String featureId;
    private String content;
    private String iterationId;
    private boolean completed;

}
