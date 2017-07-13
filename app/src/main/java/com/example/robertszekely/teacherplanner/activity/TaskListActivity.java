package com.example.robertszekely.teacherplanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Task;
import com.example.robertszekely.teacherplanner.viewholder.TaskViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskListActivity extends BaseActivity {

    private static final String TAG = TaskListActivity.class.getSimpleName();

    public static final String EXTRA_FEATURE_KEY = "feature_key";
    public static final String EXTRA_ITERATION_KEY = "iteration_key";

    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Task, TaskViewHolder> mAdapter;
    private LinearLayoutManager mManager;

    private String featureKey;
    private String iterationKey;

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
        iterationKey = (String) getIntent().getExtras().getSerializable(EXTRA_ITERATION_KEY);

        //results tasks for current feature
        Query taskQuery = mDatabase.child("feature-tasks").child(featureKey);

        mAdapter = new FirebaseRecyclerAdapter<Task, TaskViewHolder>(
                Task.class,
                R.layout.row_task,
                TaskViewHolder.class,
                taskQuery) {

            @Override
            protected void populateViewHolder(final TaskViewHolder viewHolder, final Task model, int position) {

                final DatabaseReference taskRef = getRef(position);
                final String taskKey = taskRef.getKey();

                viewHolder.bindToTask(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.button_edit_task:
                                Log.d(TAG, "Edit taks button: " + model.getBody());
                                editTask(taskRef);
                                break;
                            case R.id.button_remove_task:
                                Log.d(TAG, "Remove task button: " + model.getBody());
                                removeTask(taskRef);
                                break;
                            case R.id.checkbox_task:
                                Log.d(TAG, "Task checkbox :" + model.getBody());
                                //Need to write to both places where task is stored
//                                DatabaseReference globalTaskRef = mDatabase.child("tasks").child(taskKey);
                                DatabaseReference featureTaskRef = mDatabase.child("feature-tasks").child(featureKey).child(taskKey);

                                final Boolean checked = viewHolder.mCompletedCheckBox.isChecked();

                                //Run two transactions
//                                onTaskCheckboxClicked(globalTaskRef, checked);
                                onTaskCheckboxClicked(featureTaskRef, checked);
//                                onTaskCheckboxClicked(featureTaskRef, checked);
                        }
                    }
                });

            }
        };
        mRecycler.setAdapter(mAdapter);

        taskQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "A tag was updated");
                int totalTasks = 0, completedTasks = 0;
                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                    totalTasks++;
                    if (taskSnapshot.getValue(Task.class).isCompleted()) {
                        completedTasks++;
                    }
                }
                double progress;
                if (totalTasks == 0) {
                    progress = 0;
                } else {
                    progress = completedTasks * 100 / totalTasks;
                }
                // update feature progress at /features/$featureid/progress
                // and at /iteration-features/$iterationid/$featureid
                Map<String, Object> childUpdates = new HashMap<>();
//                childUpdates.put("/features/" + featureKey + "/progress/", progress);
                childUpdates.put("/iteration-features/" + iterationKey + "/" + featureKey + "/progress/", progress);

                mDatabase.updateChildren(childUpdates);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTaskDialog(true, null);
            }
        });


    }

    private void removeTask(DatabaseReference reference) {
        reference.removeValue();
    }


    private void showTaskDialog(final boolean newTask, final DatabaseReference taskReference) {
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
                if (TextUtils.isEmpty(body)) {
                    Toast.makeText(TaskListActivity.this, "Must fill in body", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TaskListActivity.this, "Saving task...", Toast.LENGTH_SHORT).show();
                    if (newTask) {
                        writeNewTask(featureKey, body);
                    } else {
                        updateTask(taskReference, body);
                    }

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

    private void editTask(DatabaseReference taskReference) {
        showTaskDialog(false, taskReference);
    }

    private void updateTask(DatabaseReference taskReference , String body) {
        Task task = new Task(body);
        taskReference.setValue(task);

    }


    private void writeNewTask(String featureId, String body) {
        // Create new task at /feature-tasks/$featureid/$taskid and at
        // /tasks/$taskid simultaneously
        String key = mDatabase.child("tasks").push().getKey();
        Task task = new Task(body);

        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/tasks/" + key, task);
        childUpdates.put("feature-tasks/" + featureId + "/" + key, task);

        mDatabase.updateChildren(childUpdates);
    }


    private void onTaskCheckboxClicked(DatabaseReference taskRef, final boolean checked) {
        taskRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Task t = mutableData.getValue(Task.class);
                if (t == null) {
                    return Transaction.success(mutableData);
                }

                t.setCompleted(checked);
                //Set value and report transaction success
                mutableData.setValue(t);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "taskTransaction:onComplete:" + databaseError);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


}
