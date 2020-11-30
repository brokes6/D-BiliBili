package com.example.dildil.channel_page.contract;

import com.example.dildil.abstractclass.BaseModel;
import com.example.dildil.abstractclass.BaseView;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.channel_page.bean.PartitionBean;

import io.reactivex.Observable;

public interface PartitionContract {
    interface View extends BaseView {

        void onGetCategorySuccess(PartitionBean partitionBean);

        void onGetCategoryFail(String e);
    }

    interface Model extends BaseModel {

        Observable<PartitionBean> getCategory();

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void getCategory();

    }
}
