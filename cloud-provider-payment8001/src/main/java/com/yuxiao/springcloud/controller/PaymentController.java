package com.yuxiao.springcloud.controller;

import com.yuxiao.springcloud.entry.CommonResult;
import com.yuxiao.springcloud.entry.Payment;
import com.yuxiao.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = "/create")
    public CommonResult<Payment> create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("执行创建payment 返回值为："+result);
        if(result>0){
            return new CommonResult(200,"插入payment成功",result);
        }else{
            return new CommonResult(500,"插入payment失败",null);
        }
    }

    @GetMapping(value = "/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("执行搜索payment 返回值为："+payment);
        if(payment!=null){
            return new CommonResult<Payment>(200,"查询payment成功",payment);
        }else{
            return new CommonResult(200,"未找到对应记录，查询id为："+id,null);
        }
    }
}
