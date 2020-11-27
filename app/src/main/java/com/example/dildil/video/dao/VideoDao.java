package com.example.dildil.video.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dildil.video.bean.VideoDaoBean;

@Dao
public interface VideoDao {

    /**
     * 插入数据
     *
     * @param data
     */
    @Insert
    void insertAll(VideoDaoBean data);

//    @Insert
//    void insertIdDetails()

    /**
     * 删除数据
     *
     * @param data
     */
    @Delete
    void deleteAll(VideoDaoBean data);

    /**
     * 更新数据
     * 在数据更新的时候，必须传入一样类型的实体类，必须保函主键及子类
     *
     * @param data
     */
    @Update
    void updateVideo(VideoDaoBean data);

    /**
     * 更新对应index的值
     *
     * @param value
     * @param index
     */
    @Query("update videodaobean set isThumbsUp = :value WHERE `index` = (:index)")
    void setInThumbsUp(boolean value, int index);

    /**
     * 查询数据
     *
     * @return
     */
    @Query("SELECT * FROM videodaobean")
    LiveData<VideoDaoBean> getAll();

    @Query("SELECT videoId FROM videodaobean")
    LiveData<Integer> getAllVideoId();

    @Query("SELECT videoId FROM videodaobean WHERE `index` IN (:index)")
    int getVideoId(int index);

    @Query("SELECT videoPlayTime FROM videodaobean WHERE videoId IN (:id)")
    int getVideoTime(int id);

    @Query("SELECT isThumbsUp FROM videodaobean")
    boolean isThumbsUp();
}
