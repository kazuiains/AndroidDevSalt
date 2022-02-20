package com.adiyusuf.testsandroidsalt.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NewsListResponse implements Parcelable {

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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.totalResults);
		dest.writeTypedList(this.articles);
		dest.writeString(this.status);
		dest.writeString(this.code);
		dest.writeByte(this.isLoadMore ? (byte) 1 : (byte) 0);
		dest.writeByte(this.isLast ? (byte) 1 : (byte) 0);
	}

	public void readFromParcel(Parcel source) {
		this.totalResults = source.readInt();
		this.articles = source.createTypedArrayList(ArticlesItem.CREATOR);
		this.status = source.readString();
		this.code = source.readString();
		this.isLoadMore = source.readByte() != 0;
		this.isLast = source.readByte() != 0;
	}

	public NewsListResponse() {
	}

	protected NewsListResponse(Parcel in) {
		this.totalResults = in.readInt();
		this.articles = in.createTypedArrayList(ArticlesItem.CREATOR);
		this.status = in.readString();
		this.code = in.readString();
		this.isLoadMore = in.readByte() != 0;
		this.isLast = in.readByte() != 0;
	}

	public static final Parcelable.Creator<NewsListResponse> CREATOR = new Parcelable.Creator<NewsListResponse>() {
		@Override
		public NewsListResponse createFromParcel(Parcel source) {
			return new NewsListResponse(source);
		}

		@Override
		public NewsListResponse[] newArray(int size) {
			return new NewsListResponse[size];
		}
	};
}