package com.ycy.voicerobot.module.main.presenter;

/**
 * Created by dh on 2017/2/21.
 */

public interface IMainPresenter {
    void onVoiceTypeSelect(int position);

    void onVoiceSpeedChanged(int progress);

    void onVoiceSpeedChangeCompletely(int progress);

    void startVoiceRobot();

}
