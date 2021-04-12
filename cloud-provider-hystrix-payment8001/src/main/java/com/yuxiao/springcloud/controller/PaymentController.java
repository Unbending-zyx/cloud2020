package com.yuxiao.springcloud.controller;

import com.yuxiao.springcloud.serivce.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String servicePort;

//  ===========服务降级=============
    @GetMapping(value = "/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        log.info("retult :   "+paymentService.paymentInfo_OK(id));
        return "retult :   "+paymentService.paymentInfo_OK(id);
    }
    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    public String paymentInfo_ERR(@PathVariable("id") Integer id){
        log.info("retult :   "+paymentService.paymentInfo_ERR(id));
        return "retult :   "+paymentService.paymentInfo_ERR(id);
    }

//    ===============服务熔断===============

    @GetMapping(value = "/payment/circuit/{id}")
    public String payCircuitBreaker(@PathVariable("id") Integer id){
        String result=paymentService.paymentCircuitBreaker(id);
        log.info("   result:  "+result);
        return result;
    }





}
