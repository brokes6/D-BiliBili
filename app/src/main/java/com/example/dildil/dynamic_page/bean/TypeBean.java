package com.example.dildil.dynamic_page.bean;

import com.example.dildil.channel_page.bean.BeInterestedBean;

import java.util.List;

public class TypeBean {
    private List<TypeData> date;

    public List<TypeData> getDate() {
        return date;
    }

    public void setDate(List<TypeData> date) {
        this.date = date;
    }

    public static class TypeData {
        private int type;
        private String title;
        private boolean mFull;
        private List<PursueBean> pursueBean;
        private List<BeInterestedBean> topicBean;

        public void addPursueBean(List<PursueBean> pursueBean) {
            this.pursueBean = pursueBean;
        }

        public void addBeInterestedBean(List<BeInterestedBean> topicBean) {
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

        public List<BeInterestedBean> getTopicBean() {
            return topicBean;
        }

        public void setTopicBean(List<BeInterestedBean> topicBean) {
            this.topicBean = topicBean;
        }
    }

}
