package com.moyulab.cn.exam.service.impl;

import com.moyulab.cn.exam.common.BaseEntity;
import com.moyulab.cn.exam.service.CommonService;
import com.moyulab.cn.exam.system.entity.SysUser;
import com.moyulab.cn.exam.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private UserService userService;

    @Override
    public void setCreateBy(List<? extends BaseEntity> list) {
        if (CollectionUtils.isEmpty(list)) {
            return ;
        }
        List<Long> userIdList = new ArrayList<>(list.size());
        for (BaseEntity e : list) {
            String createBy = e.getCreateBy();
            if (createBy != null) {
                Long userId = toLong(createBy, null);
                if (userId != null) {
                    userIdList.add(userId);
                }
            }
        }
        List<SysUser> sysUserList = userService.listByIds(userIdList);
        if (CollectionUtils.isEmpty(sysUserList)) {
            return ;
        }
        for (BaseEntity e : list) {
            for (SysUser u : sysUserList) {
                if (Objects.equals(e.getCreateBy(), String.valueOf(u.getUserId()))) {
                    e.setCreateBy(u.getNick());
                }
            }
        }
    }

    private Long toLong(String str, Long defaultValue){
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.valueOf(str);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

}
