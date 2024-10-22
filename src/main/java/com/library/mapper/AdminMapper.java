package com.library.mapper;

import com.library.pojo.Administrator;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: zbq
 * @Date: 2023/3/23 下午8:08
 */
@Repository
public interface AdminMapper {
    /**
     * 插入admin用户数据
     *
     * @return 受影响行数
     */
    Integer insertAdmin(Administrator admin);

    /**
     * 根据用户名查询用户数据
     * @param username
     * @return admin对象
     */
    Administrator findByUsername(@Param("username") String username);

    /**
     * 根据用户id查admin用户
     * @param aid
     * @return Patron对象
     */
    Administrator findByAid(@Param("aid") Integer aid);
}
