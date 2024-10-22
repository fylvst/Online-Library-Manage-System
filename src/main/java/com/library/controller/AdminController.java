package com.library.controller;

import com.library.pojo.Administrator;
import com.library.pojo.LibraryStaff;
import com.library.pojo.Patron;
import com.library.service.AdminService;
import com.library.service.PatronService;
import com.library.service.StaffService;
import com.library.service.exception.InsertException;
import com.library.service.exception.UserNotFoundException;
import com.library.service.exception.UsernameDuplicateException;
import com.library.service.exception.WrongPasswdException;
import com.library.utils.JsonResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zbq
 * @Date: 2023/3/23 下午8:12
 */
@RestController
@CrossOrigin
@RequestMapping("admin")
public class AdminController {
    @Resource
    AdminService adminService;
    @Resource
    PatronService patronService;
    @Resource
    StaffService staffService;
    @RequestMapping("reg")
    public JsonResult<Void> reg(Administrator admin){
        JsonResult<Void> result=new JsonResult<>();
        try{
            adminService.register(admin);
            result.setCode(200);
            result.setMessage("register successfully");
        }catch (UsernameDuplicateException e){
            result.setCode(2000);
            result.setMessage("username already exist");
        }catch (InsertException e){
            result.setCode(3000);
            result.setMessage("database server insert exception");
        }
        return result;
    }

    @RequestMapping("login")
    public JsonResult<Administrator> login(String username, String password){
        JsonResult<Administrator> result=new JsonResult<>();
        try{
            Administrator admin = adminService.login(username, password);
            result.setCode(200)
                    .setMessage("login successfully")
                    .setData(admin);
//            session.setAttribute("pid",patron.getPid());
//            session.setAttribute("username",patron.getUsername());
        }catch (UserNotFoundException | WrongPasswdException e){
            result.setCode(2000)
                    .setMessage("username or password is wrong");
        }
        return result;
    }

    @RequestMapping("listPatron")
    public JsonResult<List<Patron>> listPatron(){
        return new JsonResult<>(200,"success",patronService.listAll());
    }

    @RequestMapping("delPatron")
    public JsonResult<Void> delPatron(Integer pid){
        Integer row = patronService.deleteByPid(pid);
        if(row==1){
            return new JsonResult<>(200,"success",null);
        }else{
            return new JsonResult<>(300,"fail",null);
        }
    }

    @RequestMapping("listStaff")
    public JsonResult<List<LibraryStaff>> listStaff(){
        return new JsonResult<>(200,"success",staffService.listAll());
    }

    @RequestMapping("delStaff")
    public JsonResult<Void> delStaff(Integer sid){
        Integer row = staffService.deleteBySid(sid);
        if(row==1){
            return new JsonResult<>(200,"success",null);
        }else{
            return new JsonResult<>(300,"fail",null);
        }
    }
}
