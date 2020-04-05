package com.moyulab.cn.exam.service;

import com.moyulab.cn.exam.dto.PaperAnswerDto;
import com.moyulab.cn.exam.entity.ExamQuestion;
import com.moyulab.cn.exam.vo.PaperAnswerVo;
import com.moyulab.cn.exam.vo.PaperDetailVo;

public interface PaperService {

    /** 添加试题到试卷中
     * @param paperId 试卷id
     * @param num 题号
     * @param questionScore 题目分数
     * @param examQuestion 试题
     * @return
     */
    int createPaperQuestion(Long paperId, Integer num, Integer questionScore, ExamQuestion examQuestion);

    int deletePaperQuestion(Long paperQuestionId);

    /**
     *  获取试卷详细信息
     * @param paperId 试卷id
     */
    PaperDetailVo getPaperDetailVo(Long paperId);

    /**
     * 提交答卷
     */
    PaperAnswerVo createPaperAnswer(PaperAnswerDto paperAnswerDto);

    /**
     * 获取答卷详细信息
     * @param paperUserId 答卷id
     */
    PaperAnswerVo getPaperAnswerDetail(Long paperUserId);

    /**
     * 为整个班级的学生发起考试
     */
    int createPaperUserByClassId(Long paperId, Long classId);

}
