package com.yuxiao.springcloud.controller;

import com.yuxiao.springcloud.entry.CommonResult;
import com.yuxiao.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderFeignController {

    @Autowired
    private PaymentFeignService paymentFeignService;


    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        log.info("使用feign调用服务提供者");
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/payment/feign/timeout")
    public String paymentFeignTimeout(){
        //openfeign-ribbon客户端默认等待1s   但是服务提供者方法需要3s响应  这时就会报错
        log.info("测试feign超时测试");
        return paymentFeignService.paymentFeignTimeout();
    }

}
