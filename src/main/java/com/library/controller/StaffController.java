package com.library.controller;

import com.library.pojo.LibraryStaff;
import com.library.pojo.Patron;
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
import javax.servlet.http.HttpSession;

/**
 * @Author: zbq
 * @Date: 2023/4/10 上午11:27
 */
@RestController
@CrossOrigin
@RequestMapping("staff")
public class StaffController {
    @Resource
    private StaffService staffService;

    @RequestMapping("reg")
    public JsonResult<Void> reg(LibraryStaff staff){
        JsonResult<Void> result=new JsonResult<>();
        try{
            staffService.register(staff);
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
    public JsonResult<LibraryStaff> login(String username, String password){
        JsonResult<LibraryStaff> result=new JsonResult<>();
        try{
            LibraryStaff staff = staffService.login(username, password);
            result.setCode(200)
                    .setMessage("login successfully")
                    .setData(staff);
//            session.setAttribute("sid",staff.getSid());
//            session.setAttribute("username",staff.getUsername());
        }catch (UserNotFoundException | WrongPasswdException e){
            result.setCode(2000)
                    .setMessage("username or password is wrong");
        }
        return result;
    }

    @RequestMapping("getBySid")
    public JsonResult<LibraryStaff> getByPid(Integer sid){
        JsonResult<LibraryStaff> result=new JsonResult<>();
        //Integer pid = Integer.valueOf(session.getAttribute("sid").toString());
        LibraryStaff staff = staffService.getBySid(sid);
        result.setCode(200)
                .setMessage("success")
                .setData(staff);
        return result;
    }

    @RequestMapping("updateInfo")
    public JsonResult<Void> updateInfo(LibraryStaff staff){
        JsonResult<Void> result=new JsonResult<>();
        Integer sid = staff.getSid();
        try{
            staffService.updateInfo(sid,staff);
            result.setCode(200)
                    .setMessage("update successfully");
        }catch (InsertException e){
            result.setCode(3000);
            result.setMessage("database server insert exception");
        }
        return result;
    }
}
