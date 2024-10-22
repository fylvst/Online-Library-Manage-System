package com.library.service.Impl;

import com.library.mapper.AdminMapper;
import com.library.pojo.Administrator;
import com.library.pojo.Patron;
import com.library.service.AdminService;
import com.library.service.exception.InsertException;
import com.library.service.exception.UserNotFoundException;
import com.library.service.exception.UsernameDuplicateException;
import com.library.service.exception.WrongPasswdException;
import com.library.utils.Sha256Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: zbq
 * @Date: 2023/3/23 下午8:11
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    AdminMapper mapper;
    @Override
    public void register(Administrator admin) {
        String username=admin.getUsername();
        Administrator result = mapper.findByUsername(username);
        if(result!=null){
            throw new UsernameDuplicateException("username "+username+" already exists!");
        }
        Date now=new Date();
        //将明文密码用Sha256算法加密保存
        String passwd= Sha256Util.getSHA256Str(admin.getPassword());
        admin.setPassword(passwd)
                .setNickname(username)
                .setCreateTime(now)
                .setUpdateTime(now);
        Integer rows = mapper.insertAdmin(admin);
        if(rows!=1){
            throw new InsertException("database insert exception");
        }
    }

    @Override
    public Administrator login(String username, String password) {
        Administrator result = mapper.findByUsername(username);
        if(result==null){
            throw new UserNotFoundException("user not found");
        }
        if(result.getIsDelete()==1){
            throw new UserNotFoundException("user not found");
        }
        //将传递过来的密码进行加密，与数据库中暗文比对，相同则登录成功
        String passwd=Sha256Util.getSHA256Str(password);
        if(!passwd.equals(result.getPassword())){
            throw new WrongPasswdException("wrong password");
        }
        Administrator admin=new Administrator();
        admin.setAid(result.getAid())
                .setUsername(result.getUsername());
        return admin;
    }

    @Override
    public Administrator getByAid(Integer aid) {
        return null;
    }
}
