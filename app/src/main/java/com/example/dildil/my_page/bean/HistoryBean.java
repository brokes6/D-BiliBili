package com.example.dildil.my_page.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HistoryBean {
    @PrimaryKey(autoGenerate = true)
    private int index;
    private int vid;
    private String vImg;
    private String vTitle;
    private String vTime;
    private String vUpName;
    private int totalDuration;
    private int currentTime;
    private int vProgress;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getvImg() {
        return vImg;
    }

    public void setvImg(String vImg) {
        this.vImg = vImg;
    }

    public String getvTitle() {
        return vTitle;
    }

    public void setvTitle(String vTitle) {
        this.vTitle = vTitle;
    }

    public String getvTime() {
        return vTime;
    }

    public void setvTime(String vTime) {
        this.vTime = vTime;
    }

    public String getvUpName() {
        return vUpName;
    }

    public void setvUpName(String vUpName) {
        this.vUpName = vUpName;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public int getvProgress() {
        return vProgress;
    }

    public void setvProgress(int vProgress) {
        this.vProgress = vProgress;
    }
}
