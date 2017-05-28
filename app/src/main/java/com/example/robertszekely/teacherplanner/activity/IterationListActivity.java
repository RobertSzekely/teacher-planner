package com.example.robertszekely.teacherplanner.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Iteration;
import com.example.robertszekely.teacherplanner.models.Student;
import com.example.robertszekely.teacherplanner.viewholder.IterationViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IterationListActivity extends BaseActivity{

    private static final String TAG = IterationListActivity.class.getSimpleName();

    RecyclerView iterationRecyclerView;

    private Query mQueryCurrentStudentIterations;

    private String studentKey;

    private static final int COLOR_OPEN_STATUS = Color.rgb(255, 132, 2);
    private static final int COLOR_IN_PROGRESS_STATUS = Color.rgb(0, 182, 255);
    private static final int COLOR_RESOLVED_STATUS = Color.rgb(14, 214, 0);
    private static final int COLOR_CLOSED_STATUS = Color.rgb(72, 0, 255);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iteration_list);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        Student student = (Student) getIntent().getExtras().getSerializable(STUDENT_BUNDLE_KEY);
        studentKey = (String) getIntent().getExtras().getSerializable(STUDENT_BUNDLE_KEY);

        if (studentKey != null) {
            Log.d(TAG, "Received student key: " + studentKey);
//            String mStudentKey = student.getUid();
            mQueryCurrentStudentIterations = mIterationReference.orderByChild("studentId").equalTo(studentKey);
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

//                final String iteration_key = getRef(position).getKey();

//                viewHolder.setIterationTitle(model.getIterationName());
//
//                //TODO add date to iteration
//                Date date = new Date();
//                date.getDate();
//
//                viewHolder.setIterationTitle(model.getIterationName());
//                viewHolder.setIterationDeadline(date);
//                viewHolder.setIterationDescription(model.getContent());
//
//                //TODO add status to iteration
//                int random = randInt(0, 3);
//                viewHolder.setIterationStatus(random);


//                viewHolder.viewFeaturesButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable(ITERAION_BUNDLE_KEY, model.getIterationId());
//                        navigateToActivity(FeatureListActivity.class, bundle);
//                        Log.d(TAG, "Sent iteration: " + model.toString());
//                    }
//                });
                viewHolder.bindToIteration(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.button_view_features:
                                Log.d(TAG, "View features button");
                                break;
                            case R.id.button_edit_iteration:
                                Log.d(TAG, "Edit iteration button");
                                break;
                            case R.id.button_remove_iteration:
                                Log.d(TAG, "Remove iteration button");
                                break;
                            default:
                                break;
                        }
                    }
                });

            }
        };

        iterationRecyclerView.setAdapter(firebaseIterationAdapter);
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
