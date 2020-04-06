package com.moyulab.cn.exam.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.moyulab.cn.exam.common.BaseEntity;
import lombok.Data;

@Data
public class SysUser extends BaseEntity {
    @TableId
    private Long userId;
    private String userName;
    private String password;
    private String avatar;
    private String nick;

}
