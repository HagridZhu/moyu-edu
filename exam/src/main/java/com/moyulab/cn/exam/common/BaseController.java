package com.moyulab.cn.exam.common;

import com.moyulab.cn.exam.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    protected HttpServletRequest getRequest(){
        return RequestUtil.getRequest();
    }

    protected Long getUserId(){
        return RequestUtil.getUserId();
    }

    protected String getUserName(){
        return RequestUtil.getUserName();
    }

}
