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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IterationListActivity extends BaseActivity{

    private static final String TAG = IterationListActivity.class.getSimpleName();

    public static final String EXTRA_STUDENT_KEY = "student_key";

    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Iteration, IterationViewHolder> mAdapter;
    private LinearLayoutManager mManager;

    private String studentKey;

    @BindView(R.id.iteration_recycler_view)
    RecyclerView mRecycler;
    @BindView(R.id.fab_new_iteration)
    FloatingActionButton mNewIterationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iteration_list);
        ButterKnife.bind(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mManager = new LinearLayoutManager(this);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(mManager);

        //Gets the student key from the previous activity
        studentKey = (String) getIntent().getExtras().getSerializable(EXTRA_STUDENT_KEY);

        //results iterations for current student
        Query iterationQuery = mDatabase.child("student-iterations").child(studentKey);

        mAdapter = new FirebaseRecyclerAdapter<Iteration, IterationViewHolder>(
                Iteration.class,
                R.layout.row_iteration,
                IterationViewHolder.class,
                iterationQuery) {


            @Override
            protected void populateViewHolder(final IterationViewHolder viewHolder, final Iteration model, final int position) {

                viewHolder.bindToIteration(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.button_view_features:
                                Log.d(TAG, "View features button " + model.getTitle());
                                break;
                            case R.id.button_edit_iteration:
                                Log.d(TAG, "Edit iteration button " + model.getTitle());
                                break;
                            case R.id.button_remove_iteration:
                                Log.d(TAG, "Remove iteration button " + model.getTitle());
                                break;
                            default:
                                break;
                        }
                    }
                });

            }
        };

        mRecycler.setAdapter(mAdapter);

        mNewIterationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IterationListActivity.this, NewIterationActivity.class);
                intent.putExtra(NewIterationActivity.EXTRA_STUDENT_KEY, studentKey);
                startActivity(intent);
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
