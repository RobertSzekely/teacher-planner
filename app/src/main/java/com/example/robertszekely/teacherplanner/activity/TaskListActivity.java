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
import com.example.robertszekely.teacherplanner.viewholder.TaskViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskListActivity extends BaseActivity {

    private static final String TAG = TaskListActivity.class.getSimpleName();

    public static final String EXTRA_FEATURE_KEY = "feature_key";

    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Task, TaskViewHolder> mAdapter;
    private LinearLayoutManager mManager;

    private String featureKey;

    @BindView(R.id.task_recycler_view)
    RecyclerView mRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        ButterKnife.bind(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mManager = new LinearLayoutManager(this);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(mManager);

        // Gets the feature key from the previous activity
        featureKey = (String) getIntent().getExtras().getSerializable(EXTRA_FEATURE_KEY);

        //results tasks for current feature
        Query taskQuery = mDatabase.child("feature-tasks").child(featureKey);

        mAdapter = new FirebaseRecyclerAdapter<Task, TaskViewHolder>(
                Task.class,
                R.layout.row_task,
                TaskViewHolder.class,
                taskQuery) {

            @Override
            protected void populateViewHolder(TaskViewHolder viewHolder, final Task model, int position) {

                viewHolder.bindToTask(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.button_edit_task:
                                Log.d(TAG, "Edit taks button: " + model.getBody());
                                break;
                            case R.id.button_remove_task:
                                Log.d(TAG, "Remove task button: " + model.getBody());
                                break;
                            case R.id.checkbox_task:
                                Log.d(TAG, "Task checkbox :" + model.getBody());
                        }
                    }
                });

            }
        };
        mRecycler.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }





}
