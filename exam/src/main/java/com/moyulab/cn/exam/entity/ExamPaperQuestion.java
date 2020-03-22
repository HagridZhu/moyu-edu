package com.moyulab.cn.exam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 试卷试题
 */
@Data
public class ExamPaperQuestion {
    @TableId
    private Long id; //id
    private Long paperId; //试卷id
    private Long questionId; //题目id
    private Integer questionNum; //题号
    private Integer questionScore; //分值

}
