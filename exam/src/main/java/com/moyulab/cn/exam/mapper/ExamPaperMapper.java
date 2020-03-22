package com.moyulab.cn.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyulab.cn.exam.entity.ExamPaper;
import com.moyulab.cn.exam.vo.PaperAnswerVo;

import java.util.List;
import java.util.Map;

public interface ExamPaperMapper extends BaseMapper<ExamPaper> {

    /** 考试记录 */
    List<PaperAnswerVo> listPaperAnswerVo(Map<String,Object> map);

    int coutPaperAnswer(Map<String,Object> map);

}
