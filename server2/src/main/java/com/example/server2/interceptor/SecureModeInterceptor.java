package com.example.server2.interceptor;

import com.example.server2.config.Config;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于安全模式拦截判断的拦截器
 * 
 * @author preach
 *
 */
@Component
public class SecureModeInterceptor implements HandlerInterceptor {

	@Resource
	private Config config;

	@Override
	public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// do nothing
		System.out.println("afterCompletion");
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {
		// do nothing
		System.out.println("postHandle");
	}

	/**
	 * 请求执行前判断是否安全模式
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object object) throws Exception {
		System.out.println("preHandle");
//		boolean ret = !config.isSecureMode() || request.isSecure();
//		if (!ret) {
//			response.getWriter().write("must https");
//		}
//		return ret;
		return  true;
	}

}
