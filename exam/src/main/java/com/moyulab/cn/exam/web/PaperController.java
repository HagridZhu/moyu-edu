package com.moyulab.cn.exam.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyulab.cn.exam.common.BaseController;
import com.moyulab.cn.exam.common.Constant;
import com.moyulab.cn.exam.common.Result;
import com.moyulab.cn.exam.dto.PaperAnswerDto;
import com.moyulab.cn.exam.entity.ExamPaper;
import com.moyulab.cn.exam.entity.ExamQuestion;
import com.moyulab.cn.exam.mapper.ExamPaperMapper;
import com.moyulab.cn.exam.service.PaperService;
import com.moyulab.cn.exam.vo.PaperAnswerVo;
import com.moyulab.cn.exam.vo.PaperDetailVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags={"试卷接口"})
@RestController
@RequestMapping("exam/paper")
public class PaperController extends BaseController {

    @Autowired
    private ExamPaperMapper examPaperMapper;
    @Autowired
    private PaperService paperService;

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
    public Result<Page<ExamPaper>> listPaper(ExamPaper examPaper){
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

    @ApiOperation("添加试题到试卷")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paperId",value = "试卷id",dataType = "Long",required = true),
            @ApiImplicitParam(name = "questionNum",value = "题号,为空则自动插入到同种类型题目的末尾",dataType = "Integer"),
            @ApiImplicitParam(name = "questionScore",value = "题目的默认分值",dataType = "Integer")
    })
    @PostMapping("question")
    public Result createPaperQuestion(Long paperId, Integer questionNum, Integer questionScore, ExamQuestion examQuestion){
        paperService.createPaperQuestion(paperId, questionNum, questionScore, examQuestion);
        return Result.success();
    }

    @ApiOperation("删除试卷的试题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paperQuestionId",value = "试题id",dataType = "Long",required = true)
    })
    @DeleteMapping("question")
    public Result createPaperQuestion(Long paperQuestionId){
        paperService.deletePaperQuestion(paperQuestionId);
        return Result.success();
    }

    @ApiOperation("查询试卷详情")
    @ApiImplicitParam(name = "paperId",value = "试卷id",dataType = "Long",required = true)
    @GetMapping("detail/{paperId}")
    public Result<PaperDetailVo> getPaperDetail(@PathVariable Long paperId){
        return Result.success(paperService.getPaperDetailVo(paperId));
    }

    @ApiOperation("提交答卷")
    @PostMapping("answer")
    public Result<PaperAnswerVo> createPaperAnswer(PaperAnswerDto paperAnswerDto){
        return Result.success(paperService.createPaperAnswer(paperAnswerDto));
    }

    @ApiOperation("查看答卷记录")
    @GetMapping("answer")
    public Result<Page<PaperAnswerVo>> listPaperAnswer(String paperName, Integer paperStatus){
        Map<String,Object> map = new HashMap<>();
        map.put("paperName", paperName);
        map.put("start", getPageStart());
        map.put("size", getPageSize());
        map.put("paperStatus", paperStatus);
        List<PaperAnswerVo> list = this.examPaperMapper.listPaperAnswerVo(map);
        int total = examPaperMapper.coutPaperAnswer(map);
        Page<PaperAnswerVo> page = new Page<>(getPageIndex(), getPageSize(), total);
        page.setRecords(list);
        return Result.success(page);
    }

    @ApiOperation("查看答卷详情")
    @ApiImplicitParam(name = "paperUserId", value = "答卷id", required = true)
    @GetMapping("answer/detail")
    public Result<PaperAnswerVo> getPaperAnswerDetail(Long paperUserId){
        return Result.success(paperService.getPaperAnswerDetail(paperUserId));
    }

    @ApiOperation("给班级下的所有学生发起考试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paperId", value = "试卷id", required = true),
            @ApiImplicitParam(name = "classId", value = "班级id", required = true)
    })
    @PostMapping("user/class")
    public Result<PaperAnswerVo> createPaperUser(Long paperId, Long classId){
        return Result.success(paperService.createPaperUserByClassId(paperId, classId));
    }

    private String getCode(){
        return new BigInteger(String.valueOf(System.currentTimeMillis() - 1584794639393L)).toString(36);
    }

}
