package com.library.service;

import com.library.pojo.Administrator;

/**
 * @Author: zbq
 * @Date: 2023/3/23 下午8:11
 */
public interface AdminService {
    void register(Administrator admin);

    Administrator login(String username,String password);

    Administrator getByAid(Integer aid);
}
