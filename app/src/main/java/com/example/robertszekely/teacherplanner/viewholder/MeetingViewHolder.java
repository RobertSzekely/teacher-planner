package com.example.robertszekely.teacherplanner.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.robertszekely.teacherplanner.R;
import com.example.robertszekely.teacherplanner.models.Meeting;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MeetingViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.row_meeting_date_view)
    TextView mDateView;
    @BindView(R.id.row_meeting_body_view)
    TextView mBodyView;


    public MeetingViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindToMeeting(Meeting meeting) {
        mDateView.setText(meeting.getDate());
        mBodyView.setText(meeting.getBody());
    }
}
