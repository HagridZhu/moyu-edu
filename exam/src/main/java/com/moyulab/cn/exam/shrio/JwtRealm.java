package com.moyulab.cn.exam.shrio;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.moyulab.cn.exam.system.service.UserService;
import com.moyulab.cn.exam.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class JwtRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    /*
     * 多重写一个support
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     * */
    @Override
    public boolean supports(AuthenticationToken token) {
        //这个token就是从过滤器中传入的jwtToken
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String jwtToken = (String)principalCollection.getPrimaryPrincipal();
        Long userId = JwtUtil.getUserId(jwtToken);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(userService.listRole(userId));
        simpleAuthorizationInfo.addStringPermissions(userService.listStringPermission(userId));
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String jwt = (String) authenticationToken.getPrincipal();
        if (jwt == null) {
            throw new NullPointerException("jwtToken 不允许为空");
        }
        //判断token是否过期
        try{
            JwtUtil.verify(jwt);
        }catch (TokenExpiredException e){
            log.error("token已过期", e);
            throw new AuthenticationException("token已过期");
        }catch (Exception ee){
            log.error("token校验失败", ee);
            throw new AuthenticationException("token认证失败！");
        }
       //校验token的有效性

        //判断token是否正确
        /*if (!JwtUtil.isValid(jwt)) {
            throw new AuthenticationException("token认证失败！");
        }*/
        //下面是验证这个user是否是真实存在的
        String username = JwtUtil.getUserName(jwt);//判断数据库中username是否存在
        log.info("在使用token登录"+username);
        return new SimpleAuthenticationInfo(jwt, jwt,"jwtRealm");
        //这里返回的是类似账号密码的东西，但是jwtToken都是jwt字符串。还需要一个该Realm的类名
    }




}