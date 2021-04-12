package com.yuxiao.springcloud.serivce.impl;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.yuxiao.springcloud.serivce.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PaymentServiceImpl implements PaymentService {

//    ==============服务降级================

    /**
     * 正常访问方法
     * @param id
     * @return
     */
    @Override
    public String paymentInfo_OK(Integer id) {
        return "线程池："+Thread.currentThread().getName()+" paymentInfo_OK  ,  id="+id+"\t"+"成功了";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_ERR_Handler",commandProperties = {
            //设置接口超时时间峰值为5000毫秒
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="5000")
    })
    @Override
    public String paymentInfo_ERR(Integer id) {
//        int a=10/0;
//        return "执行成功";
        int time=3000;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池："+Thread.currentThread().getName()+" paymentInfo_ERR  ,  id="+id+"\t"+"超时失败了"+"  耗时："+time/1000+"s";

    }

    /**
     * paymentInfo_ERR方法执行失败  该方法为其兜底
     * @param id
     * @return
     */
    public String paymentInfo_ERR_Handler(Integer id) {
        return "服务提供者的paymentInfo_ERR方法执行失败了，执行服务降级方法paymentInfo_ERR_Handler   线程池："+Thread.currentThread().getName()+" paymentInfo_ERR_Handler  ,  id="+id+"   系统繁忙，请稍后重试";
    }



//    =============== 服务熔断 ===============

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback" ,commandProperties = {
            //配置以下参数的意义：
            //在 10000毫秒（10s）的时间窗口期中10次请求中有60%的请求时失败的  这时开启断路器

        @HystrixProperty(name="circuitBreaker.enabled",value="true"), //是否开启断路器
        @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10"), // 请求次数
        @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"), // 时间窗口期
        @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="60"), // 失败率达到多少后跳闸
    })
    @Override
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id<0){
            throw new RuntimeException("id不能为负");
        }
        String serialNumber= IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功  流水号为："+serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能为负数  请稍后重试   id："+id;
    }

}