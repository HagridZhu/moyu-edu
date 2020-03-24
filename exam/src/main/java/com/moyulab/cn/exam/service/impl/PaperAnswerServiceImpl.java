package com.moyulab.cn.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyulab.cn.exam.dto.PaperAnswerDto;
import com.moyulab.cn.exam.entity.ExamPaperAnswer;
import com.moyulab.cn.exam.mapper.ExamPaperAnswerMapper;
import com.moyulab.cn.exam.service.PaperAnswerService;
import com.moyulab.cn.exam.vo.AnswerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperAnswerServiceImpl extends ServiceImpl<ExamPaperAnswerMapper, ExamPaperAnswer> implements PaperAnswerService {

    @Autowired
    private ExamPaperAnswerMapper examPaperAnswerMapper;

    @Override
    public List<AnswerVo> listAnswerVo(Long paperUserId) {
        PaperAnswerDto paperAnswerDto = new PaperAnswerDto();
        paperAnswerDto.setPaperUserId(paperUserId);
        return examPaperAnswerMapper.listAnswerVo(paperAnswerDto);
    }
}
