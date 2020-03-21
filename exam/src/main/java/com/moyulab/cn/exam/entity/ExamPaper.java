package com.moyulab.cn.exam.entity;

import com.moyulab.cn.exam.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  试卷表
 */
@Data
@ApiModel(value = "试卷记录")
public class ExamPaper extends BaseEntity {

    @ApiModelProperty(value = "试卷id")
    private Long paperId; //id
    @ApiModelProperty(value = "试卷名")
    private String pagerName; //试卷名
    @ApiModelProperty(value = "考试码（根据当前时间戳自动生成）")
    private String paperCode; //考试码
    @ApiModelProperty(value = "考试时长(分钟)")
    private Integer duration; //考试时长(分钟)
    @ApiModelProperty(value = "试卷总分")
    private Integer score; //总分

}
