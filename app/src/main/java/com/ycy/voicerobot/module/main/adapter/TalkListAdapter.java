package com.ycy.voicerobot.module.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.ycy.voicerobot.R;
import com.ycy.voicerobot.bean.TalkBean;
import com.ycy.voicerobot.module.main.viewholder.RobotTalkListViewHolder;
import com.ycy.voicerobot.module.main.viewholder.UserTalkListViewHolder;

import java.util.List;

public class TalkListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_ROBOT = 1;
    public static final int VIEW_TYPE_USER = 2;
    private List<TalkBean> mTalkBeanList;
    private Context context;

    public TalkListAdapter(List<TalkBean> mTalkBeanList, Context context) {
        this.mTalkBeanList = mTalkBeanList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ROBOT:
                View robotLayout = View.inflate(context, R.layout.robot_layout, null);
                return new RobotTalkListViewHolder(robotLayout);
            case VIEW_TYPE_USER:
                View userLayout = View.inflate(context, R.layout.user_layout, null);
                return new UserTalkListViewHolder(userLayout);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mTalkBeanList == null ? 0 : mTalkBeanList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null) return;
        String talkText = "";
        TalkBean talkBean = mTalkBeanList.get(position);
        talkText = talkBean.getText();
        if (TextUtils.isEmpty(talkText)) return;

        if (holder instanceof RobotTalkListViewHolder) {
            RobotTalkListViewHolder robotHolder = (RobotTalkListViewHolder) holder;
            robotHolder.mTvRobotTalk.setText(talkText);
        } else if (holder instanceof UserTalkListViewHolder) {
            UserTalkListViewHolder userHolder = (UserTalkListViewHolder) holder;
            userHolder.mTvUserTalk.setText(talkText);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mTalkBeanList != null) {
            TalkBean talkBean = mTalkBeanList.get(position);
            return talkBean.getType();
        }
        return super.getItemViewType(position);
    }
}