package com.pangpan.springcloud.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 使用泛型，为了通用
 * @param <T>
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    private  Integer code;
    private  String message;
    private  T data;

    public  CommonResult(Integer code,String message)
    {
        this(code,message,null);

    }
}
