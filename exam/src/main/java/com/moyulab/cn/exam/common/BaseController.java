package com.moyulab.cn.exam.common;

import com.moyulab.cn.exam.util.RequestUtil;
import org.apache.shiro.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    protected HttpServletRequest getRequest(){
        return RequestUtil.getRequest();
    }

    protected Long getUserId(Long defaultValue){
        Long userId = RequestUtil.getUserId();
        if (userId != null) {
            return userId;
        }
        return defaultValue;
    }

    protected Long getUserId(){
        return getUserId(0L);
    }

    protected String getUserName(){
        return RequestUtil.getUserName();
    }

    protected long getPageIndex(){
        return getLongParam("pageIndex", 1L);
    }

    protected long getPageSize(){
        return getLongParam("pageSize", 10L);
    }

    private Long getLongParam(String field, Long defaultValue){
        String pageIndex = getRequest().getParameter(field);
        if (StringUtils.hasText(pageIndex)) {
            return Long.valueOf(pageIndex);
        }
        return defaultValue;
    }

}
