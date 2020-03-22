package com.moyulab.cn.exam.vo;

import com.moyulab.cn.exam.entity.ExamPaper;

import java.util.Date;
import java.util.List;

public class PaperVo extends ExamPaper {
    private Long paperUserId;
    private Integer score;
    private Date createDate;
    private List answerList;

}
