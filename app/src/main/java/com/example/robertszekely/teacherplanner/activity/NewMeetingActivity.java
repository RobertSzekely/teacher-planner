package com.example.robertszekely.teacherplanner.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Meeting;
import com.example.robertszekely.teacherplanner.models.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewMeetingActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = NewMeetingActivity.class.getSimpleName();

    public static final String EXTRA_STUDENT_KEY = "student_key";

    private static final String REQUIRED = "Required";

    private DatabaseReference mDatabase;

    private String studentKey;

    @BindView(R.id.meeting_student_first_name_view)
    TextView mFirstNameView;
    @BindView(R.id.meeting_student_last_name_view)
    TextView mLastNameView;
    @BindView(R.id.meeting_date_view)
    TextView mDateView;
    @BindView(R.id.meeting_progress_view)
    TextView mProgressView;
    @BindView(R.id.field_meeting_body)
    TextView mBodyField;
    @BindView(R.id.fab_submit_new_meeting)
    FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);
        ButterKnife.bind(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final String studentId = (String) getIntent().getExtras().getSerializable(EXTRA_STUDENT_KEY);
        mDatabase.child("students").child(studentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get student value
                Student student = dataSnapshot.getValue(Student.class);

                if (student == null) {
                    //User is null, error out
                    Log.e(TAG, "Student: " + studentId + "is unexpectedly null");
                    Toast.makeText(NewMeetingActivity.this, "Error: could not fetch user.", Toast.LENGTH_SHORT).show();
                } else {
                    mLastNameView.setText(student.getLastName());
                    mFirstNameView.setText(student.getFirstName());
                    mProgressView.setText(String.valueOf((int) student.getProgress()));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getStudent:onCanceled", databaseError.toException());
                setEditingEnabled(true);
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitMeeting();
            }
        });
    }

    private void submitMeeting() {
        final String progress = mProgressView.getText().toString();
        final String date = mDateView.getText().toString();
        final String body = mBodyField.getText().toString();

        //Date is required
        if (TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Must pick date!", Toast.LENGTH_SHORT).show();
            return;
        }

        //Disable button so there are no multi-iteration-submits
        setEditingEnabled(false);
        Toast.makeText(this, "Adding meeting...", Toast.LENGTH_SHORT).show();

        final String userId = getUid();
        final String studentId = (String) getIntent().getExtras().getSerializable(EXTRA_STUDENT_KEY);
        mDatabase.child("students").child(studentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get student value
                Student student = dataSnapshot.getValue(Student.class);

                if (student == null) {
                    //User is null, error out
                    Log.e(TAG, "Student: " + studentId + "is unexpectedly null");
                    Toast.makeText(NewMeetingActivity.this, "Error: could not fetch user.", Toast.LENGTH_SHORT).show();
                } else {
                    //Write new meeting
                    writeNewMeeting(studentId, student.getFirstName(), student.getLastName(), date, body, progress);
                }
                //Finishing this Activity, back to meetings list
                setEditingEnabled(true);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getStudent:onCanceled", databaseError.toException());
                setEditingEnabled(true);
            }
        });

    }

    private void writeNewMeeting(String studentId, String studentFirstName, String studentLastName, String date, String body, String progress) {
        // Creates new meeting at /student-meetings/$studentid/$meetingid and at
        // /meetings/$meetingid simultaneously
        String key = mDatabase.child("meetings").push().getKey();
        Meeting meeting = new Meeting(studentId, studentFirstName, studentLastName, date, progress, body);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/meetings/" + key, meeting);
        childUpdates.put("/student-meetings/" + studentId + "/" + key, meeting);

        mDatabase.updateChildren(childUpdates);
    }

    private void setEditingEnabled(boolean enabled) {
        mBodyField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    /**
     * This callback method, call DatePickerFragment class,
     * DatePickerFragment class returns calendar view.
     *
     * @param view
     */
    public void datePicker(View view) {

        NewIterationActivity.DatePickerFragment fragment = new NewIterationActivity.DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "date");
    }

    /**
     * To set date on TextView
     *
     * @param calendar
     */
    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        mDateView.setText(dateFormat.format(calendar.getTime()));

    }

    /**
     * To receive a callback when the user sets the date.
     *
     * @param view
     * @param year
     * @param month
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        setDate(calendar);
    }

    /**
     * Create a DatePickerFragment class that extends DialogFragment.
     * Define the onCreateDialog() method to return an instance of DatePickerDialog
     */
    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);
        }

    }

}
