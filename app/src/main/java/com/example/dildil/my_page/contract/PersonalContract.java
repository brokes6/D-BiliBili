package com.example.dildil.my_page.contract;

import com.example.dildil.abstractclass.BaseModel;
import com.example.dildil.abstractclass.BaseView;
import com.example.dildil.base.BasePresenter;

public interface PersonalContract {

    interface View extends BaseView{


    }
    interface Model extends BaseModel{

    }
    abstract class Presenter extends BasePresenter<View,Model>{

    }
}
