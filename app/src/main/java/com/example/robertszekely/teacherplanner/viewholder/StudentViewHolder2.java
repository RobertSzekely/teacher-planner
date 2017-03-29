package com.example.robertszekely.teacherplanner.viewholder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.robertszekely.teacherplanner.models.Student;
import com.example.robertszekely.teacherplanner.R;

public class StudentViewHolder2 extends RecyclerView.ViewHolder {

    public TextView nameView;
    public ImageView profileView;

    public StudentViewHolder2(View itemView) {
        super(itemView);

        nameView = (TextView) itemView.findViewById(R.id.student_name);
        profileView = (ImageView) itemView.findViewById(R.id.student_photo);
    }

    public void bindToStudent(Student student, View.OnClickListener starClickListener) {
        nameView.setText(student.getName());

        nameView.setOnClickListener(starClickListener);
    }
}
