package com.example.rssr.server;

import com.example.rssr.client.LoginService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	private static final long serialVersionUID = 1L;

	@Override
	public Boolean isLogin(String login, String password) {
		if(login.compareTo(password)==0)
			return true;
		return false;
	}

}
