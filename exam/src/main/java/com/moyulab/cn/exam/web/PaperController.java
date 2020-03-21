package com.moyulab.cn.exam.web;

import com.moyulab.cn.exam.common.BaseController;
import com.moyulab.cn.exam.common.Result;
import com.moyulab.cn.exam.entity.ExamPaper;
import com.moyulab.cn.exam.mapper.ExamPaperMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;


@RestController
@RequestMapping("exam/paper")
public class PaperController extends BaseController {

    @Autowired
    private ExamPaperMapper examPaperMapper;

    @ApiOperation(value="新增试卷", notes="就新增试卷记录..")
    @PostMapping("")
    public Result createPaper(ExamPaper examPaper){
        // 生成考试码
        examPaper.setPaperId(null);
        examPaper.setPaperCode(getCode());
        examPaperMapper.insert(examPaper);
        return Result.success();
    }

    @ApiOperation(value="编辑试卷", notes="就编辑试卷记录..")
    @PutMapping("")
    public Result updatePaper(ExamPaper examPaper){
        // 生成考试码
        examPaper.setPaperId(null);
        examPaper.setPaperCode(getCode());
        examPaperMapper.insert(examPaper);
        return Result.success();
    }

    private String getCode(){
        return new BigInteger(String.valueOf(System.currentTimeMillis() - 1584794639393L)).toString(36);
    }

}
