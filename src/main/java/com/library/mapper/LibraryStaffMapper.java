package com.library.mapper;

import com.library.pojo.LibraryStaff;
import com.library.pojo.Patron;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zbq
 * @Date: 2023/4/10 上午10:56
 */
public interface LibraryStaffMapper {
    /**
     * 插入员工数据
     * @param staff
     * @return 受影响行数
     */
    Integer insert(LibraryStaff staff);

    /**
     * 根据用户名查询用户数据
     * @param username
     * @return LibraryStaff对象
     */
    LibraryStaff findByUsername(@Param("username") String username);

    /**
     * 根据用户id查staff用户
     * @param sid
     * @return
     */
    LibraryStaff findBySid(@Param("sid") Integer sid);

    /**
     * 修改Patron信息
     * @param staff
     * @return 受影响行数
     */
    Integer updateInfoByUid(LibraryStaff staff);

    List<LibraryStaff> listAll();

    Integer deleteBySid(Integer sid);
}
