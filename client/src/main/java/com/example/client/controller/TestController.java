package com.example.client.controller;

import com.example.client.redis.inter.TokenManagerInter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @Description 测试
 * @Author lss0555
 * @Date 2018/12/26/026 10:20
 **/
@RestController
public class TestController {
    @Resource
    TokenManagerInter tokenManagerInter;

    @GetMapping("/test")
    public String test(){
        return "hello world";
    }

    @GetMapping("/index")
    public ModelAndView view(){
        ModelAndView view = new ModelAndView("index");
        return view;
    }




}
