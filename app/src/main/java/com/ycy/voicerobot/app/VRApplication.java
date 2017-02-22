package com.ycy.voicerobot.app;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

public class VRApplication extends Application {
    private static VRApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initXunfei();
    }

    public static VRApplication getApplication() {
        return app;
    }

    private void initXunfei() {
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=58ac2f10");
    }
}
