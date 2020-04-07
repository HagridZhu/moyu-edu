package com.moyulab.cn.exam.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moyulab.cn.exam.system.entity.SysUser;

import java.util.List;

public interface UserService extends IService<SysUser> {

    List<String> listRole(Long userId);
    List<String> listStringPermission(Long userId);
}
