package com.example.robertszekely.teacherplanner.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Student;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentListActivity extends BaseActivity {

    private static String TAG = StudentListActivity.class.getSimpleName();

    private RecyclerView studentListRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_students_list);

        setStudentRecyclerView();
        setStudentAdapter();

    }

    public void setStudentRecyclerView() {
        studentListRecyclerView = (RecyclerView) findViewById(R.id.student_list_recycler_view);
        studentListRecyclerView.setHasFixedSize(true);
        studentListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setStudentAdapter() {
        FirebaseRecyclerAdapter<Student, StudentViewHolder> firebaseStudentRecyclerAdapter = new FirebaseRecyclerAdapter<Student, StudentViewHolder>(
            Student.class,
            R.layout.row_student,
            StudentViewHolder.class,
            mStudentReference) {


        @Override
        protected void populateViewHolder(StudentViewHolder viewHolder, final Student model, int position) {
            final String student_key = getRef(position).getKey();

            viewHolder.setStudentName(model.getName());
            viewHolder.setStundetEmail(model.getEmail());
            viewHolder.setStudentProgress(fmt(model.getProgress()));

            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "You clicked on " + model.getName());
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("student", model);
                    navigateToActivity(StudentDetailsActivity.class, bundle);
                }
            });
        }
    };

        studentListRecyclerView.setAdapter(firebaseStudentRecyclerAdapter);

    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {

        View mView;
        @BindView(R.id.studentNameTextView) TextView mNameTextView;
        @BindView(R.id.studentEmailTextView) TextView mEmailTextView;
        @BindView(R.id.progressTextView) TextView mProgressTextView;

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

        private void setStudentProgress(String progress) {
            String progressText = progress + "%";
            mProgressTextView.setText(progressText);
        }

    }
}
