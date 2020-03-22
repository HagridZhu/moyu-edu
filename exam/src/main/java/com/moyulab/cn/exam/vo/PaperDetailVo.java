package com.moyulab.cn.exam.vo;

import com.moyulab.cn.exam.entity.ExamPaper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "试卷详情")
public class PaperDetailVo extends ExamPaper {

    @ApiModelProperty("题目列表")
    private List<QuestionVo> questionVoList;

}
