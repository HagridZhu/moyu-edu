package com.moyulab.cn.exam.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class SysUser {
    @TableId
    private Long userId;
    private String userName;
    private String password;
    private Date createDate;

}
