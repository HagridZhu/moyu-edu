package com.moyulab.cn.exam.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("haige/exam/test")
@RestController
public class TestController {

    @GetMapping("get")
    public Object get(String name){
        return "get name=" + name;
    }
}
