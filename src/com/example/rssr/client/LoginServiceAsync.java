package com.example.rssr.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
	void isValidUserPassword(String login, String password,
			AsyncCallback<String> callback);

	void isTokenValid(String token, AsyncCallback<Boolean> callback);
}
