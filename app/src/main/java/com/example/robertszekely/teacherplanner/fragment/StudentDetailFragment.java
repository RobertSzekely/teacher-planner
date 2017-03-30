package com.example.robertszekely.teacherplanner.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Student;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class StudentDetailFragment extends Fragment {

    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if(getArguments().containsKey(ARG_ITEM_ID)) {

        }

//        if (getArguments().containsKey(ARG_ITEM_ID)) {
//            // Load the dummy content specified by the fragment
//            // arguments. In a real-world scenario, use a Loader
//            // to load content from a content provider.
//            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
//
//            Activity activity = this.getActivity();
//            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
//            if (appBarLayout != null) {
//                appBarLayout.setTitle(mItem.content);
//            }
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.student_detail, container, false);

        if (container != null) {
            container.removeAllViews();
        }

        mStudentKey = getArguments().getString(DATA_RECEIVE);

        if(mStudentKey != null) {
            ((TextView) rootView.findViewById(R.id.student_name_text_view)).setText(mStudentKey);
        } else {
            Toast.makeText(getContext(), "Something went wrong :(", Toast.LENGTH_SHORT).show();
        }

//        // Show the dummy content as text in a TextView.
//        if (mItem != null) {
//            ((TextView) rootView.findViewById(R.id.student_detail)).setText(mItem.details);
//        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            mStudentKey = args.getString(DATA_RECEIVE);
        }
    }



}
