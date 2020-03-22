package com.moyulab.cn.exam.util;

import org.springframework.beans.BeanUtils;

public class BeanUtil {

    public static <T> T copy(Object src, T tag){
        BeanUtils.copyProperties(src, tag);
        return tag;
    }


}
