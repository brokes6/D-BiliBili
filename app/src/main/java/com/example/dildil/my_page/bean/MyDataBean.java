package com.example.dildil.my_page.bean;

public class MyDataBean {

    private String username;
    private String userImg;
    private boolean isMember;
    private int Dynamic;
    private int Coin;
    private int Follow;
    private int Fans;
    private String MemberTime;

    public String getMemberTime() {
        return MemberTime;
    }

    public void setMemberTime(String memberTime) {
        MemberTime = memberTime;
    }

    public int getFollow() {
        return Follow;
    }

    public void setFollow(int follow) {
        Follow = follow;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    public int getDynamic() {
        return Dynamic;
    }

    public void setDynamic(int dynamic) {
        Dynamic = dynamic;
    }

    public int getCoin() {
        return Coin;
    }

    public void setCoin(int coin) {
        Coin = coin;
    }

    public int getFans() {
        return Fans;
    }

    public void setFans(int fans) {
        Fans = fans;
    }
}
