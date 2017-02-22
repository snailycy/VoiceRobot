package com.ycy.voicerobot.bean;


import java.io.Serializable;

public class DateBean implements Serializable {
    private String date;
    private String type;
    private String dateOrig;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateOrig() {
        return dateOrig;
    }

    public void setDateOrig(String dateOrig) {
        this.dateOrig = dateOrig;
    }
}
