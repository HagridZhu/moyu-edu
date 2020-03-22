package com.moyulab.cn.exam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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

    @ApiModelProperty(value = "试卷id",dataType = "long")
    @TableId
    private Long paperId; //id
    @ApiModelProperty(value = "试卷名",dataType = "string")
    private String paperName; //试卷名
    @ApiModelProperty(value = "考试码（根据当前时间戳自动生成）" ,dataType = "string")
    private String paperCode; //考试码
    @ApiModelProperty(value = "考试时长(分钟)" ,dataType = "int")
    private Integer duration; //考试时长(分钟)
    @ApiModelProperty(value = "试卷总分",dataType = "int")
    private Integer paperScore; //总分

}
