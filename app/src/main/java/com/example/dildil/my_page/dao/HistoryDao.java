package com.example.dildil.my_page.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dildil.my_page.bean.HistoryBean;

import java.util.List;

@Dao
public interface HistoryDao {

    /**
     * 插入数据
     *
     * @param historyBean
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(HistoryBean historyBean);

    /**
     * 删除数据
     */
    //删全部
    @Query("DELETE FROM HistoryBean")
    void deleteAll();

    /**
     * 更新数据（传入不一样的数据，将会自动将不一样的数据替换旧数据）
     *
     * @param historyBean
     */
    @Update
    void updateUsers(HistoryBean historyBean);

    /**
     * 查询数据
     *
     * @return
     */
    @Query("SELECT * FROM HistoryBean")
    LiveData<List<HistoryBean>> getAll();

}
