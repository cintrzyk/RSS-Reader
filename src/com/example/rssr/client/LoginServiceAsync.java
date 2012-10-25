package com.example.rssr.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
	void isLogin(String login, String password, AsyncCallback<Boolean> callback);
}
