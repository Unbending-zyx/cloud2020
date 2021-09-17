package com.yuxiao.springcloud.controller;

import com.yuxiao.springcloud.service.IMessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMsgController {

    @Autowired
    private IMessageProvider messageProvider;

    @GetMapping(value = "/sendMsg")
    public String sendMsg(@RequestParam(value = "msg",required = true,defaultValue = "123") String message){
        return messageProvider.sendMsg(message);
    }

}
