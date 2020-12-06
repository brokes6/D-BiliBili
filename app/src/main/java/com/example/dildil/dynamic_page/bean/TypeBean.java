package com.example.dildil.dynamic_page.bean;

import java.util.List;

public class TypeBean {

    private List<TypeData> date;

    public List<TypeData> getDate() {
        return date;
    }

    public void setDate(List<TypeData> date) {
        this.date = date;
    }

    public void addDate(List<AttentionDetailsBean.Data> topicBean) {
        this.date.add(1, new TypeData(topicBean));
    }

    public static class TypeData {

        private int type;
        private String title;
        private boolean mFull;
        private List<PursueBean> pursueBean;
        private List<AttentionDetailsBean.Data> topicBean;

        public TypeData(List<AttentionDetailsBean.Data> topicBean) {
            this.topicBean = topicBean;
            this.type = 1;
        }

        public void addPursueBean(List<PursueBean> pursueBean) {
            this.pursueBean = pursueBean;
        }

        public void setTopicBean(List<AttentionDetailsBean.Data> topicBean) {
            this.topicBean = topicBean;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean ismFull() {
            return mFull;
        }

        public void setmFull(boolean mFull) {
            this.mFull = mFull;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<PursueBean> getPursueBean() {
            return pursueBean;
        }

        public void setPursueBean(List<PursueBean> pursueBean) {
            this.pursueBean = pursueBean;
        }

        public List<AttentionDetailsBean.Data> getTopicBean() {
            return topicBean;
        }

        @Override
        public String toString() {
            return "TypeData{" +
                    "type=" + type +
                    ", title='" + title + '\'' +
                    ", mFull=" + mFull +
                    ", pursueBean=" + pursueBean +
                    ", topicBean=" + topicBean +
                    '}';
        }
    }

}
