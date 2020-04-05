package com.moyulab.cn.exam.service.impl;

import com.moyulab.cn.exam.common.MoyuLabException;
import com.moyulab.cn.exam.dto.ClassDto;
import com.moyulab.cn.exam.entity.ExamClassUser;
import com.moyulab.cn.exam.enums.RoleEnum;
import com.moyulab.cn.exam.mapper.ExamClassUserMapper;
import com.moyulab.cn.exam.service.ClassService;
import com.moyulab.cn.exam.system.entity.SysUser;
import com.moyulab.cn.exam.system.entity.SysUserRole;
import com.moyulab.cn.exam.system.mapper.SysUserMapper;
import com.moyulab.cn.exam.system.mapper.SysUserRoleMapper;
import com.moyulab.cn.exam.vo.ClassUserVo;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private ExamClassUserMapper examClassUserMapper;

    @Override
    public int createStudentToClass(Long classId, SysUser sysUser) {
        sysUser.setUserId(null);
        String userName = sysUser.getUserName();
        String password = sysUser.getPassword();
        if (classId == null) {
            throw new MoyuLabException("classId不能为空");
        }
        if (!StringUtils.hasText(userName)) {
            throw new MoyuLabException("用户名不能为空");
        }
        if (!StringUtils.hasText(password)) {
            throw new MoyuLabException("密码不能为空");
        }

        // 创建学生用户
        sysUserMapper.insert(sysUser);
        Long userId = sysUser.getUserId();
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId((long)RoleEnum.学生.getValue());
        sysUserRole.setUserId(userId);
        sysUserRole.setCreateDate(new Date());
        sysUserRoleMapper.insert(sysUserRole);

        // 添加到班级
        ExamClassUser examClassUser = new ExamClassUser();
        examClassUser.setUserId(userId);
        examClassUser.setClassId(classId);
        return examClassUserMapper.insert(examClassUser);
    }

    @Override
    public ClassUserVo listClassUser(ClassDto classDto) {

        return null;
    }
}
