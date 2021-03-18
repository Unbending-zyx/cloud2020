package com.yuxiao.springcloud.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //全参构造
@NoArgsConstructor //空参构造
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;

    private CommonResult(Integer code,String message){
        this(code,message,null);
    }
}
