package com.library.pojo;

import lombok.*;
import lombok.experimental.Accessors;

//创建一个Book类继承Base基类
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Book extends Base{
    //图书id
    private Integer bid;
    //图书名称
    private String name;
    //作者
    private String author;
    //分类id
    private Integer cid;
    //分类名称
    private String cateName;
    //该图书馆藏数量
    private Integer nums;
    //可借阅数量
    private Integer available;
    //图书简介
    private String description;
    //isbn码
    private String isbn;
    //出版社
    private String publisher;
    //位置
    private String location;
}
