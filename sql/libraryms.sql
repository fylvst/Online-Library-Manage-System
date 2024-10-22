create table patron(
                       pid int primary key auto_increment comment '用户主键',
                       username varchar(32) not null unique comment '登录用户名',
                       password varchar(64) not null comment '登录密码',
                       nickname varchar(32) default null comment '昵称',
                       age int default null comment '年龄',
                       phone varchar(20) default null comment '电话号码',
                       email varchar(64) default null comment '邮箱',
                       is_delete int default 0 comment '逻辑删除，默认0,删除为1',
                       create_time datetime default null comment '创建时间',
                       update_time datetime default null comment '修改时间'
);

create table staff(
                      sid int primary key auto_increment comment '职工主键',
                      username varchar(32) not null unique comment '登录用户名',
                      password varchar(64) not null comment '登录密码',
                      nickname varchar(32) default null comment '昵称',
                      age int default null comment '年龄',
                      phone varchar(20) default null comment '电话号码',
                      email varchar(64) default null comment '邮箱',
                      is_delete int default 0 comment '逻辑删除，默认0,删除为1',
                      create_time datetime default null comment '创建时间',
                      update_time datetime default null comment '修改时间'
);

create table administrator(
                              aid int primary key auto_increment comment '管理员主键',
                              username varchar(32) not null unique comment '登录用户名',
                              password varchar(64) not null comment '登录密码',
                              nickname varchar(32) default null comment '昵称',
                              is_delete int default 0 comment '逻辑删除，默认0,删除为1',
                              create_time datetime default null comment '创建时间',
                              update_time datetime default null comment '修改时间'
);

create table book(
                     bid int primary key auto_increment comment '图书主键',
                     name varchar(255) not null comment '书名',
                     author varchar(64) not null comment '作者名',
                     cid int not null comment '分类id',
                     cate_name varchar(64) not null comment '分类名',
                     nums int not null comment '馆藏数目',
                     available int not null  comment '可借数量',
                     description text default null comment '简述',
                     isbn varchar(16) default null comment 'isbn码',
                     publisher varchar(64) default null comment '出版社',
                     is_delete int default 0 comment '逻辑删除，默认0,删除为1',
                     create_time datetime default null comment '创建时间',
                     update_time datetime default null comment '修改时间'
);

create table category(
                         cid int primary key auto_increment comment '分类主键',
                         cate_name varchar(64) not null comment '分类名',
                         is_delete int default 0 comment '逻辑删除，默认0,删除为1',
                         create_time datetime default null comment '创建时间',
                         update_time datetime default null comment '修改时间'
);

create table borrow_record(
                              id int primary key auto_increment comment '借书记录id',
                              uid int not null comment '用户id',
                              username varchar(32) not null comment '用户昵称',
                              bid int not null comment '图书id',
                              bookname varchar(255) not null comment '图书名',
                              borrow_time datetime not null comment '借书时间',
                              flag int default 0 comment '是否归还，0未归还，1已归还',
                              deadline datetime not null comment '还书截止时间',
                              fee numeric(10,2) default 0 comment '逾期待支付金额',
                              is_delete int default 0 comment '逻辑删除，默认0,删除为1',
                              create_time datetime default null comment '创建时间',
                              update_time datetime default null comment '修改时间'
);

create table return_record(
                              id int primary key auto_increment comment '还书记录id',
                              uid int not null comment '用户id',
                              username varchar(32) not null comment '用户昵称',
                              bid int not null comment '图书id',
                              bookname varchar(255) not null comment '图书名',
                              return_time datetime not null comment '借书时间',
                              fee numeric(10,2) default 0 comment '支付金额',
                              is_delete int default 0 comment '逻辑删除，默认0,删除为1',
                              create_time datetime default null comment '创建时间',
                              update_time datetime default null comment '修改时间'
);

# 创建定时任务
create procedure update_fee()
BEGIN
    update borrow_record
    set fee =if(datediff(current_timestamp,deadline)>0,1.99+datediff(current_timestamp,deadline),1.99)
    where flag=0;
end;

create Event updatefee
    on schedule EVERY 1 DAY
        STARTS current_timestamp
    do call update_fee()