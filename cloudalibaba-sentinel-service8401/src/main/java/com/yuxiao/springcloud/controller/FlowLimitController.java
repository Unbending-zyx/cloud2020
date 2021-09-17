package com.yuxiao.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping(value = "/testA")
    public String testA(){
        return "-------- testA";
    }

    @GetMapping(value = "/testB")
    public String testB(){
        log.info(Thread.currentThread().getName()+"\t"+"......testB");
        return "-------- testB";
    }

    @GetMapping(value = "/testD")
    public String testD(){
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        log.info("......testD 测试 RT（平均响应时间）");

        log.info("testD 异常比例测试");
        int i=10/0;
        return "-------- testD";
    }

    @GetMapping(value = "/testE")
    public String testE(){
        log.info("testE 测试异常数 ");
        int i=10/0;
        return "-------- testE 测试异常数";
    }

    @GetMapping(value = "/testHotKey")
    //该注解是sentinel中的注解  可以用于自定义熔断降级方法
    //value 中的值可以随便写  但是必须唯一  可以作为控制台中配置时的资源名
    // blockHandler中定义的就是当服务断路时  执行的方法
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKye")
    public String testHotKey(@RequestParam(value = "name",required = false)String name,
                             @RequestParam(value = "age",required = false)String age){
//        int i=10/0;
        return "------------ testHotKey --------------";
    }

    //该方法为自定义的服务降级熔断方法  当请求断路时 就是执行该方法
    public String deal_testHotKye(String name, String age, BlockException blockException){
        return "------------ deal_testHotKye  ,执行服务降级方法 --------------"; //等同于sentinel的默认错误提示 但是该方法是自定义的
    }

}