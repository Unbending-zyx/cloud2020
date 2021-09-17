package com.yuxiao.springcloud.alibaba.dao;

import com.yuxiao.springcloud.alibaba.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {
    //1.新建订单
    void create(Order order);
    //2.修改订单状态  从0 改为1
    int update(@Param("userId") Long userId,@Param("status") Integer status);
}
