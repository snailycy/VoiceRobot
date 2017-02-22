package com.ycy.voicerobot.bean;


import java.io.Serializable;

public class TalkBean implements Serializable {
    private String text;
    private long time;
    private int type;

    public TalkBean(String text, long time, int type) {
        this.text = text;
        this.time = time;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
