package com.moyulab.cn.exam.web;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试接口")
@RequestMapping("haige/exam/test")
@RestController
public class TestController {

    @GetMapping("get")
    public Object get(String name){
        return "get name=" + name;
    }
}
