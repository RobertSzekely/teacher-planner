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
                }
            });
        }
    };

        studentListRecyclerView.setAdapter(firebaseStudentRecyclerAdapter);

    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView mNameTextView;
        TextView mEmailTextView;
        TextView mProgressTextView;

        public StudentViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        private void setStudentName(String name) {
            mNameTextView = (TextView) mView.findViewById(R.id.studentNameTextView);
            mNameTextView.setText(name);
        }

        private void setStundetEmail(String email) {
            mEmailTextView = (TextView) mView.findViewById(R.id.studentEmailTextView);
            mEmailTextView.setText(email);
        }

        private void setStudentProgress(String progress) {
            mProgressTextView = (TextView) mView.findViewById(R.id.progressTextView);
            String progressText = progress + "%";
            mProgressTextView.setText(progressText);
        }

    }
}
