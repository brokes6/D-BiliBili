package com.example.dildil.login_page.bean;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;

@Entity
public class UserBean {

    @PrimaryKey(autoGenerate = true)
    private int mainId;

    private int code;

    @SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
    @Embedded
    private BeanData data;
    private String message;

    public int getMainId() {
        return mainId;
    }

    public void setMainId(int id) {
        this.mainId = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public BeanData getData() {
        return data;
    }

    public void setData(BeanData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class BeanData {
        private int id;
        private String account;
        private String password;
        private String username;
        private String email;
        private String img;
        private int followNum;
        private int fansNum;
        private String description;
        private int grade;
        private int coinNum;
        private String createTime;
        private String updateTime;
        private String readDynamicTime;
        private String readMessageTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getFollowNum() {
            return followNum;
        }

        public void setFollowNum(int followNum) {
            this.followNum = followNum;
        }

        public int getFansNum() {
            return fansNum;
        }

        public void setFansNum(int fansNum) {
            this.fansNum = fansNum;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public int getCoinNum() {
            return coinNum;
        }

        public void setCoinNum(int coinNum) {
            this.coinNum = coinNum;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getReadDynamicTime() {
            return readDynamicTime;
        }

        public void setReadDynamicTime(String readDynamicTime) {
            this.readDynamicTime = readDynamicTime;
        }

        public String getReadMessageTime() {
            return readMessageTime;
        }

        public void setReadMessageTime(String readMessageTime) {
            this.readMessageTime = readMessageTime;
        }
    }
}
