package com.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.library.pojo.BorrowRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @Author: zbq
 * @Date: 2023/4/13 下午1:22
 */
public interface BorrowMapper extends BaseMapper<BorrowRecord> {
    Integer updateFlagById(Integer id);

    Integer getBid(Integer id);

    Integer renewById(@Param("id") Integer id, @Param("days") Integer days);
}
