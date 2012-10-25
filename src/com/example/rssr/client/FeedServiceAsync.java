package com.example.rssr.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FeedServiceAsync {
	void addFeed(String input, AsyncCallback<String> callback);
	void updateFeedsTable(ArrayList<String> input, AsyncCallback<ArrayList<Feed>> callback);
}
