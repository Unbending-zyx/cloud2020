package com.yuxiao.springcloud.lb.impl;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;
import com.yuxiao.springcloud.lb.LoadBalancer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements LoadBalancer {

//    原子操作的Integet  内部实现原理是 CAS和自旋锁
    private AtomicInteger atomicInteger=new AtomicInteger(0);


    /**
     *
     * 给AtomicInteger自增一   先获取  然后自增
     * 该方法是用来获取是第几次来访问服务
     * @return
     */
    public final int getAndIncrement(){
        int current;
        int next;
        //该for循环  是一个自旋锁  当没有赋值成功并反回时  就会一直执行（一直重试  直至成功）  这是一种乐观锁
        for (;;){
            //取出原子操作数
            current=this.atomicInteger.get();
            //给next赋值  next= current+1
            next= current>=Integer.MAX_VALUE?0:current+1;
            /**
             * compareAndSet()方法 先拿current与对应jvm内存地址中的该数进行比较  如果相同  则将next赋值到该jvm内存地址中
             * 如果不同  则不进行赋值
             */
            if (this.atomicInteger.compareAndSet(current,next)){
                System.out.println("第几次访问次数为next  当前返回next next："+next);
                return next;
            }
        }
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstanceList) {
        int index=getAndIncrement() % serviceInstanceList.size();
        return serviceInstanceList.get(index);
    }
}
