package com.library;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootTest
class LibraryMsApplicationTests {
    @Test
    public void test() throws ParseException {
        //long time=1261629276L*1000;
        long time=1283958918L*1000;
        System.out.println(longToDate(time));
    }

    public static Date longToDate(long lo) throws ParseException {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //long转Date
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sd.format(new Date(lo)));
        return date;
    }

    @Test
    void contextLoads() {
    }

}
