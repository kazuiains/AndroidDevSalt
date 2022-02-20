package com.adiyusuf.testsandroidsalt.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Source implements Parcelable {

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.name);
		dest.writeString(this.id);
	}

	public void readFromParcel(Parcel source) {
		this.name = source.readString();
		this.id = source.readString();
	}

	public Source() {
	}

	protected Source(Parcel in) {
		this.name = in.readString();
		this.id = in.readString();
	}

	public static final Parcelable.Creator<Source> CREATOR = new Parcelable.Creator<Source>() {
		@Override
		public Source createFromParcel(Parcel source) {
			return new Source(source);
		}

		@Override
		public Source[] newArray(int size) {
			return new Source[size];
		}
	};
}