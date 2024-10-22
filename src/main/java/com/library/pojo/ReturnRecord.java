package com.library.pojo;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: zbq
 * @Date: 2023/4/9 下午5:08
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ReturnRecord extends Base{
    //还书id
    private Integer id;
    //用户id
    private Integer uid;
    //用户名
    private String username;
    //图书id
    private Integer bid;
    //图书名
    private String bookname;
    //还书时间
    private Date returnTime;
    //支付金额
    private Double fee;

}
