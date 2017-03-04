package com.ycy.voicerobot.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.ycy.voicerobot.app.VRApplication;

/**
 * SharedPreferences
 */

public class SpManager {
    private static SpManager mSpManager = null;
    private static final String spName = "voiceRobotConfig";
    private static SharedPreferences.Editor editor;
    private static SharedPreferences sp;

    private SpManager() {
        sp = VRApplication.getApplication().getSharedPreferences(spName, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static SpManager getInstance() {
        if (mSpManager == null) {
            synchronized (SpManager.class) {
                if (mSpManager == null) {
                    mSpManager = new SpManager();
                }
            }
        }
        return mSpManager;
    }

    public void writeVersion(String version) {
        editor.putString(SpKeyConstant.VERSION, version);
        editor.commit();
    }

    public String readVersion() {
        return sp.getString(SpKeyConstant.VERSION, "");
    }

    public void writeVoiceTypePosition(int position) {
        editor.putInt(SpKeyConstant.VOICE_TYPE, position);
        editor.commit();
    }

    public int readVoiceTypePosition() {
        return sp.getInt(SpKeyConstant.VOICE_TYPE, 0);
    }

    public void writeVoiceSpeed(int progress) {
        editor.putInt(SpKeyConstant.VOICE_SPEED, progress);
        editor.commit();
    }

    public int readVoiceSpeed() {
        return sp.getInt(SpKeyConstant.VOICE_SPEED, -1);
    }

    interface SpKeyConstant {
        String VERSION = "version";
        String VOICE_TYPE = "voice_type";
        String VOICE_SPEED = "voice_speed";
    }
}
