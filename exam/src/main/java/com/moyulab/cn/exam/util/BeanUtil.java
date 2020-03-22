package com.moyulab.cn.exam.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class BeanUtil {

    public static <T> T copy(Object src, T tag){
        BeanUtils.copyProperties(src, tag);
        return tag;
    }

    public static void copyList(List src, List tag){
        if (CollectionUtils.isEmpty(src) || CollectionUtils.isEmpty(tag)) {
            return ;
        }
        for(int i = 0; i < src.size(); i++){
            copy(src.get(i), tag.get(i));
        }
    }


}
