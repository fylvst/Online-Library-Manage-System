package com.library.service;

import com.library.pojo.LibraryStaff;
import com.library.pojo.Patron;

import java.util.List;

/**
 * @Author: zbq
 * @Date: 2023/4/10 上午11:16
 */
public interface StaffService {
    /**
     * 注册
     * @param staff
     */
    void register(LibraryStaff staff);

    /**
     * 登录
     * @param username
     * @param password
     * @return Staff对象
     */
    LibraryStaff login(String username,String password);

    /**
     * 通过sid获取个人信息
     * @param sid
     * @return Staff对象
     */
    LibraryStaff getBySid(Integer sid);

    /**
     * 更新个人信息
     * @param sid
     * @param staff
     */
    void updateInfo(Integer sid,LibraryStaff staff);

    List<LibraryStaff> listAll();

    Integer deleteBySid(Integer sid);
}
