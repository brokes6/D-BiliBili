package com.example.dildil.dynamic_page.bean;

import java.util.List;

public class DynamicBean {
    private int code;
    private List<Datas> data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Datas> getData() {
        return data;
    }

    public void setData(List<Datas> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Datas {
        private int id;
        private int uid;
        private int oid;
        private int forwardNum;
        private int praiseNum;
        private int commentNum;
        private String content;
        private String imgs;
        private String upName;
        private String upImg;
        private String type;
        private String createTime;
        private ObjectData object;
        private boolean collection;
        private boolean forward;
        private boolean praise;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
            this.oid = oid;
        }

        public int getForwardNum() {
            return forwardNum;
        }

        public void setForwardNum(int forwardNum) {
            this.forwardNum = forwardNum;
        }

        public int getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(int praiseNum) {
            this.praiseNum = praiseNum;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUpName() {
            return upName;
        }

        public void setUpName(String upName) {
            this.upName = upName;
        }

        public String getUpImg() {
            return upImg;
        }

        public void setUpImg(String upImg) {
            this.upImg = upImg;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public ObjectData getObject() {
            return object;
        }

        public void setObject(ObjectData object) {
            this.object = object;
        }

        public boolean isCollection() {
            return collection;
        }

        public void setCollection(boolean collection) {
            this.collection = collection;
        }

        public boolean isForward() {
            return forward;
        }

        public void setForward(boolean forward) {
            this.forward = forward;
        }

        public boolean isPraise() {
            return praise;
        }

        public void setPraise(boolean praise) {
            this.praise = praise;
        }

        public class ObjectData {
            private int id;
            private int uid;
            private int playNum;
            private int danmuNum;
            private int length;
            private String upImg;
            private String upName;
            private String title;
            private String cover;
            private String previewUrl;
            private boolean praise;
            private String urls;

            public String getUrls() {
                return urls;
            }

            public void setUrls(String urls) {
                this.urls = urls;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getPlayNum() {
                return playNum;
            }

            public void setPlayNum(int playNum) {
                this.playNum = playNum;
            }

            public int getDanmuNum() {
                return danmuNum;
            }

            public void setDanmuNum(int danmuNum) {
                this.danmuNum = danmuNum;
            }

            public int getLength() {
                return length;
            }

            public void setLength(int length) {
                this.length = length;
            }

            public String getUpImg() {
                return upImg;
            }

            public void setUpImg(String upImg) {
                this.upImg = upImg;
            }

            public String getUpName() {
                return upName;
            }

            public void setUpName(String upName) {
                this.upName = upName;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getPreviewUrl() {
                return previewUrl;
            }

            public void setPreviewUrl(String previewUrl) {
                this.previewUrl = previewUrl;
            }

            public boolean isPraise() {
                return praise;
            }

            public void setPraise(boolean praise) {
                this.praise = praise;
            }
        }
    }
}
