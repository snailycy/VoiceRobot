package com.ycy.voicerobot.module.main.view;


import com.ycy.voicerobot.bean.TalkBean;

import java.util.List;

public interface IMainView {
    void updateVoiceType(String type);

    void updateVoiceSpeed(String speed);

    void updateVersion(String version);

    void updateList(List<TalkBean> talkBeanList);
}
