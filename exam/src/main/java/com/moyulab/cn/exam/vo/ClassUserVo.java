package com.moyulab.cn.exam.vo;

import com.moyulab.cn.exam.entity.ExamClass;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClassUserVo extends ExamClass {
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("账号")
    private String userName;
    @ApiModelProperty("昵称")
    private String nick;

}
