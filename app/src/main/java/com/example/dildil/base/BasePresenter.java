package com.example.dildil.base;


import com.example.dildil.abstractclass.BaseModel;
import com.example.dildil.abstractclass.BaseView;

public class BasePresenter<V extends BaseView, M extends BaseModel> {

    protected V mView;
    protected M mModel;


    public void unSubscribe(){
        if(mView != null){
            mView = null;
        }
    }
}
