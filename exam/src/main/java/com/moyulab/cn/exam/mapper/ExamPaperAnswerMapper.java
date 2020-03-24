package com.moyulab.cn.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyulab.cn.exam.dto.PaperAnswerDto;
import com.moyulab.cn.exam.entity.ExamPaperAnswer;
import com.moyulab.cn.exam.vo.AnswerVo;

import java.util.List;

public interface ExamPaperAnswerMapper extends BaseMapper<ExamPaperAnswer> {

    List<AnswerVo> listAnswerVo(PaperAnswerDto paperAnswerDto);

}
