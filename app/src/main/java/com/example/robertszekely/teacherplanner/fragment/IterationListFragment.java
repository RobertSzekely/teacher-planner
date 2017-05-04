package com.example.robertszekely.teacherplanner.fragment;

import android.content.Context;
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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class IterationListFragment extends Fragment {

    private static final String TAG = "IterationListFragment";
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mIterationReference = mRootRef.child("iteration");
    private DatabaseReference mStudentReference = mRootRef.child("student");
    private DatabaseReference mDatabaseCurrentStundent;
    private Query mQueryCurrentStudentIterations;
    private Query mQueryStudentProgress;
    private IterationDataPassListener mCallBack;
    public final static String DATA_RECEIVE = "data_receive";
    private String mStudentKey;

    public interface IterationDataPassListener {
        public void passIterationData(String data);
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
//            mDatabaseCurrentStundent = FirebaseDatabase.getInstance().getReference().child("iteration");
            mQueryCurrentStudentIterations = mIterationReference.orderByChild("studentId").equalTo(mStudentKey);
        } else {
            Toast.makeText(getContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();

        }


        mQueryCurrentStudentIterations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Iteration> iterationCompletedList = new ArrayList<>();
                int totalIterations=0;
                for(DataSnapshot iterationSnapshot: dataSnapshot.getChildren()) {
                    totalIterations++;
                    Iteration iteration = iterationSnapshot.getValue(Iteration.class);
                    if(iteration.isCompleted()) {
                        iterationCompletedList.add(iteration);
                    }
                }
                float progress;
                if(iterationCompletedList.size() == 0) {
                    progress = 0;
                } else {
                    progress = iterationCompletedList.size()*100/totalIterations;
                }
//                Toast.makeText(getContext(), "Progress: " + progress, Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Progress: " + progress);
                mStudentReference.child(mStudentKey).child("progress").setValue(progress);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //setting recyclerview
        RecyclerView iterationRecyclerView = (RecyclerView) rootView.findViewById(R.id.iterationRecyclerView);
        iterationRecyclerView.setHasFixedSize(true);
        iterationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //set adapter
        FirebaseRecyclerAdapter<Iteration, IterationViewHolder> firebaseIterationAdapter = new FirebaseRecyclerAdapter<Iteration, IterationViewHolder>(
                Iteration.class,
                R.layout.row_iteration,
                IterationViewHolder.class,
                mQueryCurrentStudentIterations) {


            @Override                                                                             //changed position to FINAL
            protected void populateViewHolder(final IterationViewHolder viewHolder, final Iteration model, final int position) {

                final String iteration_key = getRef(position).getKey();

                viewHolder.setIterationName(model.getIterationName());
                viewHolder.setIterationDetails(model.getContent());
                viewHolder.setIterationCheckBox(model.isCompleted());



                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.w(TAG, "You clicked on " + position +"   "   +iteration_key);
                                mCallBack.passIterationData(iteration_key);
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

        iterationRecyclerView.setAdapter(firebaseIterationAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallBack = (IterationDataPassListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement StudentDataPassListener");
        }
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
