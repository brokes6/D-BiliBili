package com.example.dildil.video.bean;

public class VideoDetailsBean {
    private int code;
    private BeanData data;
    private String message;

    /**
     *  {
     *         "code": 200,
     *             "data": {
     *         "id": 43,
     *                 "uid": 1,
     *                 "playNum": 1,
     *                 "danmuNum": 0,
     *                 "commentNum": 0,
     *                 "praiseNum": 0,
     *                 "coinNum": 0,
     *                 "categoryPId": 1,
     *                 "categoryId": 2,
     *                 "length": 213,
     *                 "upImg": null,
     *                 "upName": null,
     *                 "categoryPName": "音乐",
     *                 "categoryName": "原创音乐",
     *                 "title": "【罗翔】Despacito",
     *                 "urls": "http://116.196.105.203:9000/dalidali/43c93530-6df9-4938-87ed-ca97d811c516.mp4,http://116.196.105.203:9000/dalidali//43c93530-6df9-4938-87ed-ca97d811c516360.mp4,http://116.196.105.203:9000/dalidali//43c93530-6df9-4938-87ed-ca97d811c516480.mp4,http://116.196.105.203:9000/dalidali//43c93530-6df9-4938-87ed-ca97d811c516720.mp4",
     *                 "cover": "http://116.196.105.203:9000/dalidali/43c93530-6df9-4938-87ed-ca97d811c516.jpg",
     *                 "description": "该视频通过爬虫爬取下来，作为测试数据，如果侵权请联系我，我立马删~",
     *                 "tags": "测试,热门",
     *                 "resolutionState": null,
     *                 "updateTime": "2020-08-27T13:28:15.000+0000",
     *                 "log": null
     *     },
     *         "message": "操作成功！"
     *     }
     */

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

    public static class BeanData{
        private int id;
        private int uid;
        private int playNum;
        private int danmuNum;
        private int commentNum;
        private int praiseNum;
        private int coinNum;
        private int categoryPId;
        private int categoryId;
        private int length;
        private String screenType;
        private String upImg;
        private String upName;
        private String categoryPName;
        private String categoryName;
        private String title;
        private String urls;
        private String cover;
        private String previewUrl;
        private String description;
        private String tags;
        private String resolutionState;
        private String updateTime;
        private LogData log;

        public String getScreenType() {
            return screenType;
        }

        public void setScreenType(String screenType) {
            this.screenType = screenType;
        }

        public LogData getLog() {
            return log;
        }

        public void setLog(LogData log) {
            this.log = log;
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

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public int getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(int praiseNum) {
            this.praiseNum = praiseNum;
        }

        public int getCoinNum() {
            return coinNum;
        }

        public void setCoinNum(int coinNum) {
            this.coinNum = coinNum;
        }

        public int getCategoryPId() {
            return categoryPId;
        }

        public void setCategoryPId(int categoryPId) {
            this.categoryPId = categoryPId;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
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

        public String getCategoryPName() {
            return categoryPName;
        }

        public void setCategoryPName(String categoryPName) {
            this.categoryPName = categoryPName;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrls() {
            return urls;
        }

        public void setUrls(String urls) {
            this.urls = urls;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getResolutionState() {
            return resolutionState;
        }

        public void setResolutionState(String resolutionState) {
            this.resolutionState = resolutionState;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public class LogData{
            private int id;
            private int uid;
            private int vid;
            private boolean isPraise;
            private boolean isCollection;
            private int coinNum;

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

            public int getVid() {
                return vid;
            }

            public void setVid(int vid) {
                this.vid = vid;
            }

            public boolean isPraise() {
                return isPraise;
            }

            public void setPraise(boolean praise) {
                isPraise = praise;
            }

            public boolean isCollection() {
                return isCollection;
            }

            public void setCollection(boolean collection) {
                isCollection = collection;
            }

            public int getCoinNum() {
                return coinNum;
            }

            public void setCoinNum(int coinNum) {
                this.coinNum = coinNum;
            }
        }
    }
}
