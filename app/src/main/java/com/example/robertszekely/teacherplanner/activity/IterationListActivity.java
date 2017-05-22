package com.example.robertszekely.teacherplanner.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.fragment.IterationListFragment;
import com.example.robertszekely.teacherplanner.models.Iteration;
import com.example.robertszekely.teacherplanner.models.Student;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IterationListActivity extends BaseActivity {

    private static final String TAG = IterationListActivity.class.getSimpleName();

    RecyclerView iterationRecyclerView;

    private Query mQueryCurrentStudentIterations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iteration_list);
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

        Student student = (Student) getIntent().getExtras().getSerializable("student");

        if (student != null) {
            String mStudentKey = student.getUid();
            mQueryCurrentStudentIterations = mIterationReference.orderByChild("studentId").equalTo(mStudentKey);

        } else {
            Toast.makeText(this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
        }

        setRecyclerView();
        setAdapter();

    }

    public void setRecyclerView() {
        iterationRecyclerView = (RecyclerView) findViewById(R.id.iterationRecyclerView);
        iterationRecyclerView.setHasFixedSize(true);
        iterationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setAdapter() {
        FirebaseRecyclerAdapter<Iteration, IterationViewHolder> firebaseIterationAdapter = new FirebaseRecyclerAdapter<Iteration, IterationViewHolder>(
                Iteration.class,
                R.layout.row_general_item,
                IterationViewHolder.class,
                mQueryCurrentStudentIterations) {


            @Override
            protected void populateViewHolder(final IterationViewHolder viewHolder, final Iteration model, final int position) {

                final String iteration_key = getRef(position).getKey();

                viewHolder.setIterationName(model.getIterationName());
                viewHolder.setIterationDetails(model.getContent());
                viewHolder.setIterationCheckBox(model.isCompleted());
                viewHolder.setIterationProgress(fmt(model.getProgress()));

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.w(TAG, "You clicked on " + model.getIterationName());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("iteration_key", model);
                        navigateToActivity(IterationDetailsActivity.class, bundle);

                    }
                });


//                viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (buttonView.isChecked()) {
//                            Log.w(TAG, "You checked on " + position);
//                            mIterationReference.child(model.getIterationId()).child("completed").setValue(true);
//                            mIterationReference.child(model.getIterationId()).child("progress").setValue(100);
//
//                        } else {
//                            Log.w(TAG, "You unchecked on " + position);
//                            mIterationReference.child(model.getIterationId()).child("completed").setValue(false);
//                            mIterationReference.child(model.getIterationId()).child("progress").setValue(0);
//                        }
//
//                    }
//                });


            }
        };

        iterationRecyclerView.setAdapter(firebaseIterationAdapter);
    }


    public static class IterationViewHolder extends RecyclerView.ViewHolder {

        View mView;
        @BindView(R.id.itemCheckBox) CheckBox checkBox;
        @BindView(R.id.itemNameTextView) TextView mIterationTitle;
        @BindView(R.id.itemDetailsTextView) TextView mIterationDetails;
        @BindView(R.id.itemProgressTextView) TextView mIterationProgress;

        public IterationViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);
        }

        private void setIterationName(String name) {
            mIterationTitle.setText(name);
        }

        private void setIterationDetails(String details) {
            mIterationDetails.setText(details);
        }

        private void setIterationProgress(String progress) {
            final String progressText = progress+"%";
            mIterationProgress.setText(progressText);
        }

        private void setIterationCheckBox(boolean checked) {
            checkBox.setChecked(checked);
        }

    }

}
