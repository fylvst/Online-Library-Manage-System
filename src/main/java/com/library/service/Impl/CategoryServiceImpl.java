package com.library.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.pojo.Category;
import com.library.service.CategoryService;
import com.library.mapper.CategoryMapper;
import com.library.service.exception.UpdateDataBaseException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author admin
* @description 针对表【category】的数据库操作Service实现
* @createDate 2023-05-07 16:33:05
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Resource
    CategoryMapper mapper;
    @Override
    public void deleteByCid(Integer cid) {
        int row = mapper.deleteByCid(cid);
        if(row!=1) throw new UpdateDataBaseException("update database exception");
    }

    @Override
    public List<Category> getAllCate() {
        return mapper.getAllCate();
    }
}




