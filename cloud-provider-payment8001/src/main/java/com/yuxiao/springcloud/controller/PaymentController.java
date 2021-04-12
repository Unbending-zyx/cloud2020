package com.yuxiao.springcloud.controller;

import ch.qos.logback.core.util.TimeUtil;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import com.yuxiao.springcloud.entry.CommonResult;
import com.yuxiao.springcloud.entry.Payment;
import com.yuxiao.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DiscoveryClient discoveryClient;
    /**
     * 服务发现接口  用于查看当前服务的信息
     * @return
     */
    @GetMapping(value = "/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String service: services) {
            log.info("+++++++++ service:"+service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance s:instances) {
            log.info(s.getInstanceId()+"\t"+s.getHost()+"\t"+s.getPort()+"\t"+s.getUri());
        }
        return discoveryClient;
    }

    @PostMapping(value = "/create")
    public CommonResult<Payment> create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        if (result>0){
            return new CommonResult(200,"插入payment成功 serverPort:"+serverPort,result);
        }else{
            return new CommonResult(500,"插入payment失败 serverPort:"+serverPort,null);
        }
    }

    @GetMapping(value = "/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        if(payment!=null){
            return new CommonResult<Payment>(200,"查询payment成功 serverPort："+serverPort,payment);
        }else{
            return new CommonResult(200,"未找到对应记录 ，serverPort："+serverPort+"  查询id为："+id,null);
        }
    }

    @GetMapping(value = "/lb")
    public String getPaymentLB(){
        return serverPort;
    }


    /**
     * 用于测试feign的超时控制
     * @return
     */
    @GetMapping(value = "/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
