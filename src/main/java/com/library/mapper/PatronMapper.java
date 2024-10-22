package com.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.library.pojo.Patron;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zbq
 * @Date: 2023/4/9 下午8:16
 */
public interface PatronMapper extends BaseMapper<Patron> {
    /**
     * 插入普通用户数据
     * @param patron
     * @return 受影响行数
     */
    Integer insertPatron(Patron patron);

    /**
     * 根据用户名查询用户数据
     * @param username
     * @return Patron对象
     */
    Patron findByUsername(@Param("username") String username);

    /**
     * 根据用户id查patron用户
     * @param pid
     * @return Patron对象
     */
    Patron findByPid(@Param("pid") Integer pid);

    /**
     * 根据Parton对象修改信息
     * @param patron
     * @return 受影响行数
     */
    Integer updateInfoByUid(Patron patron);

    /**
     * 查询所有patron信息
     * @return list
     */
    List<Patron> listAll();

    Integer deleteByPid(Integer pid);
}
