package com.moyulab.cn.exam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 班级用户
 */
@Data
public class ExamClassUser {
    @TableId
    private Long id;
    private Long classId;
    private Long userId;

}
