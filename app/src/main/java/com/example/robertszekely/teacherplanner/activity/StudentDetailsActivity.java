package com.example.robertszekely.teacherplanner.activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Student;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentDetailsActivity extends BaseActivity {

    private static final String TAG = StudentDetailsActivity.class.getSimpleName();

    @BindView(R.id.nameTextView) TextView mNameTextView;
    @BindView(R.id.emailTextView) TextView mEmailTextView;
    @BindView(R.id.phoneNumberTextView) TextView mPhoneTextView;
    @BindView(R.id.progressTextView) TextView mProgressTextView;

    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        student = (Student) getIntent().getExtras().getSerializable(STUDENT_BUNDLE_KEY);

        if(student != null) {
            mNameTextView.setText(student.getName());
            mEmailTextView.setText(student.getEmail());
            mPhoneTextView.setText("to do");
            mProgressTextView.setText(fmt(student.getProgress()));
        }
    }

    @OnClick(R.id.iterationsButton)
    public void seeIterationsForCurrentStudent() {
        Log.d(TAG, "Clicked iterations button");
        Bundle bundle = new Bundle();
        bundle.putSerializable(STUDENT_BUNDLE_KEY, student);
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
