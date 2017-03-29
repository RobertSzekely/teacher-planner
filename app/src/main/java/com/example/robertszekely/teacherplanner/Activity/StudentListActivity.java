package com.example.robertszekely.teacherplanner.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;


import com.example.robertszekely.teacherplanner.models.Student;
import com.example.robertszekely.teacherplanner.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

/**
 * An activity representing a list of Students. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StudentDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StudentListActivity extends BaseActivity {

    private static final String TAG = "PostListFragment";
    private RecyclerView mStudentList;
    private LinearLayoutManager mManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        mStudentList = (RecyclerView) findViewById(R.id.student_list);
        mStudentList.setHasFixedSize(true);
        mStudentList.setLayoutManager(new LinearLayoutManager(this));


//        // Set up Layout Manager, reverse layout
//        mManager = new LinearLayoutManager(this);
//        mManager.setReverseLayout(true);
//        mManager.setStackFromEnd(true);
//        mRecycler.setLayoutManager(mManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        View recyclerView = findViewById(R.id.student_list);


//        assert recyclerView != null;
//        setupRecyclerView((RecyclerView) recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Student, StudentViewHolder> firebaseStudentRecyclerAdapter = new FirebaseRecyclerAdapter<Student, StudentViewHolder>(
                Student.class,
                R.layout.student_row,
                StudentViewHolder.class,
                mStudentReference) {

            @Override
            protected void populateViewHolder(StudentViewHolder viewHolder, Student model, int position) {
                viewHolder.setStudentName(model.getName());
                viewHolder.setStundetEmail(model.getEmail());
                Float progress = model.getProgress();
                String progressString = progress.toString();
                viewHolder.setStudentProgress(fmt(model.getProgress()));
            }
        };
        mStudentList.setAdapter(firebaseStudentRecyclerAdapter);


    }

    private static class StudentViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public StudentViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        private void setStudentName(String name) {
            TextView student_name = (TextView) mView.findViewById(R.id.studentNameTextView);
            student_name.setText(name);
        }

        private void setStundetEmail(String email) {
            TextView student_email = (TextView) mView.findViewById(R.id.studentEmailTextView);
            student_email.setText(email);
        }

        private void setStudentProgress(String progress) {
            TextView student_progress = (TextView) mView.findViewById(R.id.progressTextView);
            String progressText = progress + "%";
            student_progress.setText(progressText);
        }



    }



    //    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
//        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
//    }

//    public class SimpleItemRecyclerViewAdapter
//            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {
//
//        private final List<DummyContent.DummyItem> mValues;
//
//        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
//            mValues = items;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.student_list_content, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, int position) {
//            holder.mItem = mValues.get(position);
//            holder.mIdView.setText(mValues.get(position).id);
//            holder.mContentView.setText(mValues.get(position).content);
//
//            holder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mTwoPane) {
//                        Bundle arguments = new Bundle();
//                        arguments.putString(StudentDetailFragment.ARG_ITEM_ID, holder.mItem.id);
//                        StudentDetailFragment fragment = new StudentDetailFragment();
//                        fragment.setArguments(arguments);
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.student_detail_container, fragment)
//                                .commit();
//                    } else {
//                        Context context = v.getContext();
//                        Intent intent = new Intent(context, StudentDetailActivity.class);
//                        intent.putExtra(StudentDetailFragment.ARG_ITEM_ID, holder.mItem.id);
//
//                        context.startActivity(intent);
//                    }
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return mValues.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            public final View mView;
//            public final TextView mIdView;
//            public final TextView mContentView;
//            public DummyContent.DummyItem mItem;
//
//            public ViewHolder(View view) {
//                super(view);
//                mView = view;
//                mIdView = (TextView) view.findViewById(R.id.id);
//                mContentView = (TextView) view.findViewById(R.id.content);
//            }
//
//            @Override
//            public String toString() {
//                return super.toString() + " '" + mContentView.getText() + "'";
//            }
//        }
//    }
}
