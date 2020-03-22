package com.moyulab.cn.exam.service;

import com.moyulab.cn.exam.dto.PaperAnswerDto;
import com.moyulab.cn.exam.entity.ExamQuestion;
import com.moyulab.cn.exam.vo.PaperAnswerVo;
import com.moyulab.cn.exam.vo.PaperDetailVo;
import com.moyulab.cn.exam.vo.QuestionVo;

public interface PaperService {

    /** 添加试题到试卷中
     * @param paperId 试卷id
     * @param num 题号
     * @param examQuestion 试题
     * @return
     */
    int createPaperQuestion(Long paperId, Integer num, ExamQuestion examQuestion);

    /**
     *  获取试卷详细信息
     * @param paperId 试卷id
     */
    PaperDetailVo getPaperDetailVo(Long paperId);


    PaperAnswerVo createPaperAnswer(PaperAnswerDto paperAnswerDto);

}
