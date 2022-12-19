package com.redpack.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @time 2022/12/13
 */
@Api(tags = "测试接口类")
@RestController
@RequestMapping("/hi")
public class HelloController {

    @ApiOperation("测试hello")
    @PostMapping("/hello")
    public String hello(){
        return "hello";
    }


}
