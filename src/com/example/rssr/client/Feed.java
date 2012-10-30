package com.example.rssr.client;

import java.util.Comparator;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Feed implements IsSerializable, Comparable<Feed>{
	private String url;
	private String title;
	private String description;
	private Date date;
	
	public Feed() {
		
	}
	
	
	public Feed(String url, String title, String description, Date date) {
		super();
		this.url = url;
		this.description = description;
		this.title = title;
		this.date = date;
	}

	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int compareTo(Feed compareFeed) {
		 
		Date compareDate = ((Feed) compareFeed).getDate(); 
 
		//ascending order
		return this.date.compareTo(compareDate);
 
		//descending order
		//return compareQuantity - this.quantity;
 
	}	
	
	public static Comparator<Feed> FruitNameComparator 
    = new Comparator<Feed>() {

		public int compare(Feed feed1, Feed feed2) {

			Date feedDate1 = feed1.getDate();
			Date feedDate2 = feed2.getDate();

			//ascending order
			return feedDate1.compareTo(feedDate2);

			//descending order
			//return fruitName2.compareTo(fruitName1);
		}

	};
	
}
