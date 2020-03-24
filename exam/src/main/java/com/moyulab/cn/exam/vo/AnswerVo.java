package com.moyulab.cn.exam.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AnswerVo extends QuestionVo {

    @ApiModelProperty("答卷id")
    private Integer paperUserId;
    @ApiModelProperty("答卷答题id")
    private Integer paperAnswerId;
    @ApiModelProperty("答案题号")
    private Integer answerNum;
    @ApiModelProperty("答题情况：0=正确，1=错误")
    private Integer answerStatus;
    @ApiModelProperty("答案")
    private String answer;
    @ApiModelProperty("答案得分")
    private Integer answerScore;
}
