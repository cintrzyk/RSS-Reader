package com.example.rssr.server;

import java.util.ArrayList;
import java.util.List;

import org.horrabin.horrorss.RssChannelBean;
import org.horrabin.horrorss.RssFeed;
import org.horrabin.horrorss.RssItemBean;
import org.horrabin.horrorss.RssParser;

import com.example.rssr.client.Feed;
import com.example.rssr.client.FeedService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class FeedServiceImpl extends RemoteServiceServlet implements
		FeedService {

	private static final long serialVersionUID = 1L;

	@Override
	public String addFeed(String url) {
		RssFeed feed = getFeed(url);
		String feedChannelTitle = getChannelTitle(feed);
		
		return feedChannelTitle;
	}
	
	@Override
	public ArrayList<Feed> updateFeedsTable(ArrayList<String> feedsUrl) {
		ArrayList<Feed> feeds = new ArrayList<Feed>();
		for (String url : feedsUrl) {
			RssFeed feed = getFeed(url);
			List<RssItemBean> items = feed.getItems();
	        for (int i=0; i<items.size(); i++){
	             RssItemBean item = items.get(i); 
	             feeds.add(new Feed(item.getLink(), item.getTitle(), item.getDescription()));
	        }
		}
		return feeds;
	}
	
	private RssFeed getFeed(String url) {
		RssParser rss = new RssParser();
		try{
			RssFeed feed = rss.load(url); 
			return feed;
		}catch(Exception e){	}
		return null;
	}
	
	private String getChannelTitle(RssFeed feed) {
		RssChannelBean channel = feed.getChannel();
	    return channel.getTitle();
	}

}
