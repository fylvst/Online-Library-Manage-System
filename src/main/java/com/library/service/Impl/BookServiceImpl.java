package com.library.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.mapper.BookMapper;
import com.library.pojo.Book;
import com.library.service.BookService;
import com.library.service.exception.InsertException;
import com.library.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zbq
 * @Date: 2023/4/13 下午1:51
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private BookMapper bookMapper;
    @Override
    public void addBook(Book book) {
        int insert = bookMapper.insert(book);
        if (insert !=1 ){
            throw new InsertException("insert exception");
        }
    }

    @Override
    public Integer getBookNums() {
        return bookMapper.getBookNums();
    }
}
