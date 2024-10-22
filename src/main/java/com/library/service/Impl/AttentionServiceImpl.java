package com.library.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.mapper.AttentionMapper;
import com.library.pojo.Attention;
import com.library.service.AttentionService;
import com.library.service.exception.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author mlovek
* @description 针对表【attention】的数据库操作Service实现
* @createDate 2023-05-08 14:35:05
*/
@Service
public class AttentionServiceImpl extends ServiceImpl<AttentionMapper, Attention>
    implements AttentionService{

    @Autowired
    private AttentionMapper mapper;
    @Override
    public void addAttention(Attention attention) {
        int insert = mapper.insert(attention);
        if (insert != 1){
            throw new InsertException("insert exception");
        }
    }
}




