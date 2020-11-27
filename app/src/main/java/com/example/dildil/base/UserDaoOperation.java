package com.example.dildil.base;

import android.content.Context;
import android.os.AsyncTask;

import com.example.dildil.MyApplication;
import com.example.dildil.login_page.bean.UserBean;
import com.example.dildil.video.bean.VideoDaoBean;

public class UserDaoOperation {
    private static AppDatabase db;

    public UserDaoOperation() {
    }

    public UserDaoOperation(Context context) {
        db = MyApplication.getDatabase(context);
    }

    public void setUserDetail(UserBean userBean) {
        new insertAll(userBean).execute(userBean);
    }

    public void setVideoDetail(VideoDaoBean videoDetail) {
        new insertVideo(videoDetail).execute(videoDetail);
    }

    public void UpVideoDetail(VideoDaoBean videoDetail) {
        new UpdateVideo(videoDetail).execute(videoDetail);
    }


    public void setVideo(VideoDaoBean videoDetail) {
        new setVideo(videoDetail).execute(videoDetail);
    }

    public void delUserDetail() {
        new DeleteUser();
    }

    public static class insertAll extends AsyncTask<UserBean, Void, Void> {
        private UserBean userBean;

        public insertAll(UserBean userBean) {
            this.userBean = userBean;
        }

        @Override
        protected Void doInBackground(UserBean... userBeans) {
            db.userDao().insertAll(userBean);
            return null;
        }
    }

    public static class insertVideo extends AsyncTask<VideoDaoBean, Void, Void> {
        private VideoDaoBean videoDaoBean;

        public insertVideo(VideoDaoBean videoDaoBean) {
            this.videoDaoBean = videoDaoBean;
        }

        @Override
        protected Void doInBackground(VideoDaoBean... videoDaoBeans) {
            db.videoDao().insertAll(videoDaoBean);
            return null;
        }
    }

    public static class DeleteUser extends AsyncTask<Void, Void, Void> {
        private UserBean userBean;

        public DeleteUser() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.userDao().deleteAll();
            return null;
        }
    }

    public static class UpdateUser extends AsyncTask<UserBean, Void, Void> {
        private UserBean userBean;

        public UpdateUser(UserBean userBean) {
            this.userBean = userBean;
        }

        @Override
        protected Void doInBackground(UserBean... userBeans) {
            db.userDao().delete(userBean);
            return null;
        }
    }

    public static class UpdateVideo extends AsyncTask<VideoDaoBean, Void, Void> {
        private VideoDaoBean userBean;

        public UpdateVideo(VideoDaoBean userBean) {
            this.userBean = userBean;
        }

        @Override
        protected Void doInBackground(VideoDaoBean... userBeans) {
            db.videoDao().updateVideo(userBean);
            return null;
        }
    }

    public static class setVideo extends AsyncTask<VideoDaoBean, Void, Void> {
        private VideoDaoBean videoDaoBean;

        public setVideo(VideoDaoBean videoDaoBean) {
            this.videoDaoBean = videoDaoBean;
        }

        @Override
        protected Void doInBackground(VideoDaoBean... videoDaoBeans) {
            db.videoDao().insertAll(videoDaoBean);
            return null;
        }
    }
}
