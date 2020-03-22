package com.moyulab.cn.exam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel
public class PaperAnswerVo {

    @ApiModelProperty("答卷id")
    private Long paperUserId;
    @ApiModelProperty("试卷id")
    private String paperId;
    @ApiModelProperty("试卷名")
    private String paperName;
    @ApiModelProperty("考试码")
    private String paperCode;
    @ApiModelProperty("考试时长")
    private Integer duration;
    @ApiModelProperty("提交时间")
    private Date createDate;
    @ApiModelProperty("答卷人")
    private String createBy;
    @ApiModelProperty("试卷总分")
    private Integer paperScore;
    @ApiModelProperty("我的总分")
    private Integer userScore;
    @ApiModelProperty("答题情况列表")
    private List<QuestionVo> questionVoList;

}
