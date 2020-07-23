package com.example.restful.controller;


import com.example.restful.annotation.WildcardParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * 路径参数请求方法示例
 * 通配符路径参数内容的获取要用到自定义注解类WildcardParam和配置类WebMvcConfig。
 *
 * @author: QL Zhang
 * @time: 2020/7/23 11:14
 */
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

    /**
     * 表示name满足4--32位以字母开头的字母与数字字符，并且不包括fonts、oauth、webjars、swagger、images这几个词。
     * 参考 https://blog.csdn.net/liming19890713/article/details/87366137
     * @author: QL Zhang
     * @time: 2020/7/15 15:44
     */
    @RequestMapping(value = "/test1/{name:(?!fonts|oauth|webjars|swagger|images)[a-z][0-9a-z-]{3,31}}",method = {RequestMethod.GET})
    public void test1(@PathVariable String name, HttpServletRequest request){
        log.info("url={}", request.getRequestURL());
        log.info("执行test1方法，name={}", name);
    }

    /**
     * 示例：/test2/ddffe222a/mytest
     *
     * @author: QL Zhang
     * @time: 2020/7/15 16:03
     */
    @RequestMapping(value = "/test2/{name:[a-z][0-9a-z-]{3,31}}/mytest",method = {RequestMethod.GET})
    public void test2(@PathVariable String name, HttpServletRequest request){
        log.info("url={}", request.getRequestURL());
        log.info("执行test2方法，name={}", name);
    }

    /**
     * 表示name满足4--32位以字母开头的字母与数字字符，
     *
     * url=http://localhost:8080/test3/ddffe222a/mytest/dfsaf
     * @author: QL Zhang
     * @time: 2020/7/15 15:45
     */
    @RequestMapping(value = "/test3/{name:[a-z][0-9a-z-]{3,31}}/**",method = {RequestMethod.GET})
    public void test3(@PathVariable String name, HttpServletRequest request){
        log.info("url={}", request.getRequestURL());
        log.info("执行test3方法，name={}", name);
    }

    /**
     * 用**通配符匹配包含/的多种路径，如下示例：
     * url=http://localhost:8080/test4/zql/ddddd/dfsaf/mytest
     * **=ddddd/dfsaf/mytest
     * 自定义注解然后覆写addArgumentResolvers方法将其注入，实现自己对**内容的解析并赋值给变量
     *
     * @author: QL Zhang
     * @time: 2020/7/15 16:25
     */
    @GetMapping(value = "/test4/{name}/**")
    public void test4(@PathVariable String name, @WildcardParam String path, HttpServletRequest request){
        log.info("url={}", request.getRequestURL());
        log.info("执行test3方法，name={}", name);
        log.info("**={}", path);
    }

}
