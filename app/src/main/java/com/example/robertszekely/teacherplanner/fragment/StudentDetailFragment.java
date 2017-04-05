package com.example.robertszekely.teacherplanner.fragment;

import android.os.Bundle;
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
import com.example.robertszekely.teacherplanner.models.Iteration;
import com.example.robertszekely.teacherplanner.models.Student;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static com.example.robertszekely.teacherplanner.activity.BaseActivity.fmt;


public class StudentDetailFragment extends Fragment {

    private static final String TAG = "StudentDetailFragment";
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mIterationReference = mRootRef.child("iteration");
    private DatabaseReference mDatabaseCurrentStundet;
    private Query mQueryCurrentUser;
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public final static String DATA_RECEIVE = "data_receive";

    private Student mItem;
    private String mStudentKey;

    public StudentDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_student_detail, container, false);

        if (container != null) {
            container.removeAllViews();
        }

        mStudentKey = getArguments().getString(DATA_RECEIVE);

        if(mStudentKey != null) {
//            ((TextView) rootView.findViewById(R.id.student_name_text_view)).setText(mStudentKey);
            mDatabaseCurrentStundet = FirebaseDatabase.getInstance().getReference().child("iteration");
            mQueryCurrentUser = mDatabaseCurrentStundet.orderByChild("studentId").equalTo(mStudentKey);
        } else {
            Toast.makeText(getContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();

        }

        //setting recyclerview
        RecyclerView iterationRecyclerView = (RecyclerView) rootView.findViewById(R.id.iterationRecyclerView);
        iterationRecyclerView.setHasFixedSize(true);
        iterationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //set adapter
        FirebaseRecyclerAdapter<Iteration, IterationViewHolder> firebaseIterationAdapter = new FirebaseRecyclerAdapter<Iteration, IterationViewHolder>(
                Iteration.class,
                R.layout.iteration_row,
                IterationViewHolder.class,
                mQueryCurrentUser) {


            @Override                                                                             //changed position to FINAL
            protected void populateViewHolder(final IterationViewHolder viewHolder, final Iteration model, final int position) {

                viewHolder.setIterationName(model.getIterationName());
                viewHolder.setIterationDetails(model.getContent());
                viewHolder.setIterationCheckBox(model.isCompleted());

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
                            Log.w(TAG, "You checked on " + position);
                            mIterationReference.child(model.getIterationId()).child("completed").setValue(true);

                        } else {
                            Log.w(TAG, "You unchecked on " + position);
                            mIterationReference.child(model.getIterationId()).child("completed").setValue(false);
                        }

                    }
                });


            }
        };

//        // Show the dummy content as text in a TextView.
//        if (mItem != null) {
//            ((TextView) rootView.findViewById(R.id.fragment_student_detail)).setText(mItem.details);
//        }
        iterationRecyclerView.setAdapter(firebaseIterationAdapter);
        return rootView;
    }


    private static class IterationViewHolder extends RecyclerView.ViewHolder {

        View mView;
        CheckBox checkBox;

        public IterationViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            checkBox = (CheckBox)mView.findViewById(R.id.iterationCheckBox);

        }

        private void setIterationName(String name) {
            TextView student_name = (TextView) mView.findViewById(R.id.iterationNameTextView);
            student_name.setText(name);
        }

        private void setIterationDetails(String details) {
            TextView student_email = (TextView) mView.findViewById(R.id.iterationDetailsTextView);
            student_email.setText(details);
        }

        private void setIterationCheckBox(boolean checked) {
            checkBox.setChecked(checked);
        }

    }


//    @Override
//    public void onStart() {
//        super.onStart();
//        Bundle args = getArguments();
//        if (args != null) {
//            mStudentKey = args.getString(DATA_RECEIVE);
//        }
//    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//
//        if(getArguments().containsKey(ARG_ITEM_ID)) {
//
//        }
//
////        if (getArguments().containsKey(ARG_ITEM_ID)) {
////            // Load the dummy content specified by the fragment
////            // arguments. In a real-world scenario, use a Loader
////            // to load content from a content provider.
////            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
////
////            Activity activity = this.getActivity();
////            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
////            if (appBarLayout != null) {
////                appBarLayout.setTitle(mItem.content);
////            }
////        }
//    }


}
