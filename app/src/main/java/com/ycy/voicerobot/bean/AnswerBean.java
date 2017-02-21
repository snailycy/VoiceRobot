package com.ycy.voicerobot.bean;


public class AnswerBean {
    private String text;
    private String type;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AnswerBean{" +
                "text='" + text + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
