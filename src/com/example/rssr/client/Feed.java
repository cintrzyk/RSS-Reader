package com.example.rssr.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Feed implements IsSerializable{
	private String url;
	private String title;
	private String description;
	
	public Feed() {
		
	}
	
	
	public Feed(String url, String title, String description) {
		this.url = url;
		this.description = description;
		this.title = title;
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
	
	
}
