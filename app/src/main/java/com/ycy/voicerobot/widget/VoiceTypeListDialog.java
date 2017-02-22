package com.ycy.voicerobot.widget;

import android.app.Dialog;
import android.content.Context;
import android.widget.RadioGroup;

import com.ycy.voicerobot.R;
import com.ycy.voicerobot.manager.SpManager;
import com.ycy.voicerobot.util.LogUtils;

/**
 * 设置播报语音的语言类型对话框
 */
public class VoiceTypeListDialog extends Dialog {
    private Context context;
    private int[] rbIds = {R.id.rb_1, R.id.rb_2, R.id.rb_3, R.id.rb_4,
            R.id.rb_5, R.id.rb_6, R.id.rb_7, R.id.rb_8};

    public VoiceTypeListDialog(Context context) {
        super(context);
        init(context);
    }

    public VoiceTypeListDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    protected VoiceTypeListDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.setContentView(R.layout.voice_type_dialog);

        RadioGroup rgVoiceType = (RadioGroup) findViewById(R.id.rg_voice_type);
        rgVoiceType.check(rbIds[SpManager.getInstance().readVoiceTypePosition()]);

        rgVoiceType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int position = 0;
                switch (checkedId) {
                    case R.id.rb_1:
                        position = 0;
                        break;
                    case R.id.rb_2:
                        position = 1;
                        break;
                    case R.id.rb_3:
                        position = 2;
                        break;

                    case R.id.rb_4:
                        position = 3;
                        break;
                    case R.id.rb_5:
                        position = 4;
                        break;
                    case R.id.rb_6:
                        position = 5;
                        break;
                    case R.id.rb_7:
                        position = 6;
                        break;
                    case R.id.rb_8:
                        position = 7;
                        break;
                }
                LogUtils.d("position = " + position);
                if (onVoiceTypeListSelectListener != null) {
                    onVoiceTypeListSelectListener.onSelect(position);
                }
                dismiss();
            }
        });
    }

    public interface OnVoiceTypeListSelectListener {
        void onSelect(int position);
    }

    private OnVoiceTypeListSelectListener onVoiceTypeListSelectListener;

    public OnVoiceTypeListSelectListener getOnVoiceTypeListSelectListener() {
        return onVoiceTypeListSelectListener;
    }

    public void setOnVoiceTypeListSelectListener(OnVoiceTypeListSelectListener onVoiceTypeListSelectListener) {
        this.onVoiceTypeListSelectListener = onVoiceTypeListSelectListener;
    }
}
