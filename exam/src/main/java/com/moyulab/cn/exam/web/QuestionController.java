package com.moyulab.cn.exam.web;

import com.moyulab.cn.exam.common.Result;
import com.moyulab.cn.exam.entity.ExamQuestion;
import com.moyulab.cn.exam.mapper.ExamQuestionMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags={"试题接口"})
@RestController
@RequestMapping("exam/question")
public class QuestionController {

    @Autowired
    private ExamQuestionMapper examQuestionMapper;

    @ApiOperation("添加试题")
    @PostMapping
    public Result createQuestion(ExamQuestion examQuestion){
        examQuestion.setQuestionId(null);
        examQuestionMapper.insert(examQuestion);
        return Result.success();
    }

    @ApiOperation("修改试题")
    @PutMapping
    public Result updateQuestion(ExamQuestion examQuestion){
        if (examQuestion.getQuestionId() == null) {
            return Result.error("questionId不能位空");
        }
        examQuestionMapper.updateById(examQuestion);
        return Result.success();
    }




}
