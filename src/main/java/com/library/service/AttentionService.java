package com.library.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.library.pojo.Attention;

/**
* @author mlovek
* @description 针对表【attention】的数据库操作Service
* @createDate 2023-05-08 14:35:05
*/
public interface AttentionService extends IService<Attention> {

    void addAttention(Attention attention);
}
