package com.dodoca.create_image.controller;

import com.dodoca.create_image.exception.BaseResult;
import com.dodoca.create_image.exception.ResultUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: TianGuangHui
 * @Date: 2019/6/19 11:29
 * @Description:
 */
@RestController
public class RootController {

    @RequestMapping("/")
    public BaseResult root() {
        return ResultUtil.success("create_image server");
    }
}
