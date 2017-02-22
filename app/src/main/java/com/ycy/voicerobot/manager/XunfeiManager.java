package com.ycy.voicerobot.manager;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.ycy.voicerobot.module.main.service.MusicService;
import com.ycy.voicerobot.util.LogUtils;
import com.ycy.voicerobot.util.SpUtils;

/**
 * 讯飞智能语音相关API的封装
 */
public class XunfeiManager {
    private static XunfeiManager mXunfeiManager;
    /**
     * 语音听写对象
     */
    private SpeechRecognizer mIat;
    /**
     * 语音合成对象
     */
    private SpeechSynthesizer mTts;
    /**
     * 语义理解对象
     */
    private TextUnderstander mTextUnderstander;
    /**
     * 语音听写对话框
     */
    private RecognizerDialog mIatDialog;
    /**
     * 初始监听器
     */
    private InitListener mInitListener;


    private XunfeiManager() {
        //设置初始监听器
        mInitListener = new InitListener() {

            @Override
            public void onInit(int code) {
                if (code != ErrorCode.SUCCESS) {
                    LogUtils.d("初始监听器初始化失败，错误码：" + code);
                } else {
                    LogUtils.d("初始监听器初始化成功");
                }
            }
        };
    }

    public static XunfeiManager getInstance() {
        if (mXunfeiManager == null) {
            synchronized (XunfeiManager.class) {
                if (mXunfeiManager == null) {
                    mXunfeiManager = new XunfeiManager();
                }
            }
        }
        return mXunfeiManager;
    }

    /**
     * 语音听写
     */
    public void voiceDictation(Context context, RecognizerDialogListener recognizerDialogListener) {
        //如果音乐正在播放，先停掉
        stopMusicService(context);
        //如果正在播放语音，先停掉
        stopSpeak();

        if (mIatDialog != null) {
            mIatDialog.dismiss();
        }

        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        if (mIat == null)
            mIat = SpeechRecognizer.createRecognizer(context, mInitListener);
        // 设置参数
        setVoiceDictationParam();

        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        if (mIatDialog == null)
            mIatDialog = new RecognizerDialog(context, mInitListener);

        // 显示听写对话框
        mIatDialog.setListener(recognizerDialogListener);
        mIatDialog.show();
    }

    /**
     * 语义理解
     */
    public void semanticComprehension(Context context, String text, TextUnderstanderListener textUnderstanderListener) {
        if (mTextUnderstander == null)
            mTextUnderstander = TextUnderstander.createTextUnderstander(context, mInitListener);

        if (mTextUnderstander.isUnderstanding()) {
            mTextUnderstander.cancel();
            LogUtils.d("取消语义理解");
        } else {
            int ret = mTextUnderstander.understandText(text, textUnderstanderListener);
            if (ret != 0) {
                LogUtils.d("语义理解失败，错误码:" + ret);
            }
        }
    }

    /**
     * 语音合成
     */
    public void voiceSynthesis(Context context, String smartAnswer, SynthesizerListener ttsListener) {
        //如果对话框还在，先dismiss
        if (mIatDialog != null && mIatDialog.isShowing()) {
            mIatDialog.dismiss();
        }

        // 初始化合成对象
        if (mTts == null)
            mTts = SpeechSynthesizer.createSynthesizer(context, mInitListener);

        setVoiceSynthesisParam();

        int code = mTts.startSpeaking(smartAnswer, ttsListener);
        if (code != ErrorCode.SUCCESS) {
            LogUtils.e("语音合成失败,错误码: " + code);
        }
    }

    /**
     * 设置语音合成参数
     */
    private void setVoiceSynthesisParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);

        // 设置在线合成发音人
        // 默认发音人
        String voicer = SpUtils.readVoiceTypeParam();
        LogUtils.d("voicer = " + voicer);
        mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);

        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, String.valueOf(SpUtils.readVoiceSpeed()));
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "50");
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");

        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/wanbei/tts.wav");
    }

    /**
     * 关闭音乐
     */
    private void stopMusicService(Context context) {
        if (!(context instanceof Activity)) {
            throw new RuntimeException("context must be activity");
        }
        MusicService.startService(context, false, null);
    }

    /**
     * 关闭语音
     */
    private void stopSpeak() {
        if (mTts != null && mTts.isSpeaking()) {
            mTts.stopSpeaking();
        }
    }

    /**
     * 设置语音听写参数
     */
    private void setVoiceDictationParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
        // 设置语言
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置语言区域
        mIat.setParameter(SpeechConstant.ACCENT, "zh_cn");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/VoiceRobot/talk.wav");

        // 设置听写结果是否结果动态修正，为“1”则在听写过程中动态递增地返回结果，否则只在听写结束之后返回最终结果
        // 注：该参数暂时只对在线听写有效
        mIat.setParameter(SpeechConstant.ASR_DWA, "0");
    }
}
