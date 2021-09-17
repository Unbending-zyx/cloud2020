package com.yuxiao.springcloud.alibaba.service;

import com.yuxiao.springcloud.alibaba.domain.Order;

public interface OrderService {
    void create(Order order);
    int updata(Long id,Integer status);
}
