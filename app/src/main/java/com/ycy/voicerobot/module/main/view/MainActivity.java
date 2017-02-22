package com.ycy.voicerobot.module.main.view;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ycy.voicerobot.R;
import com.ycy.voicerobot.bean.TalkBean;
import com.ycy.voicerobot.module.main.adapter.TalkListAdapter;
import com.ycy.voicerobot.module.main.presenter.IMainPresenter;
import com.ycy.voicerobot.module.main.presenter.MainPresenter;
import com.ycy.voicerobot.util.SpUtils;
import com.ycy.voicerobot.widget.VoiceSpeedDialog;
import com.ycy.voicerobot.widget.VoiceTypeListDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements IMainView {

    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.tv_voice_type)
    TextView tvVoiceType;

    @BindView(R.id.tv_voice_speed)
    TextView tvVoiceSpeed;

    @BindView(R.id.tv_version)
    TextView tvVersion;

    @BindView(R.id.rl_voice_speed)
    RelativeLayout rlVoiceSpeed;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ActionBarDrawerToggle toggle;
    private IMainPresenter mIMainPresenter;
    private List<TalkBean> mTalkBeanList = new ArrayList<>();
    private TalkListAdapter mTalkListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        afterView();
    }

    private void afterView() {
        //init Toolbar
        this.setSupportActionBar(mToolbar);

        //DrawerLayout bind ActionBarDrawerToggle
        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //init RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mTalkListAdapter = new TalkListAdapter(mTalkBeanList, this);
        recyclerView.setAdapter(mTalkListAdapter);

        //init presenter
        mIMainPresenter = new MainPresenter(this);
    }

    @OnClick(R.id.rl_voice_type)
    public void onVoiceTypeClick() {
        VoiceTypeListDialog voiceTypeListDialog = new VoiceTypeListDialog(this, R.style.CommonDialog);
        voiceTypeListDialog.setOnVoiceTypeListSelectListener(new VoiceTypeListDialog.OnVoiceTypeListSelectListener() {
            @Override
            public void onSelect(int position) {
                mIMainPresenter.onVoiceTypeSelect(position);
            }
        });
        voiceTypeListDialog.show();
    }

    @OnClick(R.id.rl_voice_speed)
    public void onVoiceSpeedClick() {
        VoiceSpeedDialog voiceSpeedDialog = new VoiceSpeedDialog(this, R.style.CommonDialog);
        voiceSpeedDialog.setOnProgressChangedListener(new VoiceSpeedDialog.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                mIMainPresenter.onVoiceSpeedChanged(progress);
            }

            @Override
            public void onStopTrackingTouch(int progress) {
                mIMainPresenter.onVoiceSpeedChangeCompletely(progress);
            }
        });
        voiceSpeedDialog.show(SpUtils.readVoiceSpeed());
    }

    @OnClick(R.id.iv_talk)
    public void onTalkClick() {
        mIMainPresenter.startVoiceRobot();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDrawerLayout != null) {
            mDrawerLayout.removeDrawerListener(toggle);
        }

        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void updateVoiceType(String type) {
        if (tvVoiceType != null) {
            tvVoiceType.setText(type);
        }
    }

    @Override
    public void updateVoiceSpeed(String speed) {
        if (tvVoiceSpeed != null) {
            tvVoiceSpeed.setText(speed);
        }
    }

    @Override
    public void updateVersion(String version) {
        if (tvVersion != null) {
            tvVersion.setText(version);
        }
    }

    @Override
    public void updateList(List<TalkBean> talkBeanList) {
        mTalkBeanList.clear();
        mTalkBeanList.addAll(talkBeanList);
        mTalkListAdapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(mTalkBeanList.size() - 1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}
