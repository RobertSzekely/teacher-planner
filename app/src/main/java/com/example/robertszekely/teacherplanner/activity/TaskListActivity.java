package com.example.robertszekely.teacherplanner.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Feature;
import com.example.robertszekely.teacherplanner.models.Task;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskListActivity extends BaseActivity {

    private static final String TAG = TaskListActivity.class.getSimpleName();

//    @BindView(R.id.taskListRecyclerView)
    RecyclerView mTaskRecyclerView;

    String receivedFeatureKey;
    Query mQueryTasksForCurrentFeature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);

        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        receivedFeatureKey = (String) getIntent().getExtras().getSerializable(FEATURE_BUNDLE_KEY);

        if(receivedFeatureKey != null) {
            Log.d(TAG, "received feature:" + receivedFeatureKey);
            mQueryTasksForCurrentFeature = mTaskReference.orderByChild(FEATURE_ID).equalTo(receivedFeatureKey);
            setRecyclerView();
            setAdapter();
        } else {
            Toast.makeText(this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
        }


    }

    private void setRecyclerView() {
        mTaskRecyclerView = (RecyclerView) findViewById(R.id.taskListRecyclerView);
        mTaskRecyclerView.setHasFixedSize(true);
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setAdapter() {
        FirebaseRecyclerAdapter<Task, TaskViewHolder> taskFirebaseRecyclerViewAdapter = new FirebaseRecyclerAdapter<Task, TaskViewHolder>(
                Task.class,
                R.layout.row_task,
                TaskViewHolder.class,
                mQueryTasksForCurrentFeature) {

            @Override
            protected void populateViewHolder(TaskViewHolder viewHolder, Task model, int position) {

                viewHolder.setCompletedCheckBox(model.isCompleted());
                viewHolder.setDescriptionTexBox(model.getContent());
                viewHolder.mEditButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO
                    }
                });
                viewHolder.mRemoveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO
                    }
                });

            }
        };
        mTaskRecyclerView.setAdapter(taskFirebaseRecyclerViewAdapter);

    }


    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.taskCompletedCheckBox)
        CheckBox completedCheckBox;
        @BindView(R.id.taskDescriptionTextView)
        TextView mDescriptionTexBox;
        @BindView(R.id.taskEditButton)
        Button mEditButton;
        @BindView(R.id.taskRemoveButton)
        Button mRemoveButton;

        public TaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void setCompletedCheckBox(boolean completed) {
            completedCheckBox.setChecked(completed);
        }

        private void setDescriptionTexBox(String description) {
            mDescriptionTexBox.setText(description);
        }
    }



}
