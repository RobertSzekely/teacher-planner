package com.example.robertszekely.teacherplanner.activity;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Student;
import com.example.robertszekely.teacherplanner.viewholder.StudentViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentListActivity extends BaseActivity {

    private static String TAG = StudentListActivity.class.getSimpleName();

    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Student, StudentViewHolder> mAdapter;
    private LinearLayoutManager mManager;

    @BindView(R.id.studentRecyclerView)
    RecyclerView mRecycler;
    @BindView(R.id.fab_new_student)
    FloatingActionButton addStudentBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        ButterKnife.bind(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mManager);
        mRecycler.setHasFixedSize(true);

        //results students for the current user
        Query studentQuery = mDatabase.child("user-students").child(getUid());

        mAdapter = new FirebaseRecyclerAdapter<Student, StudentViewHolder>(
                Student.class,
                R.layout.row_student_without_profile_pic,
                StudentViewHolder.class,
                studentQuery) {


            @Override
            protected void populateViewHolder(StudentViewHolder viewHolder, final Student model, int position) {

                final DatabaseReference studentRef = getRef(position);

                //Set click listener for the whole student view
                final String studentKey = studentRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Launch StudentDetailActivity
                        Intent intent = new Intent(StudentListActivity.this, StudentDetailsActivity.class);
                        intent.putExtra(StudentDetailsActivity.EXTRA_STUDENT_KEY, studentKey);
                        Log.d(TAG, "Sent student key: " + studentKey);
                        startActivity(intent);
                    }
                });

                //Bind Student to ViewHolder
                viewHolder.bindToStudent(model);
            }
        };

        mRecycler.setAdapter(mAdapter);

        addStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Launch NewStudentActivity
                startActivity(new Intent(StudentListActivity.this, NewStudentActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if(i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
