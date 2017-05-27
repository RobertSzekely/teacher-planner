package com.example.robertszekely.teacherplanner.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by robertszekely on 27/05/2017.
 */

@IgnoreExtraProperties
public class User {
    public String username;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
