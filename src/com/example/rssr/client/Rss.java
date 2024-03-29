package com.example.rssr.client;

import java.util.ArrayList;
import com.example.rssr.shared.FieldVerifier;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Rss implements EntryPoint {

	private final FeedServiceAsync feedService = GWT.create(FeedService.class);
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);
	
	private ArrayList<String> feedsUrl = new ArrayList<String>();

	//LOGIN FORM
	private TextBox loginTextBox = new TextBox();
	private PasswordTextBox passwordTextBox = new PasswordTextBox();
	private Button loginButton = new Button("sign in");
	private Button logoutButton = new Button("logout");

	private Label loginStatusLabel = new Label();
	private String token = "";
	private Boolean loggedIn = false;

	
	//TABLES
	private FlexTable feedsFlexTable = new FlexTable();
	private FlexTable channelsFlexTable = new FlexTable();
	//PANELS
	private VerticalPanel addPanel = new VerticalPanel();
	
	private TextBox addChannelTextBox = new TextBox();
	private Button addChannelButton = new Button("Add channel");
	
	private Button refreshFeedsButton = new Button();
	private Label errorLabel = new Label();
		
	
	@Override
	public void onModuleLoad() {
		channelsFlexTable.setText(0, 0, "Channel");
		channelsFlexTable.setText(0, 1, "URL");
		channelsFlexTable.setText(0, 2, "Delete");
		channelsFlexTable.setVisible(false);
		channelsFlexTable.setStyleName("table table-bordered");
	    refreshFeedsButton.setVisible(false);
	    
	    //LOGIN FORM
	    loginTextBox.setStyleName("input-small");
	    passwordTextBox.setStyleName("input-small");
	    loginButton.setStyleName("btn btn-success");
	    loginButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				checkLogin();
			}
		});
	    logoutButton.setStyleName("btn btn-danger");
	    logoutButton.setVisible(false);
	    logoutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				logout();
			}
		}); 
	    
	    RootPanel.get("loginForm").add(loginTextBox);
	    RootPanel.get("loginForm").add(passwordTextBox);
	    RootPanel.get("loginForm").add(loginButton);
	    RootPanel.get("loginForm").add(logoutButton);
	    RootPanel.get("loginDiv").add(loginStatusLabel);
	    loginStatusLabel.setText("please sign in");
	    
	    addPanel.add(addChannelTextBox);
	    addPanel.add(addChannelButton);
	    RootPanel.get("error").add(errorLabel);
	    RootPanel.get("addFeed").add(addPanel);
	    RootPanel.get("refreshSpan").add(refreshFeedsButton);
	    refreshFeedsButton.setStyleName("btn btn-info");
	    refreshFeedsButton.setHTML("<i class='icon-refresh'></i>");
	    refreshFeedsButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				updateFeedsFlexTable();
			}
		});
	   
	    RootPanel.get("channelList").add(channelsFlexTable);
	    RootPanel.get("feedList").add(feedsFlexTable);
	    
	    addChannelTextBox.setFocus(true);
	    addChannelTextBox.setVisible(false);
	    RootPanel.get("titleAdd").setVisible(false);
	    addChannelButton.setVisible(false);
	    addChannelButton.setStyleName("btn btn-large btn-primary");
		
	    addChannelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addChannel();	
			}
		});

		addChannelTextBox.addKeyUpHandler(new KeyUpHandler() {
	      public void onKeyUp(KeyUpEvent event) {
		  		errorLabel.setText("");
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addChannel();				
				}
			}
	    });
		
	}
	
	//add feeds channel
	private void addChannel() {
		final String url = addChannelTextBox.getText();
		
		// Don't add the feed if it's already in the table.
		if (feedsUrl.contains(url)){
			errorLabel.setText("channel allready added");
			addChannelTextBox.setText("");
			return;
		}
		
		//validation
		if (!FieldVerifier.isValidUrl(url)) {
			errorLabel.setText("URL is not valid");
			addChannelTextBox.setFocus(true);
			addChannelTextBox.setSelectionRange(0, addChannelTextBox.getText().length());
			return;
		}
		
		errorLabel.setText("");
		addChannelTextBox.setText("");
		
		//finally add new feed channel
		feedService.addFeed(url, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				// Add the feed to the table.
				final int row = channelsFlexTable.getRowCount();
			    feedsUrl.add(url);
			    channelsFlexTable.setText(row, 0, result);
			    channelsFlexTable.setHTML(row, 1, url);
			    channelsFlexTable.setVisible(true);
			    refreshFeedsButton.setVisible(true);

			    // Add a button to remove this feed from the table.
			    Button removeFeedButton = new Button("x");
			    removeFeedButton.setStyleName("btn btn-mini btn-danger");
			    removeFeedButton.addClickHandler(new ClickHandler() {
			      public void onClick(ClickEvent event) {
			        int removedIndex = feedsUrl.indexOf(url);
			        feedsUrl.remove(removedIndex);        
			        channelsFlexTable.removeRow(removedIndex + 1);
		    		
			        if (feedsUrl.isEmpty()){
			        	channelsFlexTable.setVisible(false);
					    refreshFeedsButton.setVisible(false);
			        }
			        updateFeedsFlexTable();
			      }
			    });
			    channelsFlexTable.setWidget(row, 2, removeFeedButton);
			    
			    updateFeedsFlexTable();
			}					
			@Override
			public void onFailure(Throwable caught) {
				errorLabel.setText("Failed to connect channel. Please check URL.");
			}
		});
		
	}
	
	// update feeds table
	private void updateFeedsFlexTable(){
		
		feedService.updateFeedsTable(feedsUrl, new AsyncCallback<ArrayList<Feed>>() {
			
			@Override
			public void onSuccess(ArrayList<Feed> result) {
				feedsFlexTable.removeAllRows();
				for (Feed feed : result) {
					int row = feedsFlexTable.getRowCount()+1;
					feedsFlexTable.setText(row, 0, feed.getTitle());
					feedsFlexTable.setHTML(row, 1, "<a href='"+feed.getUrl()+"'>"+feed.getUrl()+"</a>");
					feedsFlexTable.setHTML(row, 2, feed.getDescription());
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				errorLabel.setText("Remote Procedure Call - Failure to update channel list");
			}

		
		});
	}
	
	//check if user is login
	private void checkLogin(){
		
		//validation
		if (!FieldVerifier.isValidName(loginTextBox.getText())) {
			errorLabel.setText("Name is not valid");
			loginTextBox.setFocus(true);
			return;
		}
		

		if (!FieldVerifier.isValidPassword(passwordTextBox.getText())) {
			errorLabel.setText("Password is not valid");
			passwordTextBox.setFocus(true);
			return;
		}
		
		loginService.isValidUserPassword(loginTextBox.getText(), passwordTextBox.getText(), new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				if (result != null)
					if (result.length() > 0) {
						setLoggedIn(true);
						setToken(result);
					} else {
						setLoggedIn(false);
						setToken(result);
					}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				//loginStatusLabel.setText("failed to login");
				setLoggedIn(false);
			}
		});
	}
	
	public Boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Boolean loggedIn) {
		if (loggedIn){
			loginStatusLabel.setText("Hello admin");
		    logoutButton.setVisible(true);
		    loginButton.setVisible(false);
		    loginTextBox.setVisible(false);
		    passwordTextBox.setVisible(false);
		    addChannelTextBox.setVisible(true);
		    addChannelButton.setVisible(true);
		    RootPanel.get("titleAdd").setVisible(true);
		    feedsFlexTable.setVisible(true);
		    channelsFlexTable.setVisible(true);

		}
		else loginStatusLabel.setText("Please log in");
		this.loggedIn = loggedIn;
	}
	
	public void logout(){
		setToken("");
		setLoggedIn(false);
	    logoutButton.setVisible(false);
	    loginButton.setVisible(true);
	    loginTextBox.setVisible(true);
	    passwordTextBox.setVisible(true);
	    addChannelTextBox.setVisible(false);
	    addChannelButton.setVisible(false);
	    RootPanel.get("titleAdd").setVisible(false);
	    feedsFlexTable.setVisible(false);
	    channelsFlexTable.setVisible(false);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
