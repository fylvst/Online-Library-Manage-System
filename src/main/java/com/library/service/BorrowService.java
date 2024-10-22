package com.library.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.library.pojo.BorrowRecord;

/**
 * @Author: zbq
 * @Date: 2023/4/13 下午1:24
 */
public interface BorrowService extends IService<BorrowRecord> {
    void borrowBook(Integer pid,Integer bid,Integer days);

    void returnBook(Integer id,Integer bid);
    void returnBook(Integer id);

    void renewBook(Integer id,Integer days);
}
