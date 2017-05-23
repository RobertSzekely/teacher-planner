package com.example.robertszekely.teacherplanner.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Iteration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IterationDetailsActivity extends BaseActivity {

    @BindView(R.id.iterationDetailsTitleTextView) TextView mIterationTitleTextView;
    @BindView(R.id.iterationDetailsDescriptionTextView) TextView mDescriptionTextView;
    @BindView(R.id.iterationDetailsStatusTextView) TextView mStatusTextView;
    @BindView(R.id.iterationDetailsDeadlineTextView) TextView mDeadlineTextView;

    Iteration receivedIteration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iteration_details);
        ButterKnife.bind(this);

        receivedIteration = (Iteration) getIntent().getExtras().getSerializable(ITERAION_BUNDLE_KEY);

        if(receivedIteration != null) {
            mIterationTitleTextView.setText(receivedIteration.getIterationName());
            mDescriptionTextView.setText(receivedIteration.getContent());
            //TODO
            mStatusTextView.setText("TODO");
            mDeadlineTextView.setText("TODO");
        }
    }

    @OnClick(R.id.iterationDetailsSeeFeaturesButton)
    void showFeaturesForCurrentIteraion() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITERAION_BUNDLE_KEY, receivedIteration);
        navigateToActivity(FeatureListActivity.class, bundle);
    }

}
