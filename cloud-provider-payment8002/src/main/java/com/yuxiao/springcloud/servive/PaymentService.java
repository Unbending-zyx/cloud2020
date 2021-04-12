package com.yuxiao.springcloud.servive;

import com.yuxiao.springcloud.entry.Payment;

public interface PaymentService {
    public int create(Payment payment);
    public Payment getPaymentById(Long id);
}
