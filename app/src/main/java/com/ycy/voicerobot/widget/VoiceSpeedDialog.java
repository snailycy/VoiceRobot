package com.ycy.voicerobot.widget;

import android.app.Dialog;
import android.content.Context;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ycy.voicerobot.R;

/**
 * 设置播报声音语速的对话框
 */
public class VoiceSpeedDialog extends Dialog {
    private Context context;
    private OnProgressChangedListener mOnProgressChangedListener;
    private SeekBar sbVoiceSpeed;
    private TextView tvSpeedTitle;

    public VoiceSpeedDialog(Context context) {
        super(context);
        init(context);
    }

    public VoiceSpeedDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    protected VoiceSpeedDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(final Context context) {
        this.context = context;
        this.setContentView(R.layout.voice_speed_popwindow);

        tvSpeedTitle = (TextView) findViewById(R.id.tv_speed_title);
        sbVoiceSpeed = (SeekBar) findViewById(R.id.sb_voice_speed);
        sbVoiceSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSpeedTitle.setText(context.getResources().getString(R.string.voice_speed) + "：" + progress + "%");
                if (mOnProgressChangedListener != null) {
                    mOnProgressChangedListener.onProgressChanged(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mOnProgressChangedListener != null) {
                    mOnProgressChangedListener.onStopTrackingTouch(seekBar.getProgress());
                }
            }
        });
    }

    public void show(int progress) {
        if (sbVoiceSpeed != null) {
            sbVoiceSpeed.setProgress(progress);
        }
        super.show();
    }

    public void setOnProgressChangedListener(OnProgressChangedListener onProgressChangedListener) {
        this.mOnProgressChangedListener = onProgressChangedListener;
    }

    public interface OnProgressChangedListener {
        void onProgressChanged(int progress);

        void onStopTrackingTouch(int progress);
    }

}
