package com.yuxiao.springcloud.service;

import com.yuxiao.springcloud.entry.Payment;

public interface PaymentService {
    //创建payment
    public int create(Payment payment);

    /**
     * 根据id查询payment
     * @param id
     * @return
     */
    public Payment getPaymentById(Long id);
}
