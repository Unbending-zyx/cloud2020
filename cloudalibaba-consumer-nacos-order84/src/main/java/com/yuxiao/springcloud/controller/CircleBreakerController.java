package com.yuxiao.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.yuxiao.springcloud.entry.CommonResult;
import com.yuxiao.springcloud.entry.Payment;
import com.yuxiao.springcloud.service.PaymentSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class CircleBreakerController {
    public static final String SERVICE_URL="http://nacos-payment-provider";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/fallback/{id}")
//    @SentinelResource(value = "fallback") //无任何配置
//    @SentinelResource(value = "fallback",fallback = "handlerFallback") //fallback只负责业务异常
//    @SentinelResource(value = "fallback",blockHandler = "blockHandler") //blockHandler只负责sentinel控制台配置的违规
    @SentinelResource(value = "fallback",blockHandler = "blockHandler",fallback = "handlerFallback") //blockHandler只负责sentinel控制台配置的违规
    public CommonResult fallback(@PathVariable("id") Long id){
        CommonResult result=restTemplate.getForObject(SERVICE_URL+"/paymentSQL/"+id,CommonResult.class);
        if (id==4){
            throw new IllegalArgumentException("IllegalArgumentException , 非法参数异常。。。。");
        }else if(result.getData()==null){
            throw new NullPointerException("NullPointerException ,该 ID 没有对应记录，空指针异常");
        }
        return result;
    }

    //fallback 处理方法
    public CommonResult handlerFallback(@PathVariable Long id,Throwable e){
        Payment payment=new Payment(id,"null");
        return new CommonResult(444,"业务逻辑出现异常 执行fallback方法：handlerFallback ，exception内容为"+e.getMessage(),payment);
    }

    //blockHandler 处理方法
    public CommonResult blockHandler(@PathVariable Long id, BlockException b) {
        Payment payment = new Payment(id, "null");
        return new CommonResult(445, "blockHandler-sentinel限流 执行blockHandler方法：blockHandler ，exception内容为" + b.getMessage(), payment);
    }

    @Autowired
    private PaymentSerivce paymentSerivce;

    @GetMapping(value = "/consumer/paymentSQL/{id}")
    public CommonResult paymentSL(@PathVariable("id") Long id){
        return paymentSerivce.paymentSQL(id);
    }
}
