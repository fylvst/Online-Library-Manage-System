package com.library.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.library.mapper.BookMapper;
import com.library.pojo.Book;
import com.library.service.BookService;
import com.library.service.exception.InsertException;
import com.library.utils.JsonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zbq
 * @Date: 2023/4/11 下午9:03
 */
@RestController
@RequestMapping("book")
@CrossOrigin
public class BookController {
    @Resource
    private BookService service;

    @RequestMapping("getBookByCid")
    public JsonResult<List<Book>> getBookByCid(Integer cid) {
        JsonResult<List<Book>> result = new JsonResult<>();
        QueryWrapper<Book> qw = new QueryWrapper<>();
        qw.select("bid", "name", "author", "cid", "cate_name", "nums", "available", "description", "isbn", "publisher","location")
                .eq("cid", cid)
                .eq("is_delete", 0);
        List<Book> books = service.list(qw);
        if (books != null && books.size() != 0) {
            result.setCode(200)
                    .setMessage("success")
                    .setData(books);
        } else {
            result.setCode(300)
                    .setMessage("no books or category not exist")
                    .setData(null);
        }
        return result;
    }


    @GetMapping("/list")
    public JsonResult<List<Book>> getBookList(@RequestParam(value = "text", required = false) String text,
                                          @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        JsonResult<List<Book>> result = new JsonResult<>();
        // 设置分页信息
        Page<Book> page = new Page<>(pageNum, 10);
        // 构建查询条件
        QueryWrapper<Book> query = new QueryWrapper<>();
        if (StringUtils.isNotBlank(text)) {
            // 模糊查询书名、作者、分类
            query.and(wrapper -> wrapper.like("name", text)
                    .or().like("author", text)
                    .or().like("cate_name", text))
                    .orderByDesc("bid");
        }
        // 执行查询
        List<Book> books = service.page(page, query).getRecords();
        // 封装返回结果
        if (books != null && books.size() != 0) {
            result.setCode(200)
                    .setMessage("success")
                    .setData(books);
        } else {
            result.setCode(300)
                    .setMessage("no books or category not exist")
                    .setData(null);
        }
        return result;
    }
    @RequestMapping("add")
    public JsonResult<Void> addBook(Book book){
        JsonResult<Void> result=new JsonResult<>();
        Date date = new Date();
        book.setCreateTime(date);
        book.setUpdateTime(date);
        try{
            service.addBook(book);
            result.setCode(200).setMessage("success");
        }catch (InsertException e){
            result.setCode(300).setMessage("database insert exception");
        }
        return result;
    }

    @RequestMapping("getBookByPage")
    public JsonResult<Map<String,Object>> getBookByPage(int size,int page){
        JsonResult<Map<String,Object>> result=new JsonResult<>();
        Map<String,Object> map=new HashMap<>();
        QueryWrapper<Book> qw=new QueryWrapper<>();
        Page<Book> pages=new Page<>(page,size);
        qw.select("bid", "name", "author", "cid", "cate_name", "nums", "available", "description", "isbn", "publisher","location")
                .orderByDesc("bid");
        List<Book> books = service.page(pages, qw).getRecords();
        map.put("book",books);
        if(page==1){
           map.put("nums",service.getBookNums());
           result.setCode(200).setMessage("success").setData(map);
        }else{
            result.setCode(200).setMessage("success").setData(map);
        }
        return result;
    }
}
