# create database mymusic;

use mymusic;

-- 登录用户表
create table MUSIC_USER(
                           USER_ID varchar(14) primary key not null ,
                           USER_NAME varchar(20) null ,
                           USER_LOGINNAME varchar(20),
                           USER_PASSWORD varchar(50),
                           USER_ADMIN int not null comment '是否是管理员:1普通用户，2管理员',
                           SINGER_DATE timestamp default now() comment '注册日期'
);

-- 歌手表
create table MUSIC_SINGER(
                             SINGER_ID varchar(14) primary key not null ,
                             SINGER_NAME varchar(20) not null ,
                             SINGER_AGE int not null ,
                             SINGER_SEX int not null,
                             SINGER_BIRTHDAY timestamp ,
                             SINGER_NATION varchar(400) default null comment '简介/备注',
                             SINGER_HEAD varchar(30) default null comment '头像地址',
                             SINGER_DATE timestamp default now()
);

-- 歌曲表
create table MUSIC_SONG (
                            SONG_ID varchar(14) primary key not null ,
                            SINGER_ID varchar(14) not null,
                            SINGER_REMOVE int null comment '删除标志：1正常，2删除',
                            foreign key (SINGER_ID) references MUSIC_SINGER(SINGER_ID)
);


ALTER TABLE MUSIC_SONG
    ADD COLUMN SONG_ALBUM varchar(30) AFTER SINGER_ID;

alter table MUSIC_SINGER change SINGER_NATION SINGER_REMAKE varchar(400) null comment '简介/备注';


alter table MUSIC_SINGER
    add SINGER_NATION varchar(20) null comment '故乡' after SINGER_BIRTHDAY;

alter table MUSIC_SINGER modify SINGER_REMAKE varchar(400) null comment '简介/备注' after SINGER_HEAD;


alter table MUSIC_SONG change SINGER_REMOVE SONG_REMOVE int null comment '删除标志：1正常，2删除';

-- 歌单表
create table MUSIC_LIST
(
    LIST_ID varchar(14) primary key not null,
    LIST_NAME varchar(30) not null ,
    LIST_CREATEDATE timestamp default now()
);


alter table MUSIC_SONG comment '歌曲表';

-- 用户歌单关联关系表（用于用户收藏，创建歌单所用）
create table MUSIC_USER_LIST(
                                UL_ID varchar(14) primary key not null ,
                                USER_ID varchar(14) not null ,
                                LIST_ID varchar(14) not null ,
                                foreign key (USER_ID) references MUSIC_USER(USER_ID) on DELETE CASCADE,
                                foreign key (LIST_ID) references MUSIC_LIST(LIST_ID) on DELETE CASCADE
) comment '用户歌单关联表';


-- 歌曲歌单关联关系表
create table MUSIC_SONG_LIST(
                                SL_ID varchar(14) primary key not null ,
                                SONG_ID varchar(14) not null ,
                                LIST_ID varchar(14) not null ,
                                foreign key (SONG_ID) references MUSIC_SONG(SONG_ID) on DELETE CASCADE,
                                foreign key (LIST_ID) references MUSIC_LIST(LIST_ID) on DELETE CASCADE
) comment '歌曲歌单关联表';

-- 文件上传表
create table MUSIC_FILE(
                           FILE_ID varchar(14) primary key ,
                           FILE_NAME varchar(30) null comment '文件名称',
                           FILE_TYPE int not null comment '文件类型',
                           FILE_URL varchar(30) not null comment '文件地址',
                           FILE_DATE timestamp default now() comment '上传时间'
) comment '系统文件保存信息';


alter table MUSIC_LIST
    add LIST_REMARK VARCHAR(400) null ;


-- 歌曲和歌手关联关系表
create table MUSIC_SINGER_SONG(
                                  SS_ID varchar(14) primary key ,
                                  SINGER_ID varchar(14) not null ,
                                  SONG_ID varchar(14) not null ,
                                  foreign key  (SINGER_ID) references MUSIC_SINGER (SINGER_ID) on delete  cascade ,
                                  foreign key  (SONG_ID) references MUSIC_SONG (SONG_ID) on delete  cascade

) comment '歌手歌曲关联表';


alter table MUSIC_USER drop column USER_ADMIN;

-- 创建管理员表
create table manager_user as (select * from MUSIC_USER);

alter table MUSIC_USER
    add USER_HEAD varchar(30) null after USER_PASSWORD;


alter table MUSIC_USER add constraint foreign key (USER_HEAD) references MUSIC_FILE (FILE_ID);

alter table MUSIC_FILE modify FILE_URL varchar(100) not null comment '文件地址';

alter table music_singer    add SINGER_ALIGNS varchar(30) null comment '别名' after SINGER_NAME;

alter table music_singer modify SINGER_SEX int not null after SINGER_NATION;

alter table music_singer modify SINGER_HEAD varchar(100) null comment '头像地址' after SINGER_DATE;

alter table music_singer modify SINGER_NAME varchar(50) not null;

alter table music_singer modify SINGER_ALIGNS varchar(50) null comment '别名';


create table MUSIC_CONTENT
(
    CONTENT_ID varchar(15) not null,
    ZUOQU varchar(100) null,
    ZUOCI varchar(100) null,
    BIANQU varchar(50) null,
    ZHIZUOREN varchar(50) null,
    GECI varchar(8000) null,
    JIETA varchar(50) null,
    XIANYUE varchar(50) null,
    HESHENG  varchar(50) null,
    HUNYINGGONGCHENGSHI varchar(50) null,
    YIRENTONGCHOU varchar(50) null,
    LUYINGPENG varchar(50) null,
    TUIGUANGTONGCHOU varchar(500) null,
    TEBIEMINGXIE varchar(500) null,
    constraint MUSIC_CONTENT_pk
        primary key (CONTENT_ID),
    constraint MUSIC_CONTENT_music_song_SONG_ID_fk
        foreign key (CONTENT_ID) references music_song (SONG_ID)
);


alter table music_content modify GECI varchar(10000) null;

alter table music_content
    add Time long null after CONTENT_ID;

alter table music_content modify BIANQU varchar(50) null after ZHIZUOREN;

alter table music_content modify JITA varchar(50) null after XIANYUE;

alter table music_content modify HESHENG varchar(50) null after HUNYINGGONGCHENGSHI;

alter table music_content modify LUYINGPENG varchar(50) null after TEBIEMINGXIE;

alter table music_content change Time TIME mediumtext null;
alter table music_content change JIETA JITA varchar(50) null;


alter table music_content comment '存放歌曲内容 （歌词，时长，作曲，作词...）';














