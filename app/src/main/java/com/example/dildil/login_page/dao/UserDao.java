package com.example.dildil.login_page.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dildil.login_page.bean.UserBean;

@Dao
public interface UserDao {

    /**
     * 插入数据
     *
     * @param users
     */
    @Insert
    void insertAll(UserBean users);

    /**
     * 删除数据
     *
     * @param user
     */
    @Delete
    void delete(UserBean user);

    //删全部
    @Query("DELETE FROM userbean")
    void deleteAll();

    /**
     * 更新数据（传入不一样的数据，将会自动将不一样的数据替换旧数据）
     *
     * @param users
     */
    @Update
    void updateUsers(UserBean users);

    /**
     * 查询数据
     *
     * @return
     */
    @Query("SELECT * FROM userbean")
    LiveData<UserBean> getAll();

    /**
     * 更具id来查询数据
     *
     * @param userId
     * @return
     */
    @Query("SELECT * FROM userbean WHERE id IN (:userId)")
    UserBean getUserById(int userId);

    /**
     * 更具id来查询用户名称
     *
     * @param userId
     * @return
     */
    @Query("SELECT userName FROM userbean WHERE id IN(:userId)")
    String getUserName(int userId);

    /**
     * 更具id来查询银币数量
     *
     * @param userId
     * @return
     */
    @Query("SELECT coinNum FROM userbean WHERE id IN(:userId)")
    Integer getUserCoin(int userId);

    /**
     * 更具id来查询用户头像
     *
     * @param userId
     * @return
     */
    @Query("SELECT img FROM userbean WHERE id IN(:userId)")
    String getUserImage(int userId);


    @Query("SELECT password FROM userbean WHERE id IN(:userId)")
    String getUserPassword(int userId);

}
