package com.example.dildil.base;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.dildil.login_page.bean.UserBean;
import com.example.dildil.login_page.dao.UserDao;
import com.example.dildil.my_page.bean.HistoryBean;
import com.example.dildil.my_page.dao.HistoryDao;
import com.example.dildil.video.bean.VideoDaoBean;
import com.example.dildil.video.dao.VideoDao;

/**
 * 数据库版本，升级方法，都在这里写
 */
@Database(entities = {UserBean.class, VideoDaoBean.class, HistoryBean.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract VideoDao videoDao();

    public abstract HistoryDao historyDao();

//    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//           database.execSQL("ALTER TABLE bookBean ADD COLUMN  index_name'BookName' ");
//        }
//    };
}
