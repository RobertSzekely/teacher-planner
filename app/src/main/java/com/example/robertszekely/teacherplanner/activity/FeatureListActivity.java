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
import android.widget.TextView;
import android.widget.Toast;

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

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    private Query mQueryCurrentIterationFeatures;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_list);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Facem ceva?", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String receivedIterationKey = (String) getIntent().getExtras().getSerializable(ITERAION_BUNDLE_KEY);
        Log.d(TAG, "OnCreate..........................");
        if (receivedIterationKey != null) {
            Log.d(TAG, "Received iteration key: " + receivedIterationKey);
            mQueryCurrentIterationFeatures = mFeatureReference.orderByChild("iterationId").equalTo(receivedIterationKey);
            setRecyclerView();
            setAdapter();
        } else {
            Toast.makeText(this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
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
            protected void populateViewHolder(FeatureViewHolder viewHolder, final Feature model, int position) {

                viewHolder.setDescriptionTextView(model.getContent());
                viewHolder.setProgressTextView(model.getProgress());

                viewHolder.viewTaskButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(FEATURE_BUNDLE_KEY, model.getFeatureId());
                        navigateToActivity(TaskListActivity.class, bundle);
                    }
                });

            }
        };
        featureRecyclerView.setAdapter(firebaseRecyclerViewAdapter);
    }


    public static class FeatureViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.featureDescriptionTextView)
        TextView descriptionTextView;
        @BindView(R.id.featureProgressTextView)
        TextView progressTextView;
        @BindView(R.id.featureViewTaskButton)
        Button viewTaskButton;

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


        @OnClick(R.id.featureEditFeatureButton)
        public void editFeatureDetailsButton() {
            Log.d(TAG, "Edit feature Details Button Pressed");
        }


    }


}
