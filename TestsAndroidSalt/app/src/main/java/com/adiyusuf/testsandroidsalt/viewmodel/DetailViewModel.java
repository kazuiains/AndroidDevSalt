package com.adiyusuf.testsandroidsalt.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adiyusuf.testsandroidsalt.model.pojo.ArticlesItem;

public class DetailViewModel extends ViewModel {
    private static final String TAG = ListViewModel.class.getSimpleName();

    private final MutableLiveData<ArticlesItem> listData = new MutableLiveData<>();

    public void setDetail(ArticlesItem data) {
        listData.postValue(data);
    }

    public LiveData<ArticlesItem> getDetail() {
        return listData;
    }
}
