package com.library.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.library.pojo.Book;
import com.library.utils.JsonResult;

/**
 * @Author: zbq
 * @Date: 2023/4/13 下午1:25
 */
public interface BookService extends IService<Book> {

    void addBook(Book book);

    Integer getBookNums();
}
