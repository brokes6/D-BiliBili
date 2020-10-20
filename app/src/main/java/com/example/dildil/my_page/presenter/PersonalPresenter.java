package com.example.dildil.my_page.presenter;

import com.example.dildil.my_page.contract.PersonalContract;
import com.example.dildil.my_page.model.PersonalModel;

import javax.inject.Inject;

public class PersonalPresenter extends PersonalContract.Presenter {

    @Inject
    public PersonalPresenter(){

    }

    public void attachView(PersonalContract.View view) {
        this.mView = view;
        this.mModel = new PersonalModel();
    }

    public void detachView() {
        this.mView = null;
    }

}
