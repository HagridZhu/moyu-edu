package com.moyulab.cn.exam.service;

import com.moyulab.cn.exam.common.BaseEntity;

import java.util.List;

public interface CommonService {

    void setCreateBy(List<? extends BaseEntity> list);
}
