package com.library.mapper;

import com.library.pojo.Dashboard;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: zbq
 * @Date: 2023/5/29 下午12:27
 */
public interface DashMapper {
    Dashboard getDash();
    void updateMoney(@Param("money") double money);
    void update();
}
