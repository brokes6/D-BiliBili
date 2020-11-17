package com.example.dildil.my_page.contract;

import com.example.dildil.abstractclass.BaseModel;
import com.example.dildil.abstractclass.BaseView;
import com.example.dildil.base.BasePresenter;
import com.example.dildil.login_page.bean.UserBean;

import io.reactivex.Observable;

public interface PersonalContract {

    interface View extends BaseView{

        void onGetFindUserDetailsSuccess(UserBean userBean);

        void onGetFindUserDetailsFail(String e);

    }
    interface Model extends BaseModel{

        Observable<UserBean> getFindDetails(int uid);
    }
    abstract class Presenter extends BasePresenter<View,Model>{

        public abstract void getFindDetails(int uid);

    }
}
