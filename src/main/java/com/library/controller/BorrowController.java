package com.library.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.library.pojo.BorrowRecord;
import com.library.service.BorrowService;
import com.library.service.exception.BookNotAvailException;
import com.library.service.exception.InsertException;
import com.library.service.exception.UpdateDataBaseException;
import com.library.utils.JsonResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: zbq
 * @Date: 2023/4/13 下午1:59
 */
@RestController
@CrossOrigin
public class BorrowController {
    @Resource
    private BorrowService borrowService;
    @RequestMapping("borrowBook")
    public JsonResult<Void> borrowBook(Integer pid,Integer bid,Integer days){
        JsonResult<Void> result=new JsonResult<>();
        try{
            borrowService.borrowBook(pid,bid,days);
            result.setCode(200)
                    .setMessage("borrow success");
        }catch (BookNotAvailException e){
            result.setCode(300)
                    .setMessage("book not available");
        }catch (InsertException e){
            result.setCode(3000)
                    .setMessage("database server insert exception");
        }
        return result;
    }
    @RequestMapping("getRecord")
    public JsonResult<List<BorrowRecord>> getRecord(Integer pid){
        JsonResult<List<BorrowRecord>>  result=new JsonResult<>();
        QueryWrapper<BorrowRecord> qw=new QueryWrapper<>();
        qw.select("id","bid","bookname","borrow_time","deadline","flag","fee")
                .eq("uid",pid)
                .eq("is_delete",0)
                .orderByDesc("id");
        List<BorrowRecord> list = borrowService.list(qw);
        result.setCode(200)
                .setMessage("success")
                .setData(list);
        return result;
    }
    @Deprecated
    @RequestMapping("returnBook")
    public JsonResult<Void> returnBook(Integer id,Integer bid){
        JsonResult<Void> result=new JsonResult<>();
        try{
            borrowService.returnBook(id,bid);
            result.setCode(200).setMessage("success");
        }catch (InsertException e){
            result.setCode(300).setMessage("fail");
        }
        return result;
    }

    @RequestMapping("renew")
    public JsonResult<Void> renewBook(Integer id,Integer days){
        try{
            borrowService.renewBook(id,days);
            return  new JsonResult<>(200,"success",null);
        }catch (UpdateDataBaseException e){
            return new JsonResult<>(500,"fail",null);
        }
    }
}
