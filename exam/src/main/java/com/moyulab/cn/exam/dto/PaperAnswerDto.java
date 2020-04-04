package com.moyulab.cn.exam.dto;

import com.moyulab.cn.exam.entity.ExamPaperAnswer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PaperAnswerDto {

    @ApiModelProperty("试卷id")
    private Long paperId;
    @ApiModelProperty("答卷列表")
    private List<ExamPaperAnswer> examPaperAnswerList;
    @ApiModelProperty("答卷id")
    private Long paperUserId;

}
