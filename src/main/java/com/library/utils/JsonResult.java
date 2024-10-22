package com.library.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: zbq
 * @Date: 2023/3/26 下午2:49
 * 返回响应结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class JsonResult<E> implements Serializable {
    // 状态码
    private Integer code;
    //状态描述信息
    private String message;
    //返回数据
    private E data;
    public JsonResult(Integer code){
        this.code=code;
    }
    public JsonResult(Integer code,E data){
        this.code=code;
        this.data=data;
    }
}
