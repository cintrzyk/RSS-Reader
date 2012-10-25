package com.example.rssr.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("feedService")
public interface FeedService extends RemoteService {
	String addFeed(String url);
	ArrayList<Feed> updateFeedsTable(ArrayList<String> feedsUrl);
}
