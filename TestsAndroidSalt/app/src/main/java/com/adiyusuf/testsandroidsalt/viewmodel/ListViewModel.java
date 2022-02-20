package com.adiyusuf.testsandroidsalt.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adiyusuf.testsandroidsalt.model.pojo.NewsListResponse;
import com.adiyusuf.testsandroidsalt.model.repository.Repository;

import java.util.Map;

public class ListViewModel extends ViewModel {
    private static final String TAG = ListViewModel.class.getSimpleName();

    private final Repository repository = new Repository();

    private MutableLiveData<NewsListResponse> listData = new MutableLiveData<>();

    public void reqTopHeadLines(Map<String, String> request, boolean isLoadMore) {
        listData = repository.getTopHeadLines(request, isLoadMore);
    }

    public LiveData<NewsListResponse> respTopHeadLines() {
        return listData;
    }
}
