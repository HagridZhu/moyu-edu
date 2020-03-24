package com.moyulab.cn.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moyulab.cn.exam.entity.ExamPaperAnswer;
import com.moyulab.cn.exam.vo.AnswerVo;
import java.util.List;


public interface PaperAnswerService extends IService<ExamPaperAnswer> {

    List<AnswerVo> listAnswerVo(Long paperUserId);


}
