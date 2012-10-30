package com.example.rssr.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("loginService")
public interface LoginService extends RemoteService {
	String isValidUserPassword(String login, String password);
	boolean isTokenValid(String token);
}
