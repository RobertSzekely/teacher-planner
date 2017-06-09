package com.example.robertszekely.teacherplanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Meeting;
import com.example.robertszekely.teacherplanner.viewholder.MeetingViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingsListActivity extends BaseActivity {

    private static final String TAG = MeetingsListActivity.class.getSimpleName();

    public static final String EXTRA_STUDENT_KEY = "student_key";

    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Meeting, MeetingViewHolder> mAdapter;
    private LinearLayoutManager mManager;

    private String studentKey;

    @BindView(R.id.meetings_recycler_view)
    RecyclerView mRecycler;
    @BindView(R.id.fab_new_meeting)
    FloatingActionButton mNewMeetingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_list);
        ButterKnife.bind(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mManager = new LinearLayoutManager(this);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(mManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, mManager.getOrientation());
        mRecycler.addItemDecoration(dividerItemDecoration);

        //Gets the student key from the previous activity
        studentKey = (String) getIntent().getExtras().getSerializable(EXTRA_STUDENT_KEY);

        //results meetings for current student
        Query meetingQuery = mDatabase.child("student-meetings").child(studentKey);

        mAdapter = new FirebaseRecyclerAdapter<Meeting, MeetingViewHolder>(
                Meeting.class,
                R.layout.row_meeting,
                MeetingViewHolder.class,
                meetingQuery) {
            @Override
            protected void populateViewHolder(MeetingViewHolder viewHolder, Meeting model, int position) {
                final DatabaseReference meetingRef = getRef(position);
                final String meetingKey = meetingRef.getKey();

                viewHolder.bindToMeeting(model);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MeetingsListActivity.this, MeetingDetailsActivity.class);
                        intent.putExtra(MeetingDetailsActivity.EXTRA_STUDENT_KEY, studentKey);
                        intent.putExtra(MeetingDetailsActivity.EXTRA_MEETING_KEY, meetingKey);
                        startActivity(intent);
                    }
                });
            }
        };

        mRecycler.setAdapter(mAdapter);


        mNewMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeetingsListActivity.this, NewMeetingActivity.class);
                intent.putExtra(NewMeetingActivity.EXTRA_STUDENT_KEY, studentKey);
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
        if (i == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}
