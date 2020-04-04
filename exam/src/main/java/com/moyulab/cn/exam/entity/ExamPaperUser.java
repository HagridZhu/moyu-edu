package com.moyulab.cn.exam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.moyulab.cn.exam.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 答卷
 */
@Data
public class ExamPaperUser extends BaseEntity {
    @TableId
    private Long paperUserId; //主键
    private Long paperId; //试卷id
    private Integer userScore; //用户总分
    @ApiModelProperty(value = "答卷情况：0=待考，1=已考")
    private Integer paperStatus;

}
