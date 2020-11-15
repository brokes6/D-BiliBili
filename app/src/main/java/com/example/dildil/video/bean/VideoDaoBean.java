package com.example.dildil.video.bean;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class VideoDaoBean {

    public VideoDaoBean() {

    }

    @Ignore
    public VideoDaoBean(int index, int id) {
        this.index = index;
        this.videoId = id;
    }

    @Ignore
    public VideoDaoBean(int index, boolean isThumbsUp) {
        this.index = index;
        this.isThumbsUp = isThumbsUp;
    }

    @PrimaryKey(autoGenerate = true)
    private int index;

    private int videoId;
    private String videoTitle;
    private int videoPlayTime;
    private boolean isThumbsUp;
    private boolean isCoin;
    private boolean isCollection;


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public int getVideoPlayTime() {
        return videoPlayTime;
    }

    public void setVideoPlayTime(int videoPlayTime) {
        this.videoPlayTime = videoPlayTime;
    }

    public boolean isThumbsUp() {
        return isThumbsUp;
    }

    public void setThumbsUp(boolean thumbsUp) {
        isThumbsUp = thumbsUp;
    }

    public boolean isCoin() {
        return isCoin;
    }

    public void setCoin(boolean coin) {
        isCoin = coin;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    @Override
    public String toString() {
        return "VideoDaoBean{" +
                "index=" + index +
                ", videoId=" + videoId +
                ", videoTitle='" + videoTitle + '\'' +
                ", videoPlayTime=" + videoPlayTime +
                ", isThumbsUp=" + isThumbsUp +
                ", isCoin=" + isCoin +
                ", isCollection=" + isCollection +
                '}';
    }
}
