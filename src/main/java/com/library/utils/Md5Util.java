package com.library.utils;

import org.springframework.util.DigestUtils;

/**
 * @Author: zbq
 * @Date: 2023/3/26 下午2:58
 * MD5加密工具类
 */
public class Md5Util {
    /**
     * 经典Hash加密，将明文password通过加盐后经过md5处理返回结果
     * @param password
     * @param salt
     * @return
     */
    public static String getMd5Password(String password,String salt){
        for(int i=0;i<3;i++){
            password= DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
