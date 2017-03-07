package com.example.robertszekely.teacherplanner.Domain;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Student extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;
    private String email;

}
