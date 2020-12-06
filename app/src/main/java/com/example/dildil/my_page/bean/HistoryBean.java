package com.example.dildil.my_page.bean;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class HistoryBean {

    public HistoryBean() {

    }

    @Ignore
    public HistoryBean(int vid, String VImg, String VTitle, long currentTime, int playTime, int totalDuration) {
        this.vid = vid;
        this.VImg = VImg;
        this.VTitle = VTitle;
        this.currentTime = currentTime;
        this.playTime = playTime;
        this.totalDuration = totalDuration;
    }

    @PrimaryKey()
    private int vid;
    private String VImg;
    private String VTitle;
    private String VTime;
    private String VUpName;
    private int totalDuration;
    private int playTime;
    private long currentTime;
    private int VProgress;

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getVImg() {
        return VImg;
    }

    public void setVImg(String VImg) {
        this.VImg = VImg;
    }

    public String getVTitle() {
        return VTitle;
    }

    public void setVTitle(String VTitle) {
        this.VTitle = VTitle;
    }

    public String getVTime() {
        return VTime;
    }

    public void setVTime(String VTime) {
        this.VTime = VTime;
    }

    public String getVUpName() {
        return VUpName;
    }

    public void setVUpName(String VUpName) {
        this.VUpName = VUpName;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public int getVProgress() {
        return VProgress;
    }

    public void setVProgress(int VProgress) {
        this.VProgress = VProgress;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }
}
