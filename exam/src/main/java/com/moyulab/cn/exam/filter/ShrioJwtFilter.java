package com.moyulab.cn.exam.filter;

import com.alibaba.fastjson.JSONObject;
import com.moyulab.cn.exam.common.Constant;
import com.moyulab.cn.exam.shrio.JwtToken;
import com.moyulab.cn.exam.util.JwtUtil;
import com.moyulab.cn.exam.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ShrioJwtFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //判断请求的请求头是否带上 "token"
        if (isLoginAttempt(request, response)) {
            //如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确
            try {
                executeLogin(request, response);
                refreshToken(request, response);
            } catch (Exception e) {
                responseError(response, JSONObject.toJSONString(new Result(401, e.getMessage())));
                return false;
            }
        }
        //如果请求头不存在 token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse response) throws Exception {
        /*
        AccessControlFilter
        public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
            return this.isAccessAllowed(request, response, mappedValue) || this.onAccessDenied(request, response, mappedValue);
        }
        如果isAccessAllowed返回false，才走这个方法
        */
        return false;
    }

    /**
     * 判断用户是否想要登入。
     * 检测 header 里面是否包含 token 字段
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        return getToken(request) != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        String token = getToken(request);
        JwtToken jwtToken = new JwtToken(token);
        // 提交给realm进行登入，如果错误它会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }


    /**
     * 跨域支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", "*");//httpServletRequest.getHeader("Origin"));
        /*httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }*/
        return super.preHandle(request, response);
    }

    private void responseError(ServletResponse response, String message) {
        try {
            /* *
             *  这个问题纠结了好久
             *      原生的shiro验证失败会进入全局异常 但是 和JWT结合以后却不进入了  之前一直想不通
             *      原因是 JWT直接在过滤器里验证  验证成功与否 都是直接返回到过滤器中 成功在进入controller
             *      失败直接返回进入springboot自定义异常处理页面
             */
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setContentType("application/json; charset=utf-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.getWriter().write(message);
        } catch (IOException e) {
            log.error("shrio返回特定错误信息时出错", e);
        }
    }

    private String getToken(ServletRequest request){
        HttpServletRequest req = (HttpServletRequest) request;
        return req.getHeader(Constant.TOKEN_HEADER_NAME);
    }

    private void refreshToken(ServletRequest request, ServletResponse response){
        String refreshToken = JwtUtil.refreshToken(getToken(request));
        if (refreshToken != null) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setHeader("Access-Control-Expose-Headers", Constant.REFRESH_TOKEN_HEADER_NAME);
            httpServletResponse.setHeader(Constant.REFRESH_TOKEN_HEADER_NAME, refreshToken);
        }
    }

}
