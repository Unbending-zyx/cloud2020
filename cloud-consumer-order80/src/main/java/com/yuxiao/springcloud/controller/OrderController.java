package com.yuxiao.springcloud.controller;

import com.yuxiao.springcloud.entry.CommonResult;
import com.yuxiao.springcloud.entry.Payment;
import com.yuxiao.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    public static final String PAYMENT_URL="http://CLOUD-PAYMENT-SERVICE";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancer loadBalancer;
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentLB(){
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        if (instances==null || instances.size()<=0){
            log.info("error ========》 获取服务列表为空");
            return null;
        }
        ServiceInstance serviceInstance=loadBalancer.instances(instances);
        URI uri = serviceInstance.getUri();
        System.out.println("获取的uri是："+uri);
        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }






    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    @GetMapping("/consumer/payment/getForEntry/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id){
        ResponseEntity<CommonResult> entry=restTemplate.getForEntity(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);

        if (entry.getStatusCode().is2xxSuccessful()){
            return entry.getBody();
        }else{
            return new CommonResult<>(444,"查询失败",null);
        }


    }
}
