package com.yuxiao.springcloud.alibaba.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.yuxiao.springcloud.alibaba.dao"})
public class MybatisConfig {
}
