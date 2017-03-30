package com.example.robertszekely.teacherplanner.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Student;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.robertszekely.teacherplanner.activity.BaseActivity.fmt;

public class StudentListFragment extends Fragment {

    private static final String TAG = "StudentListFragment";
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mStudentReference = mRootRef.child("student");
    private RecyclerView studentListRecyclerView;
    private DataPassListener mCallBack;

    public interface DataPassListener{
        public void passData(String data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_students_list, container, false);

        //set recyclerview
        studentListRecyclerView = (RecyclerView) view.findViewById(R.id.student_list_recycler_view);
        studentListRecyclerView.setHasFixedSize(true);
        studentListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //set adapter
        FirebaseRecyclerAdapter<Student, StudentViewHolder> firebaseStudentRecyclerAdapter = new FirebaseRecyclerAdapter<Student, StudentListFragment.StudentViewHolder>(
                Student.class,
                R.layout.student_row,
                StudentListFragment.StudentViewHolder.class,
                mStudentReference) {


            @Override                                                                             //changed position to FINAL
            protected void populateViewHolder(StudentListFragment.StudentViewHolder viewHolder, final Student model, final int position) {

                final String student_key = getRef(position).getKey();

                viewHolder.setStudentName(model.getName());
                viewHolder.setStundetEmail(model.getEmail());
                viewHolder.setStudentProgress(fmt(model.getProgress()));

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.w(TAG, "You clicked on " + student_key);
//                        mRecycleViewAdapter.getRef(position).removeValue();
//                        Bundle bundle = new Bundle(model.getUid());
                        mCallBack.passData(student_key);
                    }
                });
            }
        };

        studentListRecyclerView.setAdapter(firebaseStudentRecyclerAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallBack = (DataPassListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataPassListener");
        }
    }

    private static class StudentViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public StudentViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        private void setStudentName(String name) {
            TextView student_name = (TextView) mView.findViewById(R.id.studentNameTextView);
            student_name.setText(name);
        }

        private void setStundetEmail(String email) {
            TextView student_email = (TextView) mView.findViewById(R.id.studentEmailTextView);
            student_email.setText(email);
        }

        private void setStudentProgress(String progress) {
            TextView student_progress = (TextView) mView.findViewById(R.id.progressTextView);
            String progressText = progress + "%";
            student_progress.setText(progressText);
        }



    }


}
