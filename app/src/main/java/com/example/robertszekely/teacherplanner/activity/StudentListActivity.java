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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentListActivity extends BaseActivity {

    private static String TAG = StudentListActivity.class.getSimpleName();

    private RecyclerView studentListRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_list);

        setStudentRecyclerView();
        setStudentAdapter();

    }

    public void setStudentRecyclerView() {
        studentListRecyclerView = (RecyclerView) findViewById(R.id.studentRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        studentListRecyclerView.setLayoutManager(linearLayoutManager);
        studentListRecyclerView.setHasFixedSize(true);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewStudent();
            }
        });

//        list item decorator
//        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
//        Drawable verticalDivider = ContextCompat.getDrawable(this, R.drawable.horizontal_divider);
//        mDividerItemDecoration.setDrawable(verticalDivider);
//        studentListRecyclerView.addItemDecoration(mDividerItemDecoration);
    }

    public void setStudentAdapter() {
        Query studentAlphabeticalOrderQuery = mStudentReference.orderByChild("name");
        FirebaseRecyclerAdapter<Student, StudentViewHolder> firebaseStudentRecyclerAdapter = new FirebaseRecyclerAdapter<Student, StudentViewHolder>(
            Student.class,
            R.layout.row_student_without_profile_pic,
            StudentViewHolder.class,
            studentAlphabeticalOrderQuery) {


        @Override
        protected void populateViewHolder(StudentViewHolder viewHolder, final Student model, int position) {

            viewHolder.setStudentName(model.getName());
            viewHolder.setStundetEmail(model.getEmail());

            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "You clicked on " + model.getName());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(STUDENT_BUNDLE_KEY, model.getUid());
                    navigateToActivity(StudentDetailsActivity.class, bundle);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    Log.d(TAG, "Student sent:" + model.toString());
                }
            });
        }
    };

        studentListRecyclerView.setAdapter(firebaseStudentRecyclerAdapter);

    }

    private void addNewStudent() {
        startActivity(new Intent(StudentListActivity.this, NewStudentActivity.class));
    }


    public static class StudentViewHolder extends RecyclerView.ViewHolder {

        View mView;
        @BindView(R.id.studentNameTextView) TextView mNameTextView;
        @BindView(R.id.studentEmailTextView) TextView mEmailTextView;

        public StudentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mView = itemView;

        }

        private void setStudentName(String name) {
            mNameTextView.setText(name);
        }

        private void setStundetEmail(String email) {
            mEmailTextView.setText(email);
        }


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
