package com.swagger2word.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: swagger ui的快速重定向接口
 * @author: QL Zhang
 * @time: 2020/08/13 11:10
 **/

@Controller
public class IndexController {
    @ApiIgnore
    @RequestMapping(value = "/")
    public String index(HttpServletRequest request) {
        return "redirect:swagger-ui.html";
    }
}
