package com.moyulab.cn.exam.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.moyulab.cn.exam.util.RequestUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动设置公共字段
 */
@Component
public class MoyuMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createBy", RequestUtil.getUserIdStr(), metaObject);
        this.setFieldValByName("createDate", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("lastUpdateBy", RequestUtil.getUserIdStr(), metaObject);
        this.setFieldValByName("lastUpdateDate", new Date(), metaObject);
    }

}
