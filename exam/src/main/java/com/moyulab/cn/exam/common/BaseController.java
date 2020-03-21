package com.moyulab.cn.exam.common;

import com.moyulab.cn.exam.util.JwtUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    protected HttpServletRequest getRequest(){
        return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    protected Long getUserId(){
        String token = getRequest().getHeader(Constant.TOKEN_HEADER_NAME);
        if (StringUtils.hasText(token)) {
            return JwtUtil.getUserId(token);
        }
        return null;
    }

    protected String getUserName(){
        String token = getRequest().getHeader(Constant.TOKEN_HEADER_NAME);
        if (StringUtils.hasText(token)) {
            return JwtUtil.getUserName(token);
        }
        return null;
    }

}
