package com.example.robertszekely.teacherplanner.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Meeting;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_STUDENT_KEY = "student_key";
    public static final String EXTRA_MEETING_KEY = "meeting_key";

    private DatabaseReference mMeetingReference;

    private String studentKey;
    private String meetingKey;

    @BindView(R.id.meeting_details_student_first_name_view)
    TextView mFirstNameView;
    @BindView(R.id.meeting_details_student_last_name_view)
    TextView mLastNameView;
    @BindView(R.id.meeting_details_student_progress_view)
    TextView mProgressView;
    @BindView(R.id.meeting_details_date_view)
    TextView mDateView;
    @BindView(R.id.meeting_detaisl_body_view)
    TextView mBodyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_details);
        ButterKnife.bind(this);

        studentKey = (String) getIntent().getExtras().getSerializable(EXTRA_STUDENT_KEY);
        meetingKey = (String) getIntent().getExtras().getSerializable(EXTRA_MEETING_KEY);

        mMeetingReference = FirebaseDatabase.getInstance().getReference().child("student-meetings").child(studentKey);


        Query currentMeetingQuery = mMeetingReference.child(meetingKey);
        currentMeetingQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Meeting meeting = dataSnapshot.getValue(Meeting.class);

                mFirstNameView.setText(meeting.getStudentFirstName());
                mLastNameView.setText(meeting.getStudentLastName());
                mProgressView.setText(meeting.getStudentProgress());
                mDateView.setText(meeting.getDate());
                mBodyView.setText(meeting.getBody());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
