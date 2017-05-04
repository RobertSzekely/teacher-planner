package com.example.robertszekely.teacherplanner.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.fragment.StudentListFragment;
import com.example.robertszekely.teacherplanner.models.Feature;
import com.example.robertszekely.teacherplanner.models.Iteration;
import com.example.robertszekely.teacherplanner.models.Student;
import com.example.robertszekely.teacherplanner.models.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class BaseActivity  extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mStudentReference = mRootRef.child("student");
    DatabaseReference mTeacherReference = mRootRef.child("teacher");
    DatabaseReference mIterationReference = mRootRef.child("iteration");
    DatabaseReference mFeatureReference = mRootRef.child("feature");

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public String generateId() {
        String uniqueID = UUID.randomUUID().toString();
        return uniqueID;
    }

    public static String fmt(double d)
    {
        return String.format("%d",(long)d);
    }

    public void navigateToActivity(Class activityClass, Bundle arguments) {
        Intent intent = new Intent(this, activityClass);
        if (arguments != null) {
            intent.putExtras(arguments);
        }
        startActivity(intent);
    }

    public void navigateToFragment(Fragment fragmentClass) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragmentClass)
                .addToBackStack(null)
                .commit();
    }

    public void navigateToStudentListFragment() {
        StudentListFragment studentListFragment = new StudentListFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.
                replace(R.id.container, studentListFragment).
                addToBackStack(null).
                commit();
    }



//    private void navigateToStudentFragment(Class fragmentClass, Bundle argumets) {
//        Fragment fr = new fragmentClass;
//        fr.setArguments(args);
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_place, fr);
//        fragmentTransaction.commit();
//
//    }
//
//    public void navigateTo(Class fragmentClass, Bundle arguments) {
//
//        try {
//            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//            fragmentTransaction.replace(getContainerId(), currentFragment);
//            fragmentTransaction.commitAllowingStateLoss();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void addTeacher() {
        Teacher teacher1 = new Teacher();
        teacher1.setTeacherId(generateId());
        teacher1.setEmail("teacher@scs.ubbcluj.ro");
        teacher1.setName("Teacher 1");
        teacher1.setPassword("password");
        Student student1 = new Student();
        student1.setUid(generateId());
        student1.setName("Student 1");
        student1.setEmail("student@scs.ubbcluj.ro");
        student1.setPassword("password");
        student1.setProgress(35f);
        student1.setTeacherId(teacher1.getTeacherId());
        Student student2 = new Student(generateId(), "Student2", "student2@scs.com", "password", teacher1.getTeacherId(), 30f);
        Student student3 = new Student(generateId(), "Student3", "student3@scs.com", "password", teacher1.getTeacherId(), 10f);
        Student student4 = new Student(generateId(), "Student4", "student4@scs.com", "password", teacher1.getTeacherId(), 32.7f);
        Student student5 = new Student(generateId(), "Student5", "student5@scs.com", "password", teacher1.getTeacherId(), 99f);
        Student student6 = new Student(generateId(), "Student6", "student6@scs.com", "password", teacher1.getTeacherId(), 100f);
        Student student7 = new Student(generateId(), "Student7", "student7@scs.com", "password", teacher1.getTeacherId(), 25.3f);
        mStudentReference.child(student1.getUid()).setValue(student1);
        mStudentReference.child(student2.getUid()).setValue(student2);
        mStudentReference.child(student3.getUid()).setValue(student3);
        mStudentReference.child(student4.getUid()).setValue(student4);
        mStudentReference.child(student5.getUid()).setValue(student5);
        mStudentReference.child(student6.getUid()).setValue(student6);
        mStudentReference.child(student7.getUid()).setValue(student7);
        mTeacherReference.child(teacher1.getTeacherId()).setValue(teacher1);

    }
    public void addIteration() {
        Iteration iteration1 = new Iteration(generateId(), "Iteration1", "Description of iteration one from the iteration list", "2757cc9b-176a-4e01-ae85-79d5184eaa65", false);
        Iteration iteration2 = new Iteration(generateId(), "Iteration2", "Description of iteration two from the iteration list", "2757cc9b-176a-4e01-ae85-79d5184eaa65", false);
        Iteration iteration3 = new Iteration(generateId(), "Iteration3", "Description of iteration three from the iteration list", "2757cc9b-176a-4e01-ae85-79d5184eaa65", false);
        Iteration iteration4 = new Iteration(generateId(), "Iteration4", "Description of iteration four from the iteration list","2757cc9b-176a-4e01-ae85-79d5184eaa65", false);
        mIterationReference.child(iteration1.getIterationId()).setValue(iteration1);
        mIterationReference.child(iteration2.getIterationId()).setValue(iteration2);
        mIterationReference.child(iteration3.getIterationId()).setValue(iteration3);
        mIterationReference.child(iteration4.getIterationId()).setValue(iteration4);
    }

    public void addFeatures() {
        Feature feature1 = new Feature(generateId(), "Feature1" , "Description of iteration one from the iteration list", "4472ab46-c308-40d9-ab76-98d51d8aa53c", false);
        Feature feature2 = new Feature(generateId(), "Feature2" , "Description of iteration two from the iteration list", "4472ab46-c308-40d9-ab76-98d51d8aa53c", false);
        Feature feature3 = new Feature(generateId(), "Feature3" , "Description of iteration three from the iteration list", "4472ab46-c308-40d9-ab76-98d51d8aa53c", false);
        Feature feature4 = new Feature(generateId(), "Feature4" , "Description of iteration four from the iteration list", "4472ab46-c308-40d9-ab76-98d51d8aa53c", false);
        Feature feature5 = new Feature(generateId(), "Feature1" , "Description of iteration one from the iteration list", "2757cc9b-176a-4e01-ae85-79d5184eaa65", false);
        Feature feature6 = new Feature(generateId(), "Feature2" , "Description of iteration two from the iteration list", "2757cc9b-176a-4e01-ae85-79d5184eaa65", false);
        mFeatureReference.child(feature1.getFeatureId()).setValue(feature1);
        mFeatureReference.child(feature2.getFeatureId()).setValue(feature2);
        mFeatureReference.child(feature3.getFeatureId()).setValue(feature3);
        mFeatureReference.child(feature4.getFeatureId()).setValue(feature4);
        mFeatureReference.child(feature5.getFeatureId()).setValue(feature5);
        mFeatureReference.child(feature6.getFeatureId()).setValue(feature6);
    }

}
