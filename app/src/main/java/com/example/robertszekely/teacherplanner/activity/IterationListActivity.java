package com.example.robertszekely.teacherplanner.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Iteration;
import com.example.robertszekely.teacherplanner.models.Student;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IterationListActivity extends BaseActivity {

    private static final String TAG = IterationListActivity.class.getSimpleName();

    RecyclerView iterationRecyclerView;

    private Query mQueryCurrentStudentIterations;

    private static final int COLOR_OPEN_STATUS =  Color.rgb(255, 132, 2);
    private static final int COLOR_IN_PROGRESS_STATUS = Color.rgb(0, 182, 255);
    private static final int COLOR_RESOLVED_STATUS = Color.rgb(14, 214, 0);
    private static final int COLOR_CLOSED_STATUS = Color.rgb(72, 0, 255);


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

        Student student = (Student) getIntent().getExtras().getSerializable(STUDENT_BUNDLE_KEY);

        if (student != null) {
            String mStudentKey = student.getUid();
            mQueryCurrentStudentIterations = mIterationReference.orderByChild("studentId").equalTo(mStudentKey);
            setRecyclerView();
            setAdapter();
        } else {
            Toast.makeText(this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
        }

    }

    public void setRecyclerView() {
        iterationRecyclerView = (RecyclerView) findViewById(R.id.iterationRecyclerView);
        iterationRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        iterationRecyclerView.setLayoutManager(linearLayoutManager);

//        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
//        Drawable verticalDivider = ContextCompat.getDrawable(this, R.drawable.horizontal_divider);
//        mDividerItemDecoration.setDrawable(verticalDivider);
//        iterationRecyclerView.addItemDecoration(mDividerItemDecoration);
    }

    public void setAdapter() {
        FirebaseRecyclerAdapter<Iteration, IterationViewHolder> firebaseIterationAdapter = new FirebaseRecyclerAdapter<Iteration, IterationViewHolder>(
                Iteration.class,
                R.layout.row_iteration,
                IterationViewHolder.class,
                mQueryCurrentStudentIterations) {


            @Override
            protected void populateViewHolder(final IterationViewHolder viewHolder, final Iteration model, final int position) {

                final String iteration_key = getRef(position).getKey();

                viewHolder.setIterationTitle(model.getIterationName());
                Date date = new Date();
                date.getDate();

                viewHolder.setIterationTitle(model.getIterationName());
                viewHolder.setIterationDeadline(date);
                int random = randInt(0, 3);
                viewHolder.setIterationStatus(random);

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.w(TAG, "You clicked on " + model.getIterationName());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ITERAION_BUNDLE_KEY, model);
                        navigateToActivity(IterationDetailsActivity.class, bundle);

                    }
                });

            }
        };

        iterationRecyclerView.setAdapter(firebaseIterationAdapter);
    }


    public static class IterationViewHolder extends RecyclerView.ViewHolder {

        View mView;
        @BindView(R.id.iterationTitleTextView) TextView mIterationTitle;
        @BindView(R.id.iterationDeadlineTextView) TextView mIterationDeadline;
        @BindView(R.id.iterationStatusTextView) TextView mIterationStatus;

        public IterationViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, itemView);
        }

        private void setIterationTitle(String name) {
            mIterationTitle.setText(name);
        }

        private void setIterationDeadline(Date date) {
            DateFormat df =  new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String formattedDate = df.format(date);
            mIterationDeadline.setText(formattedDate);
        }
        private void setIterationStatus(int status) {
            if (status == 1) {
                mIterationStatus.setText(R.string.inprogress_status);
//                mIterationStatus.setTextColor(COLOR_IN_PROGRESS_STATUS);
            } else if (status == 2) {
                mIterationStatus.setText(R.string.resolved_status);
//                mIterationStatus.setTextColor(COLOR_RESOLVED_STATUS);
            } else if(status == 3) {
                mIterationStatus.setText(R.string.closed_status);
//                mIterationStatus.setTextColor(COLOR_CLOSED_STATUS);
            } else {
                mIterationStatus.setText(R.string.open_status);
//                mIterationStatus.setTextColor(COLOR_OPEN_STATUS);
            }
        }

    }

}
