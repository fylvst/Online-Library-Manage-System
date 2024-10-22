package com.library.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.library.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author admin
* @description 针对表【category】的数据库操作Service
* @createDate 2023-05-07 16:33:05
*/
public interface CategoryService extends IService<Category> {
    void deleteByCid(Integer cid);

    List<Category> getAllCate();
}
