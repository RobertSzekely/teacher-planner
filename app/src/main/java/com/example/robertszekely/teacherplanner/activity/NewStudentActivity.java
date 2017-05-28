package com.example.robertszekely.teacherplanner.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Student;
import com.example.robertszekely.teacherplanner.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewStudentActivity extends BaseActivity {

    private static final String TAG = NewStudentActivity.class.getSimpleName();
    private static final String REQUIRED = "Required";

    private DatabaseReference mDatabase;

    @BindView(R.id.field_name)
    EditText mNameField;
    @BindView(R.id.field_email)
    EditText mEmailField;
    @BindView(R.id.field_phone_number)
    EditText mPhoneNumberField;
    @BindView(R.id.field_group)
    EditText mGroupField;
    @BindView(R.id.fab_submit_new_student)
    FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);
        ButterKnife.bind(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitStudent();
            }
        });
    }

    private void submitStudent() {
        final String name = mNameField.getText().toString();
        final String email = mEmailField.getText().toString();
        final String phoneNumber = mPhoneNumberField.getText().toString();
        final String group = mGroupField.getText().toString();

        //Name is required
        if (TextUtils.isEmpty(name)) {
            mNameField.setError(REQUIRED);
            return;
        }
        //Email is required
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError(REQUIRED);
            return;
        }
        //phoneNumber is required
        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberField.setError(REQUIRED);
            return;
        }
        //group is required
        if (TextUtils.isEmpty(group)) {
            mGroupField.setError(REQUIRED);
            return;
        }

        //Disable button so there are no multi-student-submits
        setEditingEnabled(false);
        Toast.makeText(this, "Adding student...", Toast.LENGTH_SHORT).show();

        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get user value
                        User user = dataSnapshot.getValue(User.class);

                        if (user == null) {
                            //User is null, error out
                            Log.e(TAG, "User: " + userId + "is unexpectedly null");
                            Toast.makeText(NewStudentActivity.this, "Error: could not fetch user.", Toast.LENGTH_SHORT).show();

                        } else {
                            //Write new user
                            writeNewStudent(userId, name, email, phoneNumber, group);
                        }
                        //Finish this Activity, back to student list
                        setEditingEnabled(true);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCanceled", databaseError.toException());
                        setEditingEnabled(true);
                    }
                });
    }

    private void setEditingEnabled(boolean enabled) {
        mNameField.setEnabled(enabled);
        mEmailField.setEnabled(enabled);
        mPhoneNumberField.setEnabled(enabled);
        mGroupField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    private void writeNewStudent(String userId, String name, String email, String phoneNumber, String group) {
//        // Create new student at /user-student/$userid/$studentid
//        String key = mDatabase.child("user-students").child(userId).push().getKey();
//        Student student = new Student(userId, name, email, phoneNumber, group);
//        mDatabase.child("user-students").child(userId).child(key).setValue(student);

        // Create new student at /user-student/$userid/$studentid and at
        // /students/$studentid simultaneously
        String key = mDatabase.child("students").push().getKey();
        Student student = new Student(userId, name, email, phoneNumber, group);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/students/" + key, student);
        childUpdates.put("/user-students/" + userId + "/" + key, student);

        mDatabase.updateChildren(childUpdates);
    }

}
