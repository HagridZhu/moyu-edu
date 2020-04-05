package com.moyulab.cn.exam.vo;

import com.moyulab.cn.exam.entity.ExamClass;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClassUserVo extends ExamClass {
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("用户名")
    private String userName;

}
