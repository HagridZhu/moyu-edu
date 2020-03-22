package com.moyulab.cn.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyulab.cn.exam.entity.ExamQuestion;
import org.apache.ibatis.annotations.Update;

public interface ExamQuestionMapper extends BaseMapper<ExamQuestion> {

    @Update("update exam_question set correct_num = correct_num + 1 where question_id in ( ${ids}) ")
    int incrCorrectNum(String ids);

    @Update("update exam_question set wrong_num = wrong_num + 1 where question_id in ( ${ids}) ")
    int incrWrongNum(String ids);



}
