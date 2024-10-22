package com.library.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class BorrowRecord extends Base{
    //借阅记录
    @TableId(type = IdType.AUTO)
    private Integer id;
    //用户id
    private Integer uid;
    //用户名
    private String username;
    // 图书id
    private Integer bid;
    //图书名
    private String bookname;
    //借书时间
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date borrowTime;
    //标记是否归还 0 为未归还  1为归还
    private Integer flag;
    //还书截止时间
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date deadline;
    //逾期付费金额
    private Double fee;

}
