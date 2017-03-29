package com.example.robertszekely.teacherplanner.fragment;

import android.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class BaseFragment extends Fragment {
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mStudentReference = mRootRef.child("student");
    DatabaseReference mTeacherReference = mRootRef.child("teacher");
    DatabaseReference mIterationReference = mRootRef.child("iteration");
}
