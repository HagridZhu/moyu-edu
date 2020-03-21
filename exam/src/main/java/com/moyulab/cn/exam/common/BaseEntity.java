package com.moyulab.cn.exam.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
public class BaseEntity {
    @ApiModelProperty(value = "创建人id")
    @TableField(fill= FieldFill.INSERT)
    protected String createBy;
    @ApiModelProperty(value = "创建时间")
    @TableField(fill= FieldFill.INSERT)
    protected Date createDate;
    @ApiModelProperty(value = "最后更新人id")
    @TableField(fill= FieldFill.UPDATE)
    protected String lastUpdateBy;
    @ApiModelProperty(value = "最后更新时间")
    @TableField(fill=FieldFill.UPDATE)
    protected Date LastUpdateDate;

}
