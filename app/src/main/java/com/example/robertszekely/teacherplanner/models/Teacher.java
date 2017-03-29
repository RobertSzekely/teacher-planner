package com.example.robertszekely.teacherplanner.models;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor

public class Teacher {
    private String teacherId;
    private String name;
    private String password;
    private String email;
}
