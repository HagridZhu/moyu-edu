package com.moyulab.cn.exam.system.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moyulab.cn.exam.common.BaseController;
import com.moyulab.cn.exam.common.Result;
import com.moyulab.cn.exam.system.entity.SysUser;
import com.moyulab.cn.exam.system.entity.SysUserRole;
import com.moyulab.cn.exam.system.mapper.SysUserMapper;
import com.moyulab.cn.exam.system.mapper.SysUserRoleMapper;
import com.moyulab.cn.exam.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Api(tags={"认证授权接口"})
@RestController
@Slf4j
@RequestMapping("exam/oauth")
public class LoginController extends BaseController {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @ApiOperation(value = "登陆" , notes = "登陆成功后返回token和用户相关信息")
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
        Long userId = sysUser.getUserId();
        QueryWrapper<SysUserRole> roleQuery = new QueryWrapper<SysUserRole>().eq("user_id", userId);
        List<SysUserRole> roleList = sysUserRoleMapper.selectList(roleQuery);
        String roleIdStr = null;
        if (!CollectionUtils.isEmpty(roleList)) {
            List<String> roleIdList = roleList.stream().map(e -> String.valueOf(e.getRoleId())).collect(Collectors.toList());
            roleIdStr = String.join(",", roleIdList);
        }
        String token = JwtUtil.sign(sysUser.getUserName(), userId, roleIdStr);
        return Result.newBuilder()
                .put("token", token)
                .put("avatar", sysUser.getAvatar())
                .put("nick", sysUser.getNick())
                .put("roleId", roleIdStr)
                .build();
    }


}
