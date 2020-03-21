package com.moyulab.cn.exam.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyulab.cn.exam.common.BaseController;
import com.moyulab.cn.exam.common.Constant;
import com.moyulab.cn.exam.common.Result;
import com.moyulab.cn.exam.entity.ExamPaper;
import com.moyulab.cn.exam.mapper.ExamPaperMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("exam/paper")
public class PaperController extends BaseController {

    @Autowired
    private ExamPaperMapper examPaperMapper;

    @ApiOperation(value="新增试卷", notes="就新增试卷记录..")
    @PostMapping
    public Result createPaper(ExamPaper examPaper){
        // 生成考试码
        examPaper.setPaperId(null);
        examPaper.setPaperCode(getCode());
        examPaperMapper.insert(examPaper);
        return Result.success();
    }

    @ApiOperation(value="编辑试卷", notes="就编辑试卷记录..")
    @PutMapping
    public Result updatePaper(ExamPaper examPaper){
        if (examPaper.getPaperId() == null) {
            return Result.error("编辑试卷，paperId不能为空");
        }
        examPaperMapper.updateById(examPaper);
        return Result.success();
    }

    @ApiOperation(value="查看试卷列表", notes="就查看试卷记录列表..")
    @GetMapping
    public Result listPaper(ExamPaper examPaper){
        QueryWrapper<ExamPaper> wrapper = new QueryWrapper();
        Page<ExamPaper> page = new Page<>(getPageIndex(), getPageSize());
        String paperName = examPaper.getPaperName();
        if (StringUtils.hasText(paperName)) {
            wrapper.like("paper_name", "%" + paperName + "%");
        }
        // 只能查看自己创建的
        wrapper.eq(Constant.COL_CREATE_BY, getUserId()).orderByDesc(Constant.COL_CREATE_DATE);
        return Result.success(examPaperMapper.selectPage(page, wrapper));
    }


    private String getCode(){
        return new BigInteger(String.valueOf(System.currentTimeMillis() - 1584794639393L)).toString(36);
    }

}
