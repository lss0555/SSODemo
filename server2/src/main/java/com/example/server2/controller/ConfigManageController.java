package com.example.server2.controller;


import com.example.server2.config.Config;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 只是一个演示性的配置管理控制器
 * @author preach
 *
 */
@Controller
public class ConfigManageController {

	@Resource
	private Config config;

	@RequestMapping("/config")
	public void configPage() {
	}

	@GetMapping("/config/refresh")
	@ResponseBody
	public boolean config(String code) throws Exception {
		if ("test".equals(code)) {
			config.refreshConfig();
			return true;
		}

		return false;
	}
}
