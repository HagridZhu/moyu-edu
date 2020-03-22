package com.moyulab.cn.exam.vo;

import com.moyulab.cn.exam.entity.ExamPaperAnswer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel
public class PaperAnswerVo {

    @ApiModelProperty("答题情况列表")
    private List<ExamPaperAnswer> paperAnswerList;
    @ApiModelProperty("提交时间")
    private Date createDate;
    @ApiModelProperty("答卷人")
    private String createBy;
    @ApiModelProperty("总分")
    private Integer score;

}
