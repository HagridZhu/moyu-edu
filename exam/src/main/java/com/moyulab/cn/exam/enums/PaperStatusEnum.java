package com.moyulab.cn.exam.enums;

public enum PaperStatusEnum {
    已考(1),待考(0);

    private int value;

    private PaperStatusEnum(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
