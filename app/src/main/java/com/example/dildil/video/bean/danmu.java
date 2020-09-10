package com.example.dildil.video.bean;

public class danmu {
    private String color;
    private String content;
    private String location;
    private int showSecond;
    private int uid;
    private int vid;

    public danmu(String content,int showSecond,int uid,int vid){
        this.content = content;
        this.showSecond = showSecond;
        this.uid = uid;
        this.location = "bottom";
        this.vid = vid;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getShowSecond() {
        return showSecond;
    }

    public void setShowSecond(int showSecond) {
        this.showSecond = showSecond;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    @Override
    public String toString() {
        return "danmu{" +
                "color='" + color + '\'' +
                ", content='" + content + '\'' +
                ", location=" + location +
                ", showSecond=" + showSecond +
                ", uid=" + uid +
                ", vid=" + vid +
                '}';
    }
}
