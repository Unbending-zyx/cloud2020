package com.yuxiao.springcloud.serivce;

public interface PaymentService {

    public String paymentInfo_OK(Integer id);

    public String paymentInfo_ERR(Integer id);

    public String paymentCircuitBreaker(Integer id);
}
