package com.moyulab.cn.exam.system.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysUser {
    private Long userId;
    private String userName;
    private String password;
    private Date createDate;

}
