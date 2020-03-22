package com.moyulab.cn.exam.vo;

import com.moyulab.cn.exam.entity.ExamQuestion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class QuestionVo extends ExamQuestion {

    @ApiModelProperty("题号")
    private Integer num;
    @ApiModelProperty("试卷试题id")
    private Long paperQuestionId;
    
}
