package com.moyulab.cn.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyulab.cn.exam.dto.ClassDto;
import com.moyulab.cn.exam.entity.ExamClass;
import com.moyulab.cn.exam.vo.ClassUserVo;

import java.util.List;

public interface ExamClassMapper extends BaseMapper<ExamClass> {

    /** 查询学生 */
    List<ClassUserVo> listClassUserVo(ClassDto classDto);

    int countClassUserVo(ClassDto classDto);
}
