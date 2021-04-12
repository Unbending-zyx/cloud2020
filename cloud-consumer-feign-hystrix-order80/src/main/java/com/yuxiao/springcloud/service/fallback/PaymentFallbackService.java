package com.yuxiao.springcloud.service.fallback;

import com.yuxiao.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "~~~~~~~~~~PaymentFallbackService  paymentInfo_OK  fallback~~~~~~~~~~~~~";
    }

    @Override
    public String paymentInfo_ERR(Integer id) {
        return "~~~~~~~~~~PaymentFallbackService  paymentInfo_ERR  fallback~~~~~~~~~~~~~";
    }
}
