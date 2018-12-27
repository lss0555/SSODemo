package com.example.server2.service.inter;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 登录页前置处理器
 * 
 * @author Administrator
 *
 */
public interface IPreLoginHandler {

	public static final String SESSION_ATTR_NAME = "login_session_attr_name";

	/**
	 * 前置处理
	 */
	public abstract Map<?, ?> handle(HttpSession session) throws Exception;
}
