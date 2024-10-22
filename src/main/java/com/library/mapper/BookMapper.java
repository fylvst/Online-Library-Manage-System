package com.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.library.pojo.Book;
import org.springframework.stereotype.Repository;


/**
 * @Author: zbq
 * @Date: 2023/4/11 下午9:00
 */
@Repository
public interface BookMapper extends BaseMapper<Book> {
    //Integer getAvailByBid(Integer bid);

    Integer updateAvailByBid(Integer bid,Integer value);

    Integer getBookNums();
}
