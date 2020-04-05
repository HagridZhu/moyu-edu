package com.moyulab.cn.exam.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyulab.cn.exam.common.BaseController;
import com.moyulab.cn.exam.common.Constant;
import com.moyulab.cn.exam.common.Result;
import com.moyulab.cn.exam.dto.ClassDto;
import com.moyulab.cn.exam.entity.ExamClass;
import com.moyulab.cn.exam.mapper.ExamClassMapper;
import com.moyulab.cn.exam.service.ClassService;
import com.moyulab.cn.exam.system.entity.SysUser;
import com.moyulab.cn.exam.vo.ClassUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags={"班级接口"})
@RestController
@RequestMapping("exam/class")
public class ClassController extends BaseController {

    @Autowired
    private ExamClassMapper examClassMapper;
    @Autowired
    private ClassService classService;

    @ApiOperation(value="新增班级", notes="就新增班级记录..")
    @PostMapping
    public Result createClass(ExamClass examClass){
        // 生成考试码
        examClass.setClassId(null);
        examClassMapper.insert(examClass);
        return Result.success();
    }

    @ApiOperation(value="编辑班级", notes="就编辑班级记录..")
    @PutMapping
    public Result updateClass(ExamClass examClass){
        if (examClass.getClassId() == null) {
            return Result.error("编辑班级，classId不能为空");
        }
        examClassMapper.updateById(examClass);
        return Result.success();
    }

    @ApiOperation(value="查看班级列表", notes="就查看班级列表..")
    @GetMapping
    public Result<Page<ExamClass>> listClass(ExamClass examClass){
        QueryWrapper<ExamClass> wrapper = new QueryWrapper();
        if (examClass.getGrade() != null) {
            wrapper.eq("grade", examClass.getGrade());
        }
        // 只能查看自己创建的
        Page<ExamClass> page = new Page<>(getPageIndex(), getPageSize());
        wrapper.eq(Constant.COL_CREATE_BY, getUserId()).orderByDesc(Constant.COL_CREATE_DATE);
        return Result.success(examClassMapper.selectPage(page, wrapper));
    }

    @ApiOperation(value="添加学生到班级", notes="创建学生用户，并添加到我的班级..")
    @PostMapping("user")
    public Result<Page<ExamClass>> createStudentToClass(Long classId, SysUser sysUser){
        return Result.success(classService.createStudentToClass(classId, sysUser));
    }

    @ApiOperation(value="查询我的学生", notes="创建学生用户，并添加到我的班级..")
    @GetMapping("user")
    public Result<Page<ExamClass>> listClassUser(ClassDto classDto){
        classDto.setCreateBy(getUserId());
        classDto.setStart(getPageStart());
        classDto.setSize(getPageSize());
        List<ClassUserVo> list = examClassMapper.listClassUserVo(classDto);
        int total = examClassMapper.countClassUserVo(classDto);
        Page<ClassUserVo> page = new Page<>(getPageIndex(), getPageSize(), total);
        page.setRecords(list);
        return Result.success(page);
    }



}
