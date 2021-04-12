package com.yuxiao.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.yuxiao.springcloud.service.PaymentHystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbakcMethod")
public class OrderHystrixController {

    @Autowired
    private PaymentHystrixService paymentHystrixService;

    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = paymentHystrixService.paymentInfo_OK(id);
        log.info(result);
        return result;
    }

//    @HystrixCommand(fallbackMethod = "paymentInfo_ERR_Handler",commandProperties = {
//            //设置接口超时时间峰值为1500毫秒
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1500")
//    })
    @HystrixCommand
    @GetMapping(value = "/consumer/payment/hystrix/timeout/{id}")
    public String paymentInfo_ERR(@PathVariable("id") Integer id){
        int a=10/0;
        String result = paymentHystrixService.paymentInfo_ERR(id);
        log.info(result);
        return result;
    }

    /**
     * paymentInfo_ERR方法执行失败  该方法为其兜底
     * @param id
     * @return
     */
    public String paymentInfo_ERR_Handler(Integer id) {
        return "80 服务消费者的paymentInfo_ERR方法执行失败了，执行服务降级方法paymentInfo_ERR_Handler   线程池："+Thread.currentThread().getName()+" paymentInfo_ERR_Handler  ,  id="+id+"   系统繁忙，请稍后重试";
    }

    /**
     * 全局默认服务降级方法
     * @return
     */
    public String payment_Global_FallbakcMethod(){
        return "这是默认的全局异常处理方法  是一个服务降级方法 Global   请稍后重试";
    }

}
