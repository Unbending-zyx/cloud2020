package com.yuxiao.springcloud.dao;

import com.yuxiao.springcloud.entry.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentDao {
    //payment表的读写操作

    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
}
