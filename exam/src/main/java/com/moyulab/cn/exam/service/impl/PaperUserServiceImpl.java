package com.moyulab.cn.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moyulab.cn.exam.entity.ExamPaperUser;
import com.moyulab.cn.exam.mapper.ExamPaperUserMapper;
import com.moyulab.cn.exam.service.PaperUserService;
import org.springframework.stereotype.Service;

@Service
public class PaperUserServiceImpl extends ServiceImpl<ExamPaperUserMapper, ExamPaperUser> implements PaperUserService {
}
