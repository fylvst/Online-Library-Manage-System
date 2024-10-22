package com.library.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.library.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author admin
* @description 针对表【category】的数据库操作Mapper
* @createDate 2023-05-07 16:33:05
* @Entity com.library.generator.domain.Category
*/
public interface CategoryMapper extends BaseMapper<Category> {
    int deleteByCid(@Param("cid") Integer cid);

    List<Category> getAllCate();
}




