package com.example.dildil.base;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dildil.MyApplication;
import com.example.dildil.login_page.bean.UserBean;
import com.example.dildil.my_page.bean.HistoryBean;
import com.example.dildil.video.bean.VideoDaoBean;

public class UserDaoOperation {
    private static AppDatabase db;
    private static UserDaoOperation instance;

    public static UserDaoOperation getDatabase(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    db = MyApplication.getDatabase(context);
                    instance = new UserDaoOperation();
                }
            }
        }
        return instance;
    }

    /**
     * 对外开放类
     *
     * @param userBean
     */
    public void setUserDetail(UserBean userBean) {
        new insertAll(userBean).execute(userBean);
    }

    public void setVideoDetail(VideoDaoBean videoDetail) {
        new insertVideo(videoDetail).execute(videoDetail);
    }

    public void UpVideoDetail(VideoDaoBean videoDetail) {
        new UpdateVideo(videoDetail).execute(videoDetail);
    }

    public void delUserDetail() {
        new DeleteUser();
    }

    public void UpdateCoin(int coin) {
        new UpdateCoinUser(coin).execute(coin);
    }

    public void insertHistory(HistoryBean historyBean) {
        new insertHistory(historyBean).execute(historyBean);
    }

    public void dellHistory() {
        new dellHistory();
    }

    public void upHistory(HistoryBean historyBean) {
        new upHistory(historyBean).execute(historyBean);
    }


    /**
     * ***********************************************内部方法****************************************
     */

    /**
     * 插入用户数据
     */
    private static class insertAll extends AsyncTask<UserBean, Void, Void> {
        private final UserBean userBean;

        public insertAll(UserBean userBean) {
            this.userBean = userBean;
        }

        @Override
        protected Void doInBackground(UserBean... userBeans) {
            db.userDao().insertAll(userBean);
            return null;
        }
    }

    /**
     * 插入视频详情
     */
    private static class insertVideo extends AsyncTask<VideoDaoBean, Void, Void> {
        private final VideoDaoBean videoDaoBean;

        public insertVideo(VideoDaoBean videoDaoBean) {
            this.videoDaoBean = videoDaoBean;
        }

        @Override
        protected Void doInBackground(VideoDaoBean... videoDaoBeans) {
            db.videoDao().insertAll(videoDaoBean);
            return null;
        }
    }

    /**
     * 删除用户数据
     */
    private static class DeleteUser extends AsyncTask<Void, Void, Void> {
        private UserBean userBean;

        public DeleteUser() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.userDao().deleteAll();
            return null;
        }
    }

    /**
     * 更改用户数据
     */
    private static class UpdateUser extends AsyncTask<UserBean, Void, Void> {
        private final UserBean userBean;

        public UpdateUser(UserBean userBean) {
            this.userBean = userBean;
        }

        @Override
        protected Void doInBackground(UserBean... userBeans) {
            db.userDao().updateUsers(userBean);
            return null;
        }
    }

    /**
     * 更新当前用户银币数量
     */
    private static class UpdateCoinUser extends AsyncTask<Integer, Void, Void> {
        private final int coin;

        public UpdateCoinUser(int coin) {
            this.coin = coin;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            Log.e("why", "doInBackground: 进行数据库操作，减少银币数量" + coin);
            db.userDao().setCoin(coin);
            return null;
        }
    }

    /**
     * 更新当前视频详情
     */
    private static class UpdateVideo extends AsyncTask<VideoDaoBean, Void, Void> {
        private final VideoDaoBean userBean;

        public UpdateVideo(VideoDaoBean userBean) {
            this.userBean = userBean;
        }

        @Override
        protected Void doInBackground(VideoDaoBean... userBeans) {
            db.videoDao().updateVideo(userBean);
            return null;
        }
    }

    /**
     * 插入历史
     */
    private static class insertHistory extends AsyncTask<HistoryBean, Void, Void> {
        private final HistoryBean historyBean;

        public insertHistory(HistoryBean historyBean) {
            this.historyBean = historyBean;
        }

        @Override
        protected Void doInBackground(HistoryBean... historyBeans) {
            db.historyDao().insertAll(historyBean);
            return null;
        }
    }

    /**
     * 删除全部历史
     */
    private static class dellHistory extends AsyncTask<Void, Void, Void> {

        public dellHistory() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.historyDao().deleteAll();
            return null;
        }
    }

    /**
     * 更改历史（基本用不上）
     */
    private static class upHistory extends AsyncTask<HistoryBean, Void, Void> {
        private final HistoryBean historyBean;

        public upHistory(HistoryBean historyBean) {
            this.historyBean = historyBean;

        }

        @Override
        protected Void doInBackground(HistoryBean... historyBeans) {
            db.historyDao().updateUsers(historyBean);
            return null;
        }
    }

}
