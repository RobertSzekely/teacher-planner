package com.example.robertszekely.teacherplanner.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Feature;
import com.example.robertszekely.teacherplanner.models.Iteration;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FeatureListActivity extends BaseActivity {

    private static final String TAG = FeatureListActivity.class.getSimpleName();
    @BindView(R.id.featuresRecyclerView)
    RecyclerView featureRecyclerView;

    private Query mQueryCurrentIterationFeatures;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_list);
        ButterKnife.bind(this);

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

        Iteration receivedIteration = (Iteration) getIntent().getExtras().getSerializable(ITERAION_BUNDLE_KEY);

        if(receivedIteration != null) {
            String iterationId = receivedIteration.getIterationId();
            mQueryCurrentIterationFeatures = mFeatureReference.orderByChild("iterationId").equalTo(iterationId);
            setRecyclerView();
            setAdapter();
        }
    }

    private void setRecyclerView() {
        featureRecyclerView.setHasFixedSize(true);
        featureRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setAdapter() {
        FirebaseRecyclerAdapter<Feature, FeatureViewHolder> firebaseRecyclerViewAdapter = new FirebaseRecyclerAdapter<Feature, FeatureViewHolder>(
                Feature.class,
                R.layout.row_feature,
                FeatureViewHolder.class,
                mQueryCurrentIterationFeatures) {

            @Override
            protected void populateViewHolder(FeatureViewHolder viewHolder, Feature model, int position) {

                viewHolder.setDescriptionTextView(model.getContent());
                viewHolder.setProgressTextView(model.getProgress());
            }
        };
        featureRecyclerView.setAdapter(firebaseRecyclerViewAdapter);
    }



    public static class FeatureViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.featureDescriptionTextView)
        TextView descriptionTextView;
        @BindView(R.id.featureProgressTextView)
        TextView progressTextView;

        public FeatureViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setDescriptionTextView(String description) {
            descriptionTextView.setText(description);
        }

        private void setProgressTextView(float progress) {
            progressTextView.setText(fmt(progress));
        }

        @OnClick(R.id.featureViewTaskButton)
        public void viewTaskButton() {
            Log.d(TAG, "View Tasks button pressed");
        }

        @OnClick(R.id.featureEditFeatureButton)
        public void editFeatureDetailsButton() {
            Log.d(TAG, "Edit feature Details Button Pressed");
        }


    }


}
