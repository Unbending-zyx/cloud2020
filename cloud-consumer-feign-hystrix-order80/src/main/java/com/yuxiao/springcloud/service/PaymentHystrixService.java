package com.yuxiao.springcloud.service;

import com.yuxiao.springcloud.service.fallback.PaymentFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//fallback 配置服务降级处理类  该类实现了当前接口
@FeignClient(value = "cloud-provider-hystrix-payment",fallback = PaymentFallbackService.class)
@Service
public interface PaymentHystrixService {
    @GetMapping(value = "/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id);
    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    public String paymentInfo_ERR(@PathVariable("id") Integer id);
}
