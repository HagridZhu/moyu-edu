package com.moyulab.cn.exam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 答卷答题
 */
@Data
@ApiModel
public class ExamPaperAnswer {
    @TableId
    @ApiModelProperty("答卷答题id")
    private Long paperAnswerId; //id
    @ApiModelProperty("答卷id")
    private Long paperUserId; //答卷id
    @ApiModelProperty("试卷试题id")
    private Long paperQuestionId; //试卷试题id
    @ApiModelProperty("答案题号")
    private Integer answerNum; //题号
    @ApiModelProperty("答题情况：0=正确，1=错误")
    private Integer answerStatus;  //答题情况：0=正确，1=错误
    @ApiModelProperty("答案")
    private String answer; //答案
    @ApiModelProperty("答案得分")
    private Integer answerScore; //得分

}
