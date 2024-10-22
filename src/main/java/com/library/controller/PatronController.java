package com.library.controller;

import com.library.pojo.Patron;
import com.library.service.PatronService;
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
import java.sql.PreparedStatement;

/**
 * @Author: zbq
 * @Date: 2023/4/9 下午10:06
 */
@RestController
@RequestMapping("patron")
@CrossOrigin
public class PatronController {
    @Resource
    private PatronService patronService;

    @RequestMapping("reg")
    public JsonResult<Void> reg(Patron patron){
        JsonResult<Void> result=new JsonResult<>();
        try{
            patronService.register(patron);
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
    public JsonResult<Patron> login(String username, String password){
        JsonResult<Patron> result=new JsonResult<>();
        try{
            Patron patron = patronService.login(username, password);
            result.setCode(200)
                .setMessage("login successfully")
                .setData(patron);
//            session.setAttribute("pid",patron.getPid());
//            session.setAttribute("username",patron.getUsername());
        }catch (UserNotFoundException | WrongPasswdException e){
            result.setCode(2000)
                .setMessage("username or password is wrong");
        }
        return result;
    }

    @RequestMapping("getByPid")
    public JsonResult<Patron> getByPid(Integer pid){
        JsonResult<Patron> result=new JsonResult<>();
//        Integer pid = Integer.valueOf(session.getAttribute("pid").toString());
        Patron patron = patronService.getByPid(pid);
        result.setCode(200)
                .setMessage("success")
                .setData(patron);
        return result;
    }

    @RequestMapping("updateInfo")
    public JsonResult<Void> updateInfo(Patron patron){
        JsonResult<Void> result=new JsonResult<>();
//        Integer pid = Integer.valueOf(session.getAttribute("pid").toString());
        Integer pid=patron.getPid();
        try{
            patronService.updateInfo(pid,patron);
            result.setCode(200)
                    .setMessage("update successfully");

        }catch (InsertException e){
            result.setCode(3000);
            result.setMessage("database server insert exception");
        }
        return result;
    }
}
