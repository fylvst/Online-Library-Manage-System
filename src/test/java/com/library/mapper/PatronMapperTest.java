package com.library.mapper;

import com.library.pojo.Patron;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: zbq
 * @Date: 2023/4/9 下午8:55
 */
@SpringBootTest
public class PatronMapperTest {

//    @Resource
//    private PatronMapper mapper;
//
//    @Test
//    public void testInsert(){
//        Patron patron = new Patron();
//        patron.setUsername("zbq")
//                .setPassword("123")
//                .setNickname("pissenlit")
//                .setCreateTime(new Date())
//                .setUpdateTime(new Date());
//        Integer line = mapper.insert(patron);
//        System.out.println(line);
//    }
//    @Test
//    public void testFindByUsername(){
//        Patron parton = mapper.findByUsername("zbq");
//        System.out.println(parton);
//    }
//
//    @Test
//    public void testFindByUid(){
//        Patron byPid = mapper.findByPid(1);
//        System.out.println(byPid);
//    }
//
//    @Test
//    public void testUpdateInfoByUid(){
//        Patron patron=new Patron();
//        patron.setPid(1)
//                .setNickname("alex")
//                .setAge(12)
//                .setUpdateTime(new Date());
//        Integer integer = mapper.updateInfoByUid(patron);
//        System.out.println(integer);
//    }
}
