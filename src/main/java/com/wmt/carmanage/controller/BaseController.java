package com.wmt.carmanage.controller;

import com.wmt.carmanage.constant.Const;
import com.wmt.carmanage.entity.UserInfo;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 基础控制层
 */
@SuppressWarnings("all")
public class BaseController {

    private static ThreadLocal<ServletRequest> currentRequest = new ThreadLocal<>();
    private static ThreadLocal<ServletResponse> currentResponse = new ThreadLocal<>();


    /**
     * 线程安全初始化reque，respose对象
     */
    @ModelAttribute
    public void initReqAndRep(HttpServletRequest request, HttpServletResponse response) {
        currentRequest.set(request);
        currentResponse.set(response);
    }

     /**
     * 获取当前用户
     */
    public UserInfo getCurrentUser(){
        return (UserInfo) getRequest().getSession().getAttribute(Const.USER_KEY);
    }

    /**
     * 获取request
     */
    public HttpServletRequest getRequest() {
        return (HttpServletRequest) currentRequest.get();
    }

    /**
     * 获取response
     */
    public HttpServletResponse getResponse() {
        return (HttpServletResponse) currentResponse.get();
    }

}
