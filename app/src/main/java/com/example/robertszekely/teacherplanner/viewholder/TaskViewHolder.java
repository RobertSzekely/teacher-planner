package com.example.robertszekely.teacherplanner.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Task;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by robertszekely on 29/05/2017.
 */

public class TaskViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.task_body_view)
    TextView mBodyView;
    @BindView(R.id.checkbox_task)
    public CheckBox mCompletedCheckBox;
    @BindView(R.id.button_edit_task)
    Button mEditButton;
    @BindView(R.id.button_remove_task)
    Button mRemoveButton;

    public TaskViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindToTask(Task task, View.OnClickListener buttonClickListener) {
        mBodyView.setText(task.getBody());

        mCompletedCheckBox.setChecked(task.isCompleted());

        mCompletedCheckBox.setOnClickListener(buttonClickListener);

        mEditButton.setOnClickListener(buttonClickListener);
        mRemoveButton.setOnClickListener(buttonClickListener);


    }
}
