package com.library.service;

import com.library.pojo.Patron;

import java.util.List;

/**
 * @Author: zbq
 * @Date: 2023/4/9 下午9:13
 */
public interface PatronService {
    /**
     * 注册
     * @param patron
     */
    void register(Patron patron);

    /**
     * 登录
     * @param username
     * @param password
     * @return Patron对象
     */
    Patron login(String username,String password);

    /**
     * 根据pid获取用户信息
     * @param pid
     * @return
     */
    Patron getByPid(Integer pid);

    /**
     * 修改用户信息
     * @param pid
     * @param patron
     */
    void updateInfo(Integer pid,Patron patron);

    List<Patron> listAll();

    Integer deleteByPid(Integer pid);
}
