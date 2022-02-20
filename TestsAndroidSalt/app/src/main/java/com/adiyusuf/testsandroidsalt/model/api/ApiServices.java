package com.adiyusuf.testsandroidsalt.model.api;

import static com.adiyusuf.testsandroidsalt.model.api.EndPoint.TOP_HEADLINES;

import com.adiyusuf.testsandroidsalt.model.pojo.NewsListResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiServices {
    @GET(TOP_HEADLINES)
    Call<NewsListResponse> topHeadLines(@QueryMap Map<String, String> param);
}
