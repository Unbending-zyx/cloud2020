package com.yuxiao.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义Ribbon负载配置类
 */
@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule(){
        /**
         * ribbon中一共定义了7种负载规则
         *
         */
        return new RandomRule();//定义为随机访问
    }
}
