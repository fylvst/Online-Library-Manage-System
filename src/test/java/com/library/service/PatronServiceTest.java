package com.library.service;

import com.library.pojo.Patron;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author: zbq
 * @Date: 2023/4/9 下午9:45
 */
@SpringBootTest
public class PatronServiceTest {
    @Resource
    private PatronService patronService;
//    @Test
//    public void testReg(){
//        Patron patron = new Patron();
//        patron.setUsername("abc")
//                .setPassword("abc");
//        patronService.register(patron);
//    }
//    @Test
//    public void testLogin(){
//        Patron login = patronService.login("abc", "abc");
//        System.out.println(login);
//        System.out.println(login.getUsername());
//    }
//    @Test
//    public void testGetByPid(){
//        System.out.println(patronService.getByPid(1));
//    }
//
//    @Test
//    public void testUpdateInfo(){
//        Patron patron=new Patron();
//        patron.setNickname("sanni")
//                        .setAge(16)
//                                .setPhone("13619185732")
//                                        .setEmail("100@qq.com");
//        patronService.updateInfo(1,patron);
//    }
}
