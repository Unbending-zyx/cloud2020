package com.yuxiao.springcloud.servive.impl;

import com.yuxiao.springcloud.dao.PaymentDao;
import com.yuxiao.springcloud.entry.Payment;
import com.yuxiao.springcloud.servive.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
