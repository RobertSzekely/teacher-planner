package com.example.robertszekely.teacherplanner.Domain;

import android.provider.ContactsContract;

import com.firebase.ui.auth.ui.User;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Teacher extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;
    private String password;
    private String email;
    private RealmList<Student> students; //one-to-many relationship
}
