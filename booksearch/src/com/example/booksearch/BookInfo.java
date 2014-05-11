package com.example.booksearch;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class BookInfo implements Parcelable {
	private String title = "";
	private Bitmap cover;
	private String author = "";
	private String publisher = "";
	private String publishDate = "";
	private String isbn = "";
	private String summary = "";

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public static final Parcelable.Creator<BookInfo> CREATOR = 	new Creator<BookInfo>(){
		
		public BookInfo createFromParcel(Parcel source){
			BookInfo bookInfo = new BookInfo();
			bookInfo.title = source.readString();
			bookInfo.cover = source.readParcelable(Bitmap.class.getClassLoader());
			bookInfo.author = source.readString();
			bookInfo.publisher = source.readString();
			bookInfo.publishDate = source.readString();
			bookInfo.isbn = source.readString();
			bookInfo.summary = source.readString();
			return bookInfo;
		}
		
		public BookInfo[] newArray(int size){
			return new BookInfo[size];
		}

	};

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.writeString(title);
		arg0.writeParcelable(cover, arg1);
		arg0.writeString(author);
		arg0.writeString(publisher);
		arg0.writeString(publishDate);
		arg0.writeString(isbn);
		arg0.writeString(summary);

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Bitmap getCover() {
		return cover;
	}

	public void setCover(Bitmap cover) {
		this.cover = cover;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getISBN() {
		return isbn;
	}

	public void setISBN(String isbn) {
		this.isbn = isbn;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	

}
