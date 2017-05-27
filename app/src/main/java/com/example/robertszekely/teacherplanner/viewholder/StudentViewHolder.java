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

public class StudentViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.row_name_text_view)
    TextView nameView;
    @BindView(R.id.row_email_text_view)
    TextView emailView;
    @BindView(R.id.row_phone_text_view)
    TextView phoneView;

    public StudentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindToStudent(Student student) {
        nameView.setText(student.getName());
        emailView.setText(student.getEmail());
        phoneView.setText(student.getPhoneNumber());
    }
}
