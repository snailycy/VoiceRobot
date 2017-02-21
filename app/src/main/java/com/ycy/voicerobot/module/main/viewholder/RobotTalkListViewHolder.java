package com.ycy.voicerobot.module.main.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ycy.voicerobot.R;

public class RobotTalkListViewHolder extends RecyclerView.ViewHolder {
    public TextView mTvRobotTalk;

    public RobotTalkListViewHolder(View itemView) {
        super(itemView);
        mTvRobotTalk = (TextView) itemView.findViewById(R.id.tv_robot_talk);
    }
}
