package com.example.robertszekely.teacherplanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Feature;
import com.example.robertszekely.teacherplanner.models.Task;
import com.example.robertszekely.teacherplanner.viewholder.TaskViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

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
                showNewTaskDialog();
            }
        });


    }

    private void showNewTaskDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(TaskListActivity.this);
        final View mDialogView = getLayoutInflater().inflate(R.layout.dialog_task, null);
        final EditText mBodyField = (EditText) mDialogView.findViewById(R.id.dialog_task_body_field);
        Button mCancelButton = (Button) mDialogView.findViewById(R.id.dialog_task_cancel_button);
        Button mConfirmButton = (Button) mDialogView.findViewById(R.id.dialog_task_confirm_button);

        mBuilder.setView(mDialogView);
        final AlertDialog alertDialog = mBuilder.create();

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if body field is empty
                final String body = mBodyField.getText().toString();
                if(TextUtils.isEmpty(body)) {
                    Toast.makeText(TaskListActivity.this, "Must fill in body", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TaskListActivity.this, "Saving task...", Toast.LENGTH_SHORT).show();
                    writeNewTask(featureKey, body);
                    alertDialog.cancel();
                }
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if body field is empty
                Toast.makeText(TaskListActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                alertDialog.cancel();
            }
        });

        alertDialog.show();
    }

    private void writeNewTask(String featureId, String body) {
        // Create new task at /feature-tasks/$featureid/$taskid and at
        // /tasks/$taskid simultaneously
        String key = mDatabase.child("tasks").push().getKey();
        Task task = new Task(featureId, body);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/tasks/" + key, task);
        childUpdates.put("feature-tasks/" + featureId + "/" + key, task);

        mDatabase.updateChildren(childUpdates);
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
