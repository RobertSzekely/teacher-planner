package com.example.robertszekely.teacherplanner.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Student;

public class StudentDetailsActivity extends BaseActivity {

    private static final String TAG = StudentDetailsActivity.class.getSimpleName();

    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        student = getIntent().getExtras().getParcelable("student");
        if(student != null) {
            //TODO chage with data binding library
            setTextViews();

            setButtons();
        }


    }

    public void setTextViews() {
        TextView mStudentNameTextView = (TextView) findViewById(R.id.nameTextView);
        mStudentNameTextView.setText(student.getName());
        TextView mStudentEmailTextView = (TextView) findViewById(R.id.emailTextView);
        mStudentEmailTextView.setText(student.getEmail());
        TextView mStudentPhoneTextView = (TextView) findViewById(R.id.phoneNumberTextView);
        mStudentPhoneTextView.setText("to do");
        TextView mStudentProgressTextView = (TextView) findViewById(R.id.progressTextView);
        mStudentProgressTextView.setText(fmt(student.getProgress()));
    }

    public void setButtons() {
        Button mAddMeetingButton = (Button) findViewById(R.id.addMeetingButton);
        mAddMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked add meeting button");
                //TODO
            }
        });
        Button mIterationsButton = (Button) findViewById(R.id.iterationsButton);
        mIterationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked iterations button");
                //TODO
            }
        });
        Button mMeetingsButton = (Button) findViewById(R.id.meetingsButton);
        mMeetingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked meetings button");
                //TODO
            }
        });

    }

}
