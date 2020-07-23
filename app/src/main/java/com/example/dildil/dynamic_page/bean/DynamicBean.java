package com.example.dildil.dynamic_page.bean;

public class DynamicBean {
    private String user_img;
    private String user_name;
    private String release_date;
    private String Text;
    private int comment_num;
    private int thumbs_num;

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
