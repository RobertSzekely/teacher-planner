package com.example.robertszekely.teacherplanner.activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentDetailsActivity extends BaseActivity {

    private static final String TAG = StudentDetailsActivity.class.getSimpleName();

    public static final String EXTRA_STUDENT_KEY = "student_key";

    @BindView(R.id.nameTextView) TextView mNameTextView;
    @BindView(R.id.emailTextView) TextView mEmailTextView;
    @BindView(R.id.phoneNumberTextView) TextView mPhoneTextView;
    @BindView(R.id.progressTextView) TextView mProgressTextView;

    private String studentKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        studentKey = (String) getIntent().getExtras().getSerializable(STUDENT_BUNDLE_KEY);

        if(studentKey != null) {
            Log.d(TAG, "Received student key: " + studentKey);
            Query currentStudentQuerry = FirebaseDatabase.getInstance().getReference().child("student").child(studentKey);
            currentStudentQuerry.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Student student = dataSnapshot.getValue(Student.class);
                    Log.d(TAG, String.valueOf(dataSnapshot.getKey()));
                    Log.d(TAG, "Queried student: " + student.toString());
                    Log.d(TAG, dataSnapshot.getValue(Student.class).toString());

                    mNameTextView.setText(student.getName());
                    mEmailTextView.setText(student.getEmail());
                    mPhoneTextView.setText("to do");
                    mProgressTextView.setText(fmt(student.getProgress()));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    @OnClick(R.id.iterationsButton)
    public void seeIterationsForCurrentStudent() {
        Log.d(TAG, "Clicked iterations button");
        Bundle bundle = new Bundle();
        bundle.putSerializable(STUDENT_BUNDLE_KEY, studentKey);
        navigateToActivity(IterationListActivity.class, bundle);
    }

    @OnClick(R.id.addMeetingButton)
    public void addNewMeeting() {
        Log.d(TAG, "Clicked add meeting button");
        //TODO
    }

    @OnClick(R.id.meetingsButton)
    public void seeMeetingsForCurrentStudent() {
        Log.d(TAG, "Clicked meetings button");
        //TODO
    }


}
