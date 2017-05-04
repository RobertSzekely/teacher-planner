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
import com.example.robertszekely.teacherplanner.models.Iteration;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FeatureListFragment extends Fragment {
    private static final String TAG = "FeatureListFragment";
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mFeatureReference = mRootRef.child("feature");
    private DatabaseReference mDatabaseCurrentIteration;
    private Query mQueryCurrentIteration;

    public final static String DATA_RECEIVE = "data_receive";

    private String mIterationId;

    public FeatureListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recyclerviewlist, container, false);

        if (container != null) {
            container.removeAllViews();
        }

         mIterationId = getArguments().getString(DATA_RECEIVE);

        if(mIterationId != null) {
            mDatabaseCurrentIteration = FirebaseDatabase.getInstance().getReference().child("iteration");
            mQueryCurrentIteration = mDatabaseCurrentIteration.orderByChild("iterationId").equalTo(mIterationId);
        } else {
            Toast.makeText(getContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();

        }
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

                viewHolder.setFeatureName(model.getFeatureName());
                viewHolder.setFeatureDetails(model.getContent());
                viewHolder.setFeatureCheckBox(model.isCompleted());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.w(TAG, "You clicked on " + position);

                    }
                });
                viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (buttonView.isChecked()) {
//                            mIterationReference.child(model.getIterationId()).child("completed").setValue(true);
                            mFeatureReference.child(model.getFeatureId()).child("completed").setValue(true);

                        } else {
//                            mIterationReference.child(model.getIterationId()).child("completed").setValue(false);
                            mFeatureReference.child(model.getFeatureId()).child("completed").setValue(false);
                        }

                    }
                });
            }
        };
        featureRecyclerView.setAdapter(firebaseFeatureAdapter);
        return rootView;
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

        private void setFeatureCheckBox(boolean checked) {
            checkBox.setChecked(checked);
        }
    }

}
