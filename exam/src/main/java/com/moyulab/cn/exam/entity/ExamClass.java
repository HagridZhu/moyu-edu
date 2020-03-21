package com.moyulab.cn.exam.entity;

import com.moyulab.cn.exam.common.BaseEntity;
import lombok.Data;

/**
 * 班级
 */
@Data
public class ExamClass extends BaseEntity {

    private Long classId; //主键id
    private String school; //学校
    private Integer grade; //年级
    private Integer num; //班别

}
