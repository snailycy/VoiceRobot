package com.ycy.voicerobot.module.main.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 播放音乐的service
 */
public class MusicService extends Service {
    private static final String INTENT_KEY_IS_PLAY = "intent_key_is_play";
    private static final String INTENT_KEY_MUSIC_PATH = "intent_key_music_path";

    private MediaPlayer mediaPlayer;

    public static void startService(Context context, boolean isPlay, String musicPath) {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(INTENT_KEY_IS_PLAY, isPlay);
        intent.putExtra(INTENT_KEY_MUSIC_PATH, musicPath);
        context.startService(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean isPlay = intent.getBooleanExtra(INTENT_KEY_IS_PLAY, false);
        if (isPlay) {
            String musicPath = intent.getStringExtra(INTENT_KEY_MUSIC_PATH);
            playMusic(musicPath);
        } else {
            stopMusic();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void playMusic(String musicPath) {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(musicPath);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
