package com.library.mapper;

import com.library.pojo.LibraryStaff;
import com.library.pojo.Patron;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: zbq
 * @Date: 2023/4/10 上午11:03
 */
@SpringBootTest
public class StaffMapperTest {
    @Resource
    private LibraryStaffMapper staffMapper;
    @Test
    public void testInsert(){
        LibraryStaff staff=new LibraryStaff();
        staff.setUsername("zbq")
                .setPassword("123").setNickname("pissenlit")
                .setCreateTime(new Date())
                .setUpdateTime(new Date());
        Integer rows = staffMapper.insert(staff);
        System.out.println(rows);
    }
    @Test
    public void testFindByUsername(){
        LibraryStaff staff = staffMapper.findByUsername("zbq");
        System.out.println(staff);
    }
    @Test
    public void testFindByUid(){
        System.out.println(staffMapper.findBySid(1));
    }
    @Test
    public void testUpdateInfoByUid(){
        LibraryStaff staff=new LibraryStaff();
        staff.setSid(1)
                .setNickname("alex")
                .setAge(12)
                .setUpdateTime(new Date());
        Integer integer = staffMapper.updateInfoByUid(staff);
        System.out.println(integer);
    }
}
