package com.moyulab.cn.exam.vo;

import com.moyulab.cn.exam.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel
public class PaperAnswerVo extends BaseEntity {

    @ApiModelProperty("答卷id")
    private Long paperUserId;
    @ApiModelProperty("试卷id")
    private Long paperId;
    @ApiModelProperty("试卷名")
    private String paperName;
    @ApiModelProperty("考试码")
    private String paperCode;
    @ApiModelProperty("考试时长")
    private Integer duration;
    @ApiModelProperty("答卷人id")
    private Long userId;
    @ApiModelProperty("答卷人昵称")
    private Long nick;
    @ApiModelProperty("试卷总分")
    private Integer paperScore;
    @ApiModelProperty("我的总分")
    private Integer userScore;
    @ApiModelProperty("答卷情况：0=待考，1=已考")
    private Integer paperStatus;
    @ApiModelProperty("答题列表")
    private List<AnswerVo> answerVoList;

}
