package com.moyulab.cn.exam.common;

import com.moyulab.cn.exam.enums.RoleEnum;
import com.moyulab.cn.exam.util.RequestUtil;
import org.apache.shiro.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    protected long getPageStart(){
        return (getPageIndex() - 1) * getPageSize();
    }

    protected static boolean isAdmin(){
        List<Long> roleId = RequestUtil.getRoleId();
        if (roleId != null) {
            return roleId.contains((long)RoleEnum.管理员.getValue());
        }
        return false;
    }

    protected static boolean isTeacher(){
        List<Long> roleId = RequestUtil.getRoleId();
        if (roleId != null) {
            return roleId.contains((long)RoleEnum.老师.getValue());
        }
        return false;
    }

    private Long getLongParam(String field, Long defaultValue){
        String pageIndex = getRequest().getParameter(field);
        if (StringUtils.hasText(pageIndex)) {
            return Long.valueOf(pageIndex);
        }
        return defaultValue;
    }

}
