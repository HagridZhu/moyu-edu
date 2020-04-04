package com.moyulab.cn.exam.enums;


public enum AnswerStatusEnum {

    正确(1),错误(0);

    private int value;

    private AnswerStatusEnum(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

}
