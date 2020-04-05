package com.moyulab.cn.exam.service;

import com.moyulab.cn.exam.dto.ClassDto;
import com.moyulab.cn.exam.system.entity.SysUser;
import com.moyulab.cn.exam.vo.ClassUserVo;

public interface ClassService  {

    int createStudentToClass(Long classId, SysUser sysUser);

    ClassUserVo listClassUser(ClassDto classDto);
}
