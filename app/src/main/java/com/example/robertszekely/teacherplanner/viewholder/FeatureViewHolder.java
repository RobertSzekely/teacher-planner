package com.example.robertszekely.teacherplanner.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Feature;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by robertszekely on 29/05/2017.
 */

public class FeatureViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.feature_body_field)
    TextView mBodyView;
    @BindView(R.id.feature_progress_field)
    TextView mProgressView;
    @BindView(R.id.button_view_tasks)
    public Button mViewTasksButton;
    @BindView(R.id.button_edit_feature)
    public Button mEditFeatureButton;
    @BindView(R.id.button_remove_feature)
    public Button mRemoveFeatureButton;

    public FeatureViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindToFeature(Feature feature, View.OnClickListener buttonsClickListener) {
        mBodyView.setText(feature.getBody());
        mProgressView.setText(String.valueOf((int)feature.getProgress()));

        mViewTasksButton.setOnClickListener(buttonsClickListener);
        mEditFeatureButton.setOnClickListener(buttonsClickListener);
        mRemoveFeatureButton.setOnClickListener(buttonsClickListener);
    }
}
