package com.moyulab.cn.exam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.moyulab.cn.exam.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 题目
 */
@Data
@ApiModel
public class ExamQuestion extends BaseEntity {

    @TableId
    @ApiModelProperty(value = "试题id")
    private Long questionId; //id
    @ApiModelProperty(value = "试题类型：0=单择题，1=多选题，2=填空题，3=简答题，4=判断题")
    private Integer type; //试题类型：0=单择题，1=多选题，2=填空题，3=简答题，4=判断题
    @ApiModelProperty(value = "题干")
    private String content; //题干
    @ApiModelProperty(value = "选项A")
    private String optionA; //选项A
    @ApiModelProperty(value = "选项B")
    private String optionB; //选项B
    @ApiModelProperty(value = "选项C")
    private String optionC; //选项C
    @ApiModelProperty(value = "选项D")
    private String optionD; //选项E
    @ApiModelProperty(value = "图片,多张图片用英文逗号隔开")
    private String pictureUrl; //图片
    @ApiModelProperty(value = "答案")
    private String answer; //答案
    @ApiModelProperty(value = "解析")
    private String explanation; //解析
    @ApiModelProperty(value = "默认分值")
    private Integer score; //默认分值
    @ApiModelProperty(value = "答对数")
    private Integer correctNum; //答对数
    @ApiModelProperty(value = "答错数")
    private Integer wrongNum; //答错数

}
