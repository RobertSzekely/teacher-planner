package com.example.robertszekely.teacherplanner.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Feature;
import com.example.robertszekely.teacherplanner.models.Task;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TaskListFragment extends Fragment {

    private static final String TAG = "TaskListFragment";
    public final static String DATA_RECEIVE = "data_receive";

    Query mCurrentFeatureQuery;

    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mTaskRef = mRootRef.child("task");
    private DatabaseReference mFeatureRef = mRootRef.child("feature");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recyclerviewlist, container, false);

        if(container != null) {
            container.removeAllViews();
        }

        final String mFeatureId = getArguments().getString(DATA_RECEIVE);
        Log.d(TAG, "Received feature id: " + mFeatureId);

        if(mFeatureId != null) {
            mCurrentFeatureQuery = mTaskRef.orderByChild("featureId").equalTo(mFeatureId);
        } else {
            Toast.makeText(getContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();
        }
//
//        mCurrentFeatureQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<Task> completedTaskList = new ArrayList<>();
//                int totalTasks = 0;
//                for(DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
//                    totalTasks++;
//                    Task task = taskSnapshot.getValue(Task.class);
//                    if(task.isCompleted()) {
//                        completedTaskList.add(task);
//                    }
//                }
//                float progress;
//                if(completedTaskList.isEmpty()) {
//                    progress = 0;
//                } else {
//                    progress = completedTaskList.size()*100/totalTasks;
//                }
//                mFeatureRef.child(mFeatureId).child("progress").setValue(progress);
//
////                if(progress == 100) {
////                    mFeatureRef.child(mFeatureId).child("completed").setValue(true);
////                } else {
////                    mFeatureRef.child(mFeatureId).child("completed").setValue(false);
////                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        //setting recyclerview
        RecyclerView taskRecyclerView = (RecyclerView) rootView.findViewById(R.id.generalRecyclerView);
        taskRecyclerView.setHasFixedSize(true);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //set adapter
        FirebaseRecyclerAdapter<Task, TaskViewHolder> firebaseTaskAdapter = new FirebaseRecyclerAdapter<Task, TaskViewHolder>(
                Task.class,
                R.layout.row_general_item,
                TaskViewHolder.class,
                mCurrentFeatureQuery) {


            @Override
            protected void populateViewHolder(TaskViewHolder viewHolder, final Task model, int position) {
                viewHolder.setTaskName(model.getTaskName());
                viewHolder.setTaksDetails(model.getContent());
                viewHolder.setCheckBox(model.isCompleted());
                viewHolder.setTaksProgress();

                viewHolder.mTaskCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (buttonView.isChecked()) {
//                            mIterationReference.child(model.getIterationId()).child("completed").setValue(true);
                            mTaskRef.child(model.getTaskId()).child("completed").setValue(true);

                        } else {
//                            mIterationReference.child(model.getIterationId()).child("completed").setValue(false);
                            mTaskRef.child(model.getTaskId()).child("completed").setValue(false);
                        }

                    }
                });

            }

        };
        taskRecyclerView.setAdapter(firebaseTaskAdapter);
        return rootView;
    }

    private static class TaskViewHolder extends RecyclerView.ViewHolder {

        View mView;
        CheckBox mTaskCheckBox;

        public TaskViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            mTaskCheckBox = (CheckBox) mView.findViewById(R.id.itemCheckBox);
        }

        public void setTaskName(String name) {
            TextView mTaskNameTextView = (TextView) mView.findViewById(R.id.itemNameTextView);
            mTaskNameTextView.setText(name);
        }

        public void setTaksDetails(String details) {
            TextView mTaskDetailsTextView = (TextView) mView.findViewById(R.id.itemDetailsTextView);
            mTaskDetailsTextView.setText(details);
        }

        public void setTaksProgress() {
            TextView mTaskProgressTextView = (TextView) mView.findViewById(R.id.itemProgressTextView);
            mTaskProgressTextView.setText("");
        }

        public void setCheckBox(boolean checked) {
            mTaskCheckBox.setChecked(checked);
        }

    }
}
