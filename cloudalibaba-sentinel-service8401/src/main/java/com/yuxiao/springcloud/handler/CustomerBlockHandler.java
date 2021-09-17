package com.yuxiao.springcloud.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.yuxiao.springcloud.entry.CommonResult;

public class CustomerBlockHandler {

    public static CommonResult handlerException(BlockException blockException){
        return new CommonResult(4444, "按客户自定义 global handlerException 处理  ----------1",null);
    }

    public static CommonResult handlerException2(BlockException blockException){
        return new CommonResult(4444, "按客户自定义 global handlerException 处理  ----------2",null);
    }
}
