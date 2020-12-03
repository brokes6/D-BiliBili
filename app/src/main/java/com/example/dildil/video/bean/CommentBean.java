package com.example.dildil.video.bean;

public class CommentBean {
    private int code;
    private Data data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        private int total;
        //private List<CommentDetailBean> list;

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotal() {
            return total;
        }

//        public void setList(List<CommentDetailBean> list) {
//            this.list = list;
//        }
//
//        public List<CommentDetailBean> getList() {
//            return list;
//        }

    }

}