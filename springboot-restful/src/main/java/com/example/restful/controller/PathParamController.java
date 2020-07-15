package com.example.restful.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@RestController
@Slf4j
public class PathParamController {
    /**
     * 访问报404不知啥原因
     *
     * @author: QL Zhang
     * @time: 2020/7/10 17:20
     */
    @GET
    @Path("/test1/{scheme}/{mount}/{dir}")
    public void test(@PathParam("scheme") String scheme, @PathParam("mount") String mount, @PathParam("dir") String dir, HttpServletRequest request){
        log.info("url={}", request.getRequestURL());
        log.info("scheme:{}, mount:{}, dir:{}", scheme, mount, dir);
    }

    /**
     * 路径中多个变量
     * 请求处理正常
     *
     * @author: QL Zhang
     * @time: 2020/7/15 11:21
     */
    @GetMapping("/test2/{scheme}/{mount}/{dir}")
    public void testPathVariable(@PathVariable("scheme") String scheme, @PathVariable("mount") String mount, @PathVariable("dir") String dir, HttpServletRequest request){
        log.info("url={}", request.getRequestURL());
        log.info("scheme:{}, mount:{}, dir:{}", scheme, mount, dir);
    }
}
