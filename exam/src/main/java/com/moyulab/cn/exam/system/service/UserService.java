package com.moyulab.cn.exam.system.service;

import java.util.List;

public interface UserService {

    List<String> listRole(Long userId);
    List<String> listStringPermission(Long userId);
}
