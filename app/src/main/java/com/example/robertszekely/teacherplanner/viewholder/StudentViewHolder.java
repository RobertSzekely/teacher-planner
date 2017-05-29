package com.example.robertszekely.teacherplanner.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Student;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by robertszekely on 28/05/2017.
 */

public class StudentViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.row_student_first_name_view)
    TextView mFirstNameView;
    @BindView(R.id.row_student_last_name_view)
    TextView mLastNameView;
    @BindView(R.id.row_student_email_view)
    TextView mEmailView;
    @BindView(R.id.row_student_phone_view)
    TextView mPhoneView;

    public StudentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindToStudent(Student student) {
        mFirstNameView.setText(student.getFirstName());
        mLastNameView.setText(student.getLastName());
        mEmailView.setText(student.getEmail());
        mPhoneView.setText(student.getPhoneNumber());
    }
}
