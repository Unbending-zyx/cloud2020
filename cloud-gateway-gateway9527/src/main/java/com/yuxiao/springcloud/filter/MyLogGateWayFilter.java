package com.yuxiao.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter, Ordered {

    /**
     * 过滤请求的过滤器方法  验证请求中是否包含username
     * @param exchange 相当于请求内容 同zuul中的RequestContext
     * @param chain 过滤链
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("================== come in MyLogGateWayFilter:"+new Date()+" ==================");
        String username = exchange.getRequest().getQueryParams().getFirst("username");
        if(username==null){
            log.info("********** 用户名为空 访问拦截 ***********");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();//请求完成退出
        }
        return chain.filter(exchange);
    }

    /**
     * 用于管理加载过滤器的顺序  数字越小  优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
