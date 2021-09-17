package com.yuxiao.springcloud.service;

import com.yuxiao.springcloud.entry.CommonResult;
import com.yuxiao.springcloud.entry.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentSerivce {
    @Override
    public CommonResult paymentSQL(Long id) {
        return new CommonResult(44444,"服务降级返回，通用的openFeign异常处理方法 --PaymentFallbackService",new Payment(id,"errorSerial"));
    }
}
