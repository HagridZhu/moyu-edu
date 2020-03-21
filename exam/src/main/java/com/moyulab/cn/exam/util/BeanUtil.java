package com.moyulab.cn.exam.util;

import org.springframework.beans.BeanUtils;

public class BeanUtil {

    public static void copy(Object src, Object tag){
        BeanUtils.copyProperties(src, tag);
    }


}
