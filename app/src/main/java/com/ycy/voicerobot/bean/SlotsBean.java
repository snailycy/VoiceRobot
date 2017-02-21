package com.ycy.voicerobot.bean;


public class SlotsBean {
    private String name;
    private String code;
    private String artist;
    private String song;
    private String sightspot;
    private LocationBean location;
    private DateBean datetime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSightspot() {
        return sightspot;
    }

    public void setSightspot(String sightspot) {
        this.sightspot = sightspot;
    }

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

    public DateBean getDatetime() {
        return datetime;
    }

    public void setDatetime(DateBean datetime) {
        this.datetime = datetime;
    }
}
