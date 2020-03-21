package com.moyulab.cn.exam.entity;

import lombok.Data;

/**
 * 答卷答题
 */
@Data
public class ExamPaperAnswer {

    private Long id; //id
    private Long paperUserId; //答卷id
    private Integer num; //题号
    private Integer status;  //答题情况：0=正确，1=错误
    private String answer; //答案
    private Integer score; //得分

}
