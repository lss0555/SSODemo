package com.example.server2.service;

import com.example.server2.model.Credential;
import com.example.server2.model.DemoLoginUser;
import com.example.server2.model.LoginUser;
import com.example.server2.service.inter.IAuthenticationHandler;

import java.util.Set;

/**
 * 示例性的鉴权处理器，当用户名和密码都为admin时授权通过，返回的也是一个示例性Credential实例
 * 
 * @author Administrator
 *
 */
public class DemoAuthenticationHandler implements IAuthenticationHandler {

	@Override
	public LoginUser authenticate(Credential credential) throws Exception {
		if ("admin".equals(credential.getParameter("name"))
				&& "admin".equals(credential.getParameter("passwd"))) {
			DemoLoginUser user = new DemoLoginUser();
			user.setLoginName("admin");
			return user;
		} else {
			credential.setError("帐号或密码错误");
			return null;
		}
	}

	@Override
	public Set<String> authedSystemIds(LoginUser loginUser) throws Exception {
		return null;
	}

	@Override
	public LoginUser autoLogin(String lt) throws Exception {
		return null;
	}

	@Override
	public String loginToken(LoginUser loginUser) throws Exception {
		return null;
	}

	@Override
	public void clearLoginToken(LoginUser loginUser) throws Exception {
	}
	
}
