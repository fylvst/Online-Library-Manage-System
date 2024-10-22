package com.library.service.Impl;

import com.library.mapper.PatronMapper;
import com.library.pojo.Patron;
import com.library.service.PatronService;
import com.library.service.exception.InsertException;
import com.library.service.exception.UserNotFoundException;
import com.library.service.exception.UsernameDuplicateException;
import com.library.service.exception.WrongPasswdException;
import com.library.utils.Sha256Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: zbq
 * @Date: 2023/4/9 下午9:16
 */
@Service
public class PatronServiceImpl implements PatronService {
    @Resource
    private PatronMapper patronMapper;
    @Override
    public void register(Patron patron) {
        String username=patron.getUsername();
        Patron result = patronMapper.findByUsername(username);
        if(result!=null){
            throw new UsernameDuplicateException("username "+username+" already exists!");
        }
        Date now=new Date();
        //将明文密码用Sha256算法加密保存
        String passwd= Sha256Util.getSHA256Str(patron.getPassword());
        patron.setPassword(passwd)
                .setNickname(username)
                .setCreateTime(now)
                .setUpdateTime(now);
        Integer rows=patronMapper.insertPatron(patron);
        if(rows!=1){
            throw new InsertException("database insert exception");
        }

    }

    @Override
    public Patron login(String username, String password) {
        Patron result = patronMapper.findByUsername(username);
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
        Patron patron=new Patron();
        patron.setPid(result.getPid())
                .setUsername(result.getUsername());
        return patron;

    }

    @Override
    public Patron getByPid(Integer pid) {
        Patron result = patronMapper.findByPid(pid);
        if(result==null){
            throw new UserNotFoundException("user not found");
        }
        if(result.getIsDelete()==1){
            throw new UserNotFoundException("user not found");
        }
        Patron patron=new Patron();
        patron.setUsername(result.getUsername())
                .setNickname(result.getNickname())
                .setAge(result.getAge())
                .setEmail(result.getEmail())
                .setPhone(result.getPhone());
        return patron;
    }

    @Override
    public void updateInfo(Integer pid, Patron patron) {
        Patron result = patronMapper.findByPid(pid);
        if(result==null){
            throw new UserNotFoundException("user not found");
        }
        if(result.getIsDelete()==1){
            throw new UserNotFoundException("user not found");
        }
        patron.setPid(result.getPid())
                .setUpdateTime(new Date());
        Integer rows = patronMapper.updateInfoByUid(patron);
        if(rows!=1){
            throw new InsertException("database insert exception");
        }
    }

    @Override
    public List<Patron> listAll() {
        return patronMapper.listAll();
    }

    @Override
    public Integer deleteByPid(Integer pid) {
        return patronMapper.deleteByPid(pid);
    }
}
