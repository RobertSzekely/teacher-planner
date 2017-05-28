package com.example.robertszekely.teacherplanner.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Iteration;
import com.example.robertszekely.teacherplanner.models.Student;
import com.example.robertszekely.teacherplanner.models.User;
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
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewIterationActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = NewIterationActivity.class.getSimpleName();

    public static final String EXTRA_STUDENT_KEY = "student_key";

    private static final String REQUIRED = "Required";

    private DatabaseReference mDatabase;

    private String studentKey;

    @BindView(R.id.field_title)
    EditText mTitleField;
    @BindView(R.id.field_deadline)
    TextView mDeadlineField;
    @BindView(R.id.field_body)
    EditText mBodyField;
    @BindView(R.id.fab_submit_new_iteration)
    FloatingActionButton mSubmitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_iteration);
        ButterKnife.bind(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitIteration();
            }
        });
    }

    private void submitIteration() {
        final String title = mTitleField.getText().toString();
        final String body = mBodyField.getText().toString();
        final String deadline = mDeadlineField.getText().toString();

        //Title is required
        if (TextUtils.isEmpty(title)) {
            mTitleField.setError(REQUIRED);
            return;
        }
        //Body is required
        if (TextUtils.isEmpty(body)) {
            mBodyField.setError(REQUIRED);
            return;
        }
        //Deadline is required
        if (TextUtils.isEmpty(deadline)) {
            Toast.makeText(this, "Must pick deadline!", Toast.LENGTH_SHORT).show();
            return;
        }
        //Disable button so there are no multi-iteration-submits
        setEditingEnabled(false);
        Toast.makeText(this, "Adding iteration...", Toast.LENGTH_SHORT).show();

        final String userId = getUid();
        final String studentId = (String) getIntent().getExtras().getSerializable(EXTRA_STUDENT_KEY);
        mDatabase.child("students").child(studentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get student value
                Student student = dataSnapshot.getValue(Student.class);

                if(student == null) {
                    //User is null, error out
                    Log.e(TAG, "Student: " + studentId + "is unexpectedly null");
                    Toast.makeText(NewIterationActivity.this, "Error: could not fetch user.", Toast.LENGTH_SHORT).show();
                } else {
                    //Write new iteration
                    writeNewIteration(studentId, title, body, deadline);
                }
                //Finishing this Activity, back to iteration list
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

    private void writeNewIteration(String studentId, String title, String body, String deadline) {
        // Create new iteration at /student-iteration/$studentid/$iterationid and at
        // /iterations/$iterationid simultaneously
        String key = mDatabase.child("iterations").push().getKey();
        Iteration iteration = new Iteration(studentId, title, body, deadline);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/iterations/" + key, iteration);
        childUpdates.put("/student-iterations/" + studentId + "/" + key, iteration);

        mDatabase.updateChildren(childUpdates);
    }

    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
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

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "date");
    }

    /**
     * To set date on TextView
     *
     * @param calendar
     */
    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        mDeadlineField.setText(dateFormat.format(calendar.getTime()));

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
