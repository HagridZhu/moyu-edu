package com.moyulab.cn.exam.util;

import com.moyulab.cn.exam.common.Constant;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public static String getUserIdStr(){
        return toStr(getUserId(), "0");
    }

    public static Long getUserId(){
        String token = getRequest().getHeader(Constant.TOKEN_HEADER_NAME);
        if (StringUtils.hasText(token)) {
            return JwtUtil.getUserId(token);
        }
        return null;
    }

    public static String getUserName(){
        String token = getRequest().getHeader(Constant.TOKEN_HEADER_NAME);
        if (StringUtils.hasText(token)) {
            return JwtUtil.getUserName(token);
        }
        return null;
    }

    public static String toStr(Object obj, String defaultValue){
        if (obj == null) {
            return defaultValue;
        }
        return obj.toString();
    }
}
