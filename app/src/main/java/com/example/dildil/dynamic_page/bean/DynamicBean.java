package com.example.dildil.dynamic_page.bean;

import java.util.List;

public class DynamicBean {
    private String user_img;
    private String user_name;
    private String release_date;
    private String Text;
    private String video_cover;
    private String video_time;
    private List<String> ShowPictures;
    private int PictureStatus;
    private int video_bullet_chat;
    private int video_Playback_volume;
    private int comment_num;
    private int thumbs_num;

    public List<String> getShowPictures() {
        return ShowPictures;
    }

    public void setShowPictures(List<String> showPictures) {
        ShowPictures = showPictures;
    }

    public int getPictureStatus() {
        return PictureStatus;
    }

    public void setPictureStatus(int pictureStatus) {
        PictureStatus = pictureStatus;
    }

    public String getVideo_time() {
        return video_time;
    }

    public void setVideo_time(String video_time) {
        this.video_time = video_time;
    }

    public int getVideo_bullet_chat() {
        return video_bullet_chat;
    }

    public void setVideo_bullet_chat(int video_bullet_chat) {
        this.video_bullet_chat = video_bullet_chat;
    }

    public int getVideo_Playback_volume() {
        return video_Playback_volume;
    }

    public void setVideo_Playback_volume(int video_Playback_volume) {
        this.video_Playback_volume = video_Playback_volume;
    }

    public String getVideo_cover() {
        return video_cover;
    }

    public void setVideo_cover(String video_cover) {
        this.video_cover = video_cover;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getThumbs_num() {
        return thumbs_num;
    }

    public void setThumbs_num(int thumbs_num) {
        this.thumbs_num = thumbs_num;
    }
}
