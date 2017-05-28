package com.example.robertszekely.teacherplanner.activity;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Feature;
import com.example.robertszekely.teacherplanner.models.Iteration;
import com.example.robertszekely.teacherplanner.models.Task;
import com.example.robertszekely.teacherplanner.models.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class BaseActivity  extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mStudentReference = mRootRef.child("student");
    DatabaseReference mTeacherReference = mRootRef.child("teacher");
    DatabaseReference mIterationReference = mRootRef.child("iteration");
    DatabaseReference mFeatureReference = mRootRef.child("feature");
    DatabaseReference mTaskReference = mRootRef.child("task");

    protected static final String STUDENT_BUNDLE_KEY = "student_bundle_key";
    protected static final String ITERAION_BUNDLE_KEY = "iteration_bundle_key";
    protected static final String FEATURE_BUNDLE_KEY = "feature_bundle_key";
    protected static final String TASK_BUNDLE_KEY = "task_bundle_key";

    protected static final String TEACHER_ID = "teacherId";
    protected static final String ITERATION_ID = "iterationId";
    protected static final String FEATURE_ID = "featureId";
    protected static final String TASK_ID = "taskId";

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
        return UUID.randomUUID().toString();
    }

    public static int randInt(int min, int max) {

        return ThreadLocalRandom.current().nextInt(min, max +1);
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

    public void addTeacher() {
        Teacher teacher1 = new Teacher();
        teacher1.setTeacherId(generateId());
        teacher1.setEmail("teacher@scs.ubbcluj.ro");
        teacher1.setName("Teacher 1");
        teacher1.setPassword("password");
//        Student student1 = new Student();
//        student1.setUid(generateId());
//        student1.setName("Student 1");
//        student1.setEmail("student@scs.ubbcluj.ro");
//        student1.setPassword("password");
//        student1.setProgress(35f);
//        student1.setTeacherId(teacher1.getTeacherId());
//        Student student2 = new Student(generateId(), "Student2", "student2@scs.com", "password", teacher1.getTeacherId(), 30f);
//        Student student3 = new Student(generateId(), "Student3", "student3@scs.com", "password", teacher1.getTeacherId(), 10f);
//        Student student4 = new Student(generateId(), "Student4", "student4@scs.com", "password", teacher1.getTeacherId(), 32.7f);
//        Student student5 = new Student(generateId(), "Student5", "student5@scs.com", "password", teacher1.getTeacherId(), 99f);
//        Student student6 = new Student(generateId(), "Student6", "student6@scs.com", "password", teacher1.getTeacherId(), 100f);
//        Student student7 = new Student(generateId(), "Student7", "student7@scs.com", "password", teacher1.getTeacherId(), 25.3f);
//        mStudentReference.child(student1.getUid()).setValue(student1);
//        mStudentReference.child(student2.getUid()).setValue(student2);
//        mStudentReference.child(student3.getUid()).setValue(student3);
//        mStudentReference.child(student4.getUid()).setValue(student4);
//        mStudentReference.child(student5.getUid()).setValue(student5);
//        mStudentReference.child(student6.getUid()).setValue(student6);
//        mStudentReference.child(student7.getUid()).setValue(student7);
//        mTeacherReference.child(teacher1.getTeacherId()).setValue(teacher1);

    }
//    public void addIteration() {
//        Iteration iteration1 = new Iteration(generateId(), "Iteration1", "Description of iteration one from the iteration list", "2757cc9b-176a-4e01-ae85-79d5184eaa65", false);
//        Iteration iteration2 = new Iteration(generateId(), "Iteration2", "Description of iteration two from the iteration list", "2757cc9b-176a-4e01-ae85-79d5184eaa65", false);
//        Iteration iteration3 = new Iteration(generateId(), "Iteration3", "Description of iteration three from the iteration list", "2757cc9b-176a-4e01-ae85-79d5184eaa65", false);
//        Iteration iteration4 = new Iteration(generateId(), "Iteration4", "Description of iteration four from the iteration list","2757cc9b-176a-4e01-ae85-79d5184eaa65", false);
//        mIterationReference.child(iteration1.getIterationId()).setValue(iteration1);
//        mIterationReference.child(iteration2.getIterationId()).setValue(iteration2);
//        mIterationReference.child(iteration3.getIterationId()).setValue(iteration3);
//        mIterationReference.child(iteration4.getIterationId()).setValue(iteration4);
//    }

    public void addFeatures() {
        Feature feature1 = new Feature(generateId(), "Feature1" , "Description of iteration one from the iteration list", "4472ab46-c308-40d9-ab76-98d51d8aa53c", false, 0);
        Feature feature2 = new Feature(generateId(), "Feature2" , "Description of iteration two from the iteration list", "4472ab46-c308-40d9-ab76-98d51d8aa53c", false, 0);
        Feature feature3 = new Feature(generateId(), "Feature3" , "Description of iteration three from the iteration list", "4472ab46-c308-40d9-ab76-98d51d8aa53c", false, 0);
        Feature feature4 = new Feature(generateId(), "Feature4" , "Description of iteration four from the iteration list", "4472ab46-c308-40d9-ab76-98d51d8aa53c", false, 0);
        Feature feature5 = new Feature(generateId(), "Feature1" , "Description of iteration one from the iteration list", "2757cc9b-176a-4e01-ae85-79d5184eaa65", false, 0);
        Feature feature6 = new Feature(generateId(), "Feature2" , "Description of iteration two from the iteration list", "2757cc9b-176a-4e01-ae85-79d5184eaa65", false, 0);
        mFeatureReference.child(feature1.getFeatureId()).setValue(feature1);
        mFeatureReference.child(feature2.getFeatureId()).setValue(feature2);
        mFeatureReference.child(feature3.getFeatureId()).setValue(feature3);
        mFeatureReference.child(feature4.getFeatureId()).setValue(feature4);
        mFeatureReference.child(feature5.getFeatureId()).setValue(feature5);
        mFeatureReference.child(feature6.getFeatureId()).setValue(feature6);
    }

    public void addTasks() {
        Task task1 = new Task(generateId(), "Task1", "Description of task 1", "4aab5372-6988-41ce-bb3b-6e6c74f47c16", false);
        Task task2 = new Task(generateId(), "Task2", "Description of task 2", "4aab5372-6988-41ce-bb3b-6e6c74f47c16", false);
        Task task3 = new Task(generateId(), "Task3", "Description of task 3", "4aab5372-6988-41ce-bb3b-6e6c74f47c16", false);
        Task task4 = new Task(generateId(), "Task4", "Description of task 4", "4aab5372-6988-41ce-bb3b-6e6c74f47c16", false);
        Task task5 = new Task(generateId(), "Task5", "Description of task 5", "4aab5372-6988-41ce-bb3b-6e6c74f47c16", false);
        Task task6 = new Task(generateId(), "Task6", "Description of task 6", "4e6cd0f3-3f1a-4bb8-97db-f7bab49b9bfa", false);
        Task task7 = new Task(generateId(), "Task7", "Description of task 7", "4e6cd0f3-3f1a-4bb8-97db-f7bab49b9bfa", false);
        Task task8 = new Task(generateId(), "Task8", "Description of task 8", "4e6cd0f3-3f1a-4bb8-97db-f7bab49b9bfa", false);
        Task task9 = new Task(generateId(), "Task9", "Description of task 9", "4e6cd0f3-3f1a-4bb8-97db-f7bab49b9bfa", false);
        Task task10 = new Task(generateId(), "Task10", "Description of task 10", "4e6cd0f3-3f1a-4bb8-97db-f7bab49b9bfa", false);
        Task task11 = new Task(generateId(), "Task11", "Description of task 11", "4e6cd0f3-3f1a-4bb8-97db-f7bab49b9bfa", false);
        mTaskReference.child(task1.getTaskId()).setValue(task1);
        mTaskReference.child(task2.getTaskId()).setValue(task2);
        mTaskReference.child(task3.getTaskId()).setValue(task3);
        mTaskReference.child(task4.getTaskId()).setValue(task4);
        mTaskReference.child(task5.getTaskId()).setValue(task5);
        mTaskReference.child(task6.getTaskId()).setValue(task6);
        mTaskReference.child(task7.getTaskId()).setValue(task7);
        mTaskReference.child(task8.getTaskId()).setValue(task8);
        mTaskReference.child(task9.getTaskId()).setValue(task9);
        mTaskReference.child(task10.getTaskId()).setValue(task10);
        mTaskReference.child(task11.getTaskId()).setValue(task11);

    }

}
