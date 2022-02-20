package com.adiyusuf.testsandroidsalt.model.pojo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NewsListResponse{

	@SerializedName("totalResults")
	private int totalResults;

	@SerializedName("articles")
	private List<ArticlesItem> articles;

	@SerializedName("status")
	private String status;

	@SerializedName("code")
	private String code;

	private boolean isLoadMore;

	private boolean isLast = false;

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean last) {
		isLast = last;
	}

	public boolean isLoadMore() {
		return isLoadMore;
	}

	public void setLoadMore(boolean loadMore) {
		isLoadMore = loadMore;
	}

	public void setTotalResults(int totalResults){
		this.totalResults = totalResults;
	}

	public int getTotalResults(){
		return totalResults;
	}

	public void setArticles(List<ArticlesItem> articles){
		this.articles = articles;
	}

	public List<ArticlesItem> getArticles(){
		return articles;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}