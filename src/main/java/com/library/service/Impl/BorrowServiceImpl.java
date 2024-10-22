package com.library.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.mapper.BookMapper;
import com.library.mapper.BorrowMapper;
import com.library.mapper.PatronMapper;
import com.library.pojo.Book;
import com.library.pojo.BorrowRecord;
import com.library.pojo.Patron;
import com.library.service.BorrowService;
import com.library.service.exception.BookNotAvailException;
import com.library.service.exception.InsertException;
import com.library.service.exception.UpdateDataBaseException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.Provider;
import java.util.Date;

/**
 * @Author: zbq
 * @Date: 2023/4/13 下午1:58
 */
@Service
public class BorrowServiceImpl extends ServiceImpl<BorrowMapper, BorrowRecord> implements BorrowService {
    @Resource
    private BorrowMapper borrowMapper;
    @Resource
    private BookMapper bookMapper;
    @Resource
    private PatronMapper patronMapper;
    @Override
    public void borrowBook(Integer pid, Integer bid,Integer days) {
        Book book= bookMapper.selectOne(new QueryWrapper<Book>().eq("bid", bid));
        Patron patron = patronMapper.selectOne(new QueryWrapper<Patron>().eq("pid", pid));
        if(book.getAvailable()<=0){
            throw new BookNotAvailException("book not available");
        }
        Integer rows = bookMapper.updateAvailByBid(bid,  - 1);
        if(rows!=1){
            throw new InsertException("database insert exception");
        }
        BorrowRecord record = new BorrowRecord();
        Date date=new Date();
        Long ms=date.getTime();
        record.setUid(pid)
                .setBid(bid)
                .setBookname(book.getName())
                .setUsername(patron.getUsername())
                .setBorrowTime(new Date())
                .setDeadline(new Date(ms+days*24*60*60*1000L))
                .setCreateTime(date)
                .setUpdateTime(date);
        int i = borrowMapper.insert(record);
        if(i!=1){
            throw new InsertException("database insert exception");
        }
    }

    @Override
    public void returnBook(Integer id, Integer bid) {
        Integer row1 = borrowMapper.updateFlagById(id);
        if(row1!=1){
            throw new InsertException("database insert exception");
        }
        Integer row2 = bookMapper.updateAvailByBid(bid, 1);
        if(row2!=1){
            throw new InsertException("database insert exception");
        }
    }
    public void returnBook(Integer id) {
        Integer row1 = borrowMapper.updateFlagById(id);
        if(row1!=1){
            throw new InsertException("database insert exception");
        }
        Integer bid=borrowMapper.getBid(id);
        Integer row2 = bookMapper.updateAvailByBid(bid, 1);
        if(row2!=1){
            throw new InsertException("database insert exception");
        }
    }

    @Override
    public void renewBook(Integer id, Integer days) {
        Integer row = borrowMapper.renewById(id, days);
        if(row!=1) throw new UpdateDataBaseException("update database exception");
    }
}
