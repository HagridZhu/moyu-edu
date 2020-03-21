package com.moyulab.cn.exam.entity;

import lombok.Data;

/**
 * 班级用户
 */
@Data
public class ExamClassUser {

    private Long id;
    private Long classId;
    private Long userId;

}
