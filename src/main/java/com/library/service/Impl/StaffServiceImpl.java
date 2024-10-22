package com.library.service.Impl;

import com.library.mapper.LibraryStaffMapper;
import com.library.pojo.LibraryStaff;
import com.library.pojo.Patron;
import com.library.service.StaffService;
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
 * @Date: 2023/4/10 上午11:18
 */
@Service
public class StaffServiceImpl implements StaffService {
    @Resource
    private LibraryStaffMapper mapper;

    @Override
    public void register(LibraryStaff staff) {
        String username=staff.getUsername();
        LibraryStaff result =mapper.findByUsername(username);
        if(result!=null){
            throw new UsernameDuplicateException("username "+username+" already exists!");
        }
        Date now=new Date();
        //将明文密码用Sha256算法加密保存
        String passwd= Sha256Util.getSHA256Str(staff.getPassword());
        staff.setPassword(passwd)
                .setNickname(username)
                .setCreateTime(now)
                .setUpdateTime(now);
        Integer rows=mapper.insert(staff);
        if(rows!=1){
            throw new InsertException("database insert exception");
        }
    }

    @Override
    public LibraryStaff login(String username, String password) {
        LibraryStaff result = mapper.findByUsername(username);
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
        LibraryStaff staff=new LibraryStaff();
        staff.setSid(result.getSid())
                .setUsername(result.getUsername());
        return staff;
    }

    @Override
    public LibraryStaff getBySid(Integer sid) {
        LibraryStaff result = mapper.findBySid(sid);
        if(result==null){
            throw new UserNotFoundException("user not found");
        }
        if(result.getIsDelete()==1){
            throw new UserNotFoundException("user not found");
        }
        LibraryStaff staff=new LibraryStaff();
        staff.setUsername(result.getUsername())
                .setNickname(result.getNickname())
                .setAge(result.getAge())
                .setEmail(result.getEmail())
                .setPhone(result.getPhone());
        return staff;
    }

    @Override
    public void updateInfo(Integer sid, LibraryStaff staff) {
        LibraryStaff result = mapper.findBySid(sid);
        if(result==null){
            throw new UserNotFoundException("user not found");
        }
        if(result.getIsDelete()==1){
            throw new UserNotFoundException("user not found");
        }
        staff.setSid(result.getSid())
                .setUpdateTime(new Date());
        Integer rows = mapper.updateInfoByUid(staff);
        if(rows!=1){
            throw new InsertException("database insert exception");
        }
    }

    @Override
    public List<LibraryStaff> listAll() {
        return mapper.listAll();
    }

    @Override
    public Integer deleteBySid(Integer sid) {
        return mapper.deleteBySid(sid);
    }
}
