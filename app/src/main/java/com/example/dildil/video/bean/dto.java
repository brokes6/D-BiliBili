package com.example.dildil.video.bean;

public class dto {
    private int num;
    private int vid;
    private String content;
    private int pid;
    private int puid;
    private String tableNameEnum;

    public dto(int vid) {
        this.vid = vid;
    }

    public dto(int num, int vid) {
        this.num = num;
        this.vid = vid;
    }

    public dto(String content, int pid, int puid, String type) {
        this.content = content;
        this.pid = pid;
        this.puid = puid;
        this.tableNameEnum = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPuid() {
        return puid;
    }

    public void setPuid(int puid) {
        this.puid = puid;
    }

    public String getTableNameEnum() {
        return tableNameEnum;
    }

    public void setTableNameEnum(String tableNameEnum) {
        this.tableNameEnum = tableNameEnum;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }
}
