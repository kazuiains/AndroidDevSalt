package com.adiyusuf.testsandroidsalt.model.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.adiyusuf.testsandroidsalt.model.api.ApiServices;
import com.adiyusuf.testsandroidsalt.model.api.ConfigRetrofit;
import com.adiyusuf.testsandroidsalt.model.pojo.NewsListResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static final String TAG = Repository.class.getSimpleName();

    private final ApiServices service = ConfigRetrofit.service(ApiServices.class);

    private final MutableLiveData<NewsListResponse> listData = new MutableLiveData<>();

    //    list
    public MutableLiveData<NewsListResponse> getTopHeadLines(Map<String, String> request, boolean isLoadMore) {
        service.topHeadLines(request).enqueue(new Callback<NewsListResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsListResponse> call, @NonNull Response<NewsListResponse> response) {
                NewsListResponse listResponse = new NewsListResponse();
                listResponse.setLoadMore(isLoadMore);

                if (response.isSuccessful() && response.code() == 200 && response.body() != null && (response.body().getArticles() != null && !response.body().getArticles().isEmpty())) {
                    listResponse.setArticles(response.body().getArticles());

                    listData.postValue(listResponse);
                } else if (response.isSuccessful() && response.code() == 200 && response.body() != null && (response.body().getArticles() == null || response.body().getArticles().isEmpty())) {
                    listResponse.setArticles(null);
                    listResponse.setLast(true);

                    listData.postValue(listResponse);
                } else if (response.code() == 426 && response.body() != null && response.body().getCode().equals("maximumResultsReached")) {
                    listResponse.setArticles(null);
                    listResponse.setLast(true);

                    listData.postValue(listResponse);
                } else {
                    listResponse.setArticles(null);
                    listData.postValue(listResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsListResponse> call, @NonNull Throwable t) {
                NewsListResponse listResponse = new NewsListResponse();
                listResponse.setLoadMore(isLoadMore);
                listResponse.setArticles(null);

                listData.postValue(listResponse);
            }
        });
        return listData;
    }
}
