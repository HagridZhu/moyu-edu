package com.moyulab.cn.exam.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class SysUserRole {
    @TableId
    private Long id;
    private Long userId;
    private Long roleId;
    private Date createDate;

}
