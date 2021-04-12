package com.yuxiao.springcloud.controller;

import com.yuxiao.springcloud.entry.CommonResult;
import com.yuxiao.springcloud.entry.Payment;
import com.yuxiao.springcloud.servive.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    public PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

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
