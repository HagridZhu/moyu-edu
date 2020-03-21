package com.moyulab.cn.exam.entity;

import com.moyulab.cn.exam.common.BaseEntity;
import lombok.Data;

/**
 * 题目
 */
@Data
public class ExamQuestion extends BaseEntity {

    private Long questionId; //id
    private Integer type; //试题类型：0=单择题，1=多选题，2=填空题，3=简答题，4=判断题
    private String content; //题干
    private String optionA; //选项A
    private String optionB; //选项B
    private String optionC; //选项C
    private String optionD; //选项E
    private String pictureUrl; //图片
    private String answer; //答案
    private String explanation; //解析
    private String score; //默认分值
    private String correctNum; //答对数
    private String wrongNum; //答错数

}
