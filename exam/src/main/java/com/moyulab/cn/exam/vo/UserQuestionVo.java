package com.moyulab.cn.exam.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserQuestionVo extends QuestionVo {

    @ApiModelProperty("答题情况：0=正确，1=错误")
    private Integer answerStatus;
    @ApiModelProperty("用户填写的答案")
    private String userAnswer;
    @ApiModelProperty("用户得分")
    private Integer userScore;
    @ApiModelProperty("回答记录id")
    private Long userAnswerId;
    @ApiModelProperty("答卷id")
    private Long paperUserId;

}
