package com.moyulab.cn.exam.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClassDto extends BaseDto {

    @ApiModelProperty("班级id")
    private Long classId;
    @ApiModelProperty("年级")
    private Integer grade;
    @ApiModelProperty("班别")
    private Integer classNum;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("创建人")
    private Long createBy;

}
