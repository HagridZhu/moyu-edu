package com.moyulab.cn.exam.system.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moyulab.cn.exam.common.Result;
import com.moyulab.cn.exam.system.entity.SysUser;
import com.moyulab.cn.exam.system.mapper.SysUserMapper;
import com.moyulab.cn.exam.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Api(tags={"认证授权接口"})
@RestController
@Slf4j
@RequestMapping("exam/oauth")
public class LoginController {

    @Autowired
    private SysUserMapper sysUserMapper;

    @ApiOperation(value = "登陆" , notes = "登陆成功后返回token")
    @RequestMapping("login")
    public Object login(String userName, String password){
        log.info("---------------login,userName={},password={}", userName, password);
        QueryWrapper<SysUser> query = new QueryWrapper<SysUser>().eq("user_name", userName);
        SysUser sysUser = sysUserMapper.selectOne(query);
        if (sysUser == null) {
           return Result.error("账号不存在");
        }
        if (!Objects.equals(sysUser.getPassword(), password)) {
            return Result.error("密码不正确");
        }
        String token = JwtUtil.sign(sysUser.getUserName(), sysUser.getUserId());
        return Result.success("token", token);
    }


}
