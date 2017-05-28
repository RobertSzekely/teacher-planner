package com.example.robertszekely.teacherplanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Feature;
import com.example.robertszekely.teacherplanner.viewholder.FeatureViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FeatureListActivity extends BaseActivity {

    private static final String TAG = FeatureListActivity.class.getSimpleName();

    public static final String EXTRA_ITERATION_KEY = "iteration_key";

    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Feature, FeatureViewHolder> mAdapter;
    private LinearLayoutManager mManager;

    private String iterationKey;

    @BindView(R.id.features_recycler_view)
    RecyclerView mRecycler;
    @BindView(R.id.fab_new_feature)
    FloatingActionButton mNewFeatureButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_list);
        ButterKnife.bind(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mManager = new LinearLayoutManager(this);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(mManager);

        //Gets the iteration key from the previous activity
        iterationKey = (String) getIntent().getExtras().getSerializable(EXTRA_ITERATION_KEY);

        //results features for current iteration
        Query featureQuery = mDatabase.child("iteration-features").child(iterationKey);

        mAdapter = new FirebaseRecyclerAdapter<Feature, FeatureViewHolder>(
                Feature.class,
                R.layout.row_feature,
                FeatureViewHolder.class,
                featureQuery) {

            @Override
            protected void populateViewHolder(FeatureViewHolder viewHolder, final Feature model, int position) {

                viewHolder.bindToFeature(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.button_view_tasks:
                                Log.d(TAG, "View tasks button " + model.getBody());
                                break;
                            case R.id.button_edit_feature:
                                Log.d(TAG, "Edit feature button " + model.getBody());
                                break;
                            case R.id.button_remove_feature:
                                Log.d(TAG, "Remove feature button " + model.getBody());
                                break;
                            default:
                                break;
                        }
                    }
                });

            }
        };
        mRecycler.setAdapter(mAdapter);

        mNewFeatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Facem ceva?", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
