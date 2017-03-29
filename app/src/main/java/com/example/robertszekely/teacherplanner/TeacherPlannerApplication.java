package com.example.robertszekely.teacherplanner;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TeacherPlannerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        Realm.init(this);
//        RealmConfiguration config = new RealmConfiguration.Builder().build();
//        Realm.setDefaultConfiguration(config);
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    }

}
