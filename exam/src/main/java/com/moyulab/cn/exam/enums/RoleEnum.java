package com.moyulab.cn.exam.enums;

public enum RoleEnum {
    管理员(1),老师(2),学生(3);

    private int value;

    private RoleEnum(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
