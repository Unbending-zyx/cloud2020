package com.yuxiao.springcloud.alibaba.service.impl;

import com.yuxiao.springcloud.alibaba.dao.OrderDao;
import com.yuxiao.springcloud.alibaba.domain.Order;
import com.yuxiao.springcloud.alibaba.service.AccountService;
import com.yuxiao.springcloud.alibaba.service.OrderService;
import com.yuxiao.springcloud.alibaba.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private StorageService storageService;

    @Override
    public void create(Order order) {
        log.info("==========开始新建订单==============");
        //1.新建订单
        orderDao.create(order);
        log.info("==========订单微服务开始调用库存，做扣减库存操作 start ==============");
        //2.减库存操作
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("==========订单微服务开始调用库存，做扣减库存操作 end ==============");
        log.info("==========订单微服务开始调用账户，做扣减账户余额操作 start ==============");
        //3.减账户余额操作
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("==========订单微服务开始调用账户，做扣减账户余额操作 end ==============");

        //4.修改订单状态  从0 到1  1：代表订单完成
        log.info("==========修改订单状态 start ==============");
        //此处设计存在问题  不应通过用户id修改订单状态
        orderDao.update(order.getUserId(),0);
        log.info("==========修改订单状态 end ==============");

        log.info("==========订单创建操作结束==============");

    }

    @Override
    public int updata(Long id, Integer status) {
        return 0;
    }
}
