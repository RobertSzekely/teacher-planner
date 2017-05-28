package com.example.robertszekely.teacherplanner.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Iteration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by robertszekely on 28/05/2017.
 */

public class IterationViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iteration_title_view)
    TextView mIterationTitle;
    @BindView(R.id.iteration_deadline_view)
    TextView mIterationDeadline;
    @BindView(R.id.iteration_status_view)
    TextView mIterationStatus;
    @BindView(R.id.iteration_body_view)
    TextView mIterationBody;

    @BindView(R.id.button_view_features)
    public Button viewFeaturesButton;
    @BindView(R.id.button_edit_iteration)
    public Button editIterationButton;
    @BindView(R.id.button_remove_iteration)
    public Button removeIterationButton;

    public IterationViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindToIteration(Iteration iteration, View.OnClickListener buttonsClickListener) {
        mIterationTitle.setText(iteration.getTitle());
        mIterationBody.setText(iteration.getBody());
        mIterationDeadline.setText(iteration.getDeadline());

        viewFeaturesButton.setOnClickListener(buttonsClickListener);
        editIterationButton.setOnClickListener(buttonsClickListener);
        removeIterationButton.setOnClickListener(buttonsClickListener);

    }

//        private void setIterationTitle(String name) {
//            mIterationTitle.setText(name);
//        }
//
//        private void setIterationDeadline(Date date) {
//            DateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//            String formattedDate = df.format(date);
//            mIterationDeadline.setText(formattedDate);
//        }
//
//        private void setIterationStatus(int status) {
//            if (status == 1) {
//                mIterationStatus.setText(R.string.inprogress_status);
////                mIterationStatus.setTextColor(COLOR_IN_PROGRESS_STATUS);
//            } else if (status == 2) {
//                mIterationStatus.setText(R.string.resolved_status);
////                mIterationStatus.setTextColor(COLOR_RESOLVED_STATUS);
//            } else if (status == 3) {
//                mIterationStatus.setText(R.string.closed_status);
////                mIterationStatus.setTextColor(COLOR_CLOSED_STATUS);
//            } else {
//                mIterationStatus.setText(R.string.open_status);
////                mIterationStatus.setTextColor(COLOR_OPEN_STATUS);
//            }
//        }
}
