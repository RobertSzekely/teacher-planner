package com.example.robertszekely.teacherplanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Student;
import com.google.firebase.auth.FirebaseAuth;
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

    private DatabaseReference mStudentReference;

    @BindView(R.id.details_student_first_name)
    TextView mFirstNameView;
    @BindView(R.id.details_student_last_name)
    TextView mLastNameView;
    @BindView(R.id.details_student_email)
    TextView mEmailView;
    @BindView(R.id.details_student_phone_number)
    TextView mPhoneNumberView;
    @BindView(R.id.details_student_group)
    TextView mGroupView;
    @BindView(R.id.details_student_progress_bar)
    ProgressBar mProgressView;

    private String studentKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        ButterKnife.bind(this);

        //Reference to the current user's student list
        mStudentReference = FirebaseDatabase.getInstance().getReference().child("user-students").child(getUid());

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        studentKey = (String) getIntent().getExtras().getSerializable(EXTRA_STUDENT_KEY);
        Log.d(TAG, "Received student key: " + studentKey);
        if (studentKey != null) {
            Log.d(TAG, "Received student key: " + studentKey);
//            Query currentStudentQuerry = FirebaseDatabase.getInstance().getReference().child("student").child(studentKey);
            Query currentStudentQuerry = mStudentReference.child(studentKey);
            currentStudentQuerry.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Student student = dataSnapshot.getValue(Student.class);
                    Log.d(TAG, String.valueOf(dataSnapshot.getKey()));
                    Log.d(TAG, "Queried student: " + student.toString());
                    Log.d(TAG, dataSnapshot.getValue(Student.class).toString());

                    mFirstNameView.setText(student.getFirstName());
                    mLastNameView.setText(student.getLastName());
                    mEmailView.setText(student.getEmail());
                    mPhoneNumberView.setText(student.getPhoneNumber());
                    mGroupView.setText(student.getGroup());
                    mProgressView.setProgress((int) student.getProgress());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    @OnClick(R.id.button_view_iterations)
    public void seeIterationsForCurrentStudent() {
        Log.d(TAG, "Clicked iterations button");
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_STUDENT_KEY, studentKey);
        navigateToActivity(IterationListActivity.class, bundle);
    }

    @OnClick(R.id.button_view_meetings)
    public void viewMeetingsForCurrentStudent() {
        Log.d(TAG, "Clicked add meeting button");
        //TODO
    }

    @OnClick(R.id.button_img_send_email)
    public void emailStudent() {
        //TODO
    }

    @OnClick(R.id.button_img_call)
    public void callStudent() {
        //TODO
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


}
