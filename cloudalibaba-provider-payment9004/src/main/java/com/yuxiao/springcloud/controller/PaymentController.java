package com.yuxiao.springcloud.controller;

import com.yuxiao.springcloud.entry.CommonResult;
import com.yuxiao.springcloud.entry.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

@RestController
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    public static HashMap<Long, Payment> hashMap=new HashMap<>(16);

    static{
        hashMap.put(1L,new Payment(1L, "1:"+UUID.randomUUID().toString()));
        hashMap.put(2L,new Payment(2L, "2:"+UUID.randomUUID().toString()));
        hashMap.put(3L,new Payment(3L, "3:"+UUID.randomUUID().toString()));
    }

    @GetMapping(value = "/paymentSQL/{id}")
    public CommonResult paymentSQL(@PathVariable("id") Long id){
        Payment payment=hashMap.get(id);
        CommonResult result=new CommonResult(200,"查询成功 端口为："+serverPort,payment);
        return result;
    }
}
