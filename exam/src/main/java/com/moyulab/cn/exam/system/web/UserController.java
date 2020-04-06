package com.moyulab.cn.exam.system.web;

import com.moyulab.cn.exam.common.BaseController;
import com.moyulab.cn.exam.common.MoyuLabException;
import com.moyulab.cn.exam.common.Result;
import com.moyulab.cn.exam.system.entity.SysUser;
import com.moyulab.cn.exam.system.mapper.SysUserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags={"用户信息接口"})
@RestController
@Slf4j
@RequestMapping("exam/user")
public class UserController extends BaseController {

    @Autowired
    private SysUserMapper sysUserMapper;

    @ApiOperation(value = "更新用户信息" , notes = "更新用户信息")
    @PutMapping
    public Object updateUser(String nick, String avatar, String password){
        SysUser sysUser = new SysUser();
        if (StringUtils.hasText(nick)) {
            sysUser.setNick(nick);
        }
        if (StringUtils.hasText(avatar)) {
            sysUser.setAvatar(avatar);
        }
        if (StringUtils.hasText(password)) {
            sysUser.setPassword(password);
        }
        Long userId = getUserId();
        if (userId == null) {
            throw new MoyuLabException("获取当前用户的userId失败");
        }
        sysUser.setUserId(userId);
        this.sysUserMapper.updateById(sysUser);
        return Result.success();
    }
}
