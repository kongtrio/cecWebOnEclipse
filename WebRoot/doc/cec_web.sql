--2015.1.25
drop database if exists cecweb 

create database cecWeb

use cecWeb
create table users(
    userId int primary key auto_increment,
    userName varchar(20) not null,
    nickName varchar(20) not null,
    password varchar(36) not null,
    u_level bool default 0,
    reg_time timestamp default now(),
    login_count int default 0,
    last_time timestamp,
    last_ip varchar(20),
    art_count int default 0,
    remark varchar(200)
)engine=innoDB default charset=utf8;

create table art_column(
    columnId int primary key auto_increment,
    col_name varchar(20) not null,
    col_level int default 0,
    art_count int default 0,
    parent_col_id int default 0,
    is_address bool default 0,
    out_address varchar(100),
    is_new_windows bool default 0,
    is_nav bool default 0,
    is_index  bool default 0,
    status tinyint default 1,
    constraint fk_parent_col_id foreign key(parent_col_id) references art_column(columnId)
)engine=innoDB default charset=utf8;

create table article(
    artId int primary key auto_increment,
    title varchar(200) not null,
    content longtext not null,
    is_address bool default 0,
    out_address varchar(100),
    title_pic varchar(100),
    columnId int,
    summary text,
    author varchar(20) not null,
    is_top bool default 0,
    is_colmun_top bool default 0,
    is_index_top bool default 0,
    public_time timestamp not null,
    read_count int default 0,
    status tinyint default 1,
    constraint fk_columnId Foreign Key(columnId) References art_column(columnId)
)engine=innoDB default charset=utf8;

create table friend_link(
     friend_linkId int primary key auto_increment,
     name varchar(50) not null,
     address varchar(100),
     pic varchar(100),
     summary varchar(500),
     is_index bool
)engine=innoDB default charset=utf8;

create table colmun_right(
     id int primary key auto_increment,
     columnId int,
     userId int,
     Foreign Key(userId) References users(userId),
     Foreign Key(columnId) References art_column(columnId)
)engine=innoDB default charset=utf8;


--2015年4月26号更新
alter table article add column is_mark tinyint default 0
--2015年5月12号更新,新的内容编辑框和旧的编辑框不兼容，解析html方式不同
alter table article add column is_new_tab int default 0
alter table article add column is_school int default 0

create table ip_control(
     id int primary key auto_increment,
     ip varchar(50) not null,
     remark varchar(500),
     type int DEFAULT 0
)engine=innoDB default charset=utf8;