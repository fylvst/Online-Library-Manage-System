package com.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.library.pojo.Attention;
import com.library.pojo.Book;
import com.library.service.AttentionService;
import com.library.service.BookService;
import com.library.service.exception.InsertException;
import com.library.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("attention")
public class AttentionController {

    @Autowired
    private AttentionService attentionService;

    @Autowired
    private BookService bookService;

    @PostMapping("add")
    public JsonResult<Void> addAttention(Attention attention){
        JsonResult<Void> result = new JsonResult<>();
        try {
            attentionService.addAttention(attention);
            result.setCode(200).setMessage("success");
        }catch (InsertException e){
            result.setCode(300).setMessage("database insert exception");
        }
        return result;
    }

    @GetMapping("list")
    public JsonResult<List<Book>> getBookList(Integer pid){
        JsonResult<List<Book>> result = new JsonResult<>();

        QueryWrapper<Attention> qw = new QueryWrapper<>();
        qw.eq("pid",pid);
        List<Integer> bids = attentionService.list(qw)
                .stream()
                .map(Attention::getBid)
                .collect(Collectors.toList());

        List<Book> bookList = bids.stream()
                .map((item) -> {
                    QueryWrapper<Book> qw2 = new QueryWrapper<>();
                    qw2.eq("bid", item)
                            .eq("is_delete", 0);
                    return bookService.list(qw2).get(0);
                })
                .collect(Collectors.toList());
        if (bookList != null && bookList.size() != 0) {
            result.setCode(200)
                    .setMessage("success")
                    .setData(bookList);
        } else {
            result.setCode(300)
                    .setMessage("no interests")
                    .setData(null);
        }
        return result;
    }
}
