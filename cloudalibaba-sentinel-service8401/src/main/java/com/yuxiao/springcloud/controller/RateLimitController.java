package com.yuxiao.springcloud.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.yuxiao.springcloud.entry.CommonResult;
import com.yuxiao.springcloud.entry.Payment;
import com.yuxiao.springcloud.handler.CustomerBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class RateLimitController {

    @GetMapping(value = "/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handelException")
    public CommonResult byResource() {
        return new CommonResult(200, "按资源名限流测试pk", new Payment(2021L, "serial001"));
    }

    public CommonResult handelException(BlockException blockException) {
        return new CommonResult(444, blockException.getClass().getCanonicalName() + "\t" + "服务不可用", null);
    }


    @GetMapping(value = "/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public CommonResult byUrl() {
        return new CommonResult(200, "按Url限流测试仪成功", new Payment(2021L, "serial002"));
    }

    @GetMapping(value = "/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",blockHandlerClass = CustomerBlockHandler.class,blockHandler = "handlerException2")
    public CommonResult customerBlockHandler() {
        return new CommonResult(200, "按客户自定义  处理", new Payment(2021L, "serial003"));
    }

}
