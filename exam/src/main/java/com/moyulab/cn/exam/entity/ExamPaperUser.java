package com.moyulab.cn.exam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.moyulab.cn.exam.common.BaseEntity;
import lombok.Data;

/**
 * 答卷
 */
@Data
public class ExamPaperUser extends BaseEntity {
    @TableId
    private Long paperUserId; //主键
    private Long paperId; //试卷id
    private Integer score; //总分

}
