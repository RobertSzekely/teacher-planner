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

import static com.example.robertszekely.teacherplanner.activity.BaseActivity.fmt;

public class FeatureListFragment extends Fragment {
    private static final String TAG = "FeatureListFragment";
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mFeatureReference = mRootRef.child("feature");
    private DatabaseReference mIterationReference = mRootRef.child("iteration");
    private Query mQueryCurrentIteration;
    private FeatureDataPassListener mCallBack;
    private String featureKey;

    public final static String DATA_RECEIVE = "data_receive";

    private String mIterationId;

    public FeatureListFragment() {
    }

    public interface FeatureDataPassListener {
        void passFeatureData(String data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recyclerviewlist, container, false);

        if (container != null) {
            container.removeAllViews();
        }

         mIterationId = getArguments().getString(DATA_RECEIVE);
        Log.d(TAG, "received iteration id: "+mIterationId);

        if(mIterationId != null) {
            mQueryCurrentIteration = mRootRef.child("feature").orderByChild("iterationId").equalTo(mIterationId);
        } else {
            Toast.makeText(getContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();

        }

        mQueryCurrentIteration.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int completedFeatures = 0;
                int totalFeatures = 0;
                for(DataSnapshot featureSnapshot: dataSnapshot.getChildren()) {
                    totalFeatures++;
                    Feature feature = featureSnapshot.getValue(Feature.class);
//                    if(feature.isCompleted()) {
//                        completedFeatures++;
//                        Log.w(TAG, "completedFeatures++");
//                    }
                }
                float progress;
                if(completedFeatures == 0) {
                    progress = 0;
                } else  {
                    progress = (float) completedFeatures*100/totalFeatures;
                }
                //TODO FIX BUG CALLING THIS CODE 2 TIMES?!
                Log.w(TAG, "Iteration Progress: " + progress);
                mIterationReference.child(mIterationId).child("progress").setValue(progress);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //setting recyclerview
        RecyclerView featureRecyclerView = (RecyclerView) rootView.findViewById(R.id.generalRecyclerView);
        featureRecyclerView.setHasFixedSize(true);
        featureRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //set adapter
        FirebaseRecyclerAdapter<Feature, FeatureViewHolder> firebaseFeatureAdapter = new FirebaseRecyclerAdapter<Feature, FeatureViewHolder>(
                Feature.class,
                R.layout.row_general_item,
                FeatureViewHolder.class,
                mQueryCurrentIteration) {


            @Override                                                                             //changed position to FINAL
            protected void populateViewHolder(final FeatureViewHolder viewHolder, final Feature model, final int position) {

                featureKey = getRef(position).getKey();

//                viewHolder.setFeatureName(model.getFeatureName());
//                viewHolder.setFeatureDetails(model.getContent());
//                viewHolder.setFeatureProgress(fmt(model.getProgress()));
//                viewHolder.setFeatureCheckBox(model.isCompleted());
//
//                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Log.w(TAG, "You clicked on " + position);
//                        mCallBack.passFeatureData(featureKey);
//
//                    }
//                });
//                viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (buttonView.isChecked()) {
////                            mIterationReference.child(model.getIterationId()).child("completed").setValue(true);
//                            mFeatureReference.child(model.getFeatureId()).child("completed").setValue(true);
//                            mFeatureReference.child(model.getFeatureId()).child("progress").setValue(100);
//                            //setTasksCompleted(true);
//
//                        } else {
////                            mIterationReference.child(model.getIterationId()).child("completed").setValue(false);
//                            mFeatureReference.child(model.getFeatureId()).child("completed").setValue(false);
//                            mFeatureReference.child(model.getFeatureId()).child("progress").setValue(0);
//                            //setTasksCompleted(false);
//                        }
//
//                    }
//                });
            }
        };
        featureRecyclerView.setAdapter(firebaseFeatureAdapter);
        return rootView;
    }

//    private void setTasksCompleted(final boolean completed) {
//        final DatabaseReference mTaskRef = mRootRef.child("task");
//        Query mTaskQuery = mTaskRef.orderByChild("featureId").equalTo(featureKey);
//        mTaskQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot taskSnapshot: dataSnapshot.getChildren()) {
//                    Task task = taskSnapshot.getValue(Task.class);
//                    mTaskRef.child(task.getTaskId()).child("completed").setValue(completed);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallBack = (FeatureDataPassListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FeatureDataPassListener");
        }
    }

    private static class FeatureViewHolder extends RecyclerView.ViewHolder {

        View mView;
        CheckBox checkBox;

        public FeatureViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            checkBox = (CheckBox) mView.findViewById(R.id.itemCheckBox);

        }

        private void setFeatureName(String name) {
            TextView feature_name = (TextView) mView.findViewById(R.id.itemNameTextView);
            feature_name.setText(name);
        }

        private void setFeatureDetails(String details) {
            TextView feature_details = (TextView) mView.findViewById(R.id.itemDetailsTextView);
            feature_details.setText(details);
        }

        private void setFeatureProgress(String progress) {
            TextView mFeatureProgressTextView = (TextView) mView.findViewById(R.id.itemProgressTextView);
            final String progressText = progress+"%";
            mFeatureProgressTextView.setText(progressText);
        }

        private void setFeatureCheckBox(boolean checked) {
            checkBox.setChecked(checked);
        }
    }

}
