
create table artists
(
    id   int(11) unsigned auto_increment comment 'id',
    name varchar(50) not null comment '名前',
    birth date not null comment '生年月日',
    website varchar(200) default null comment 'ウェブサイト',
    primary key (id)
) comment 'アーティスト';

create table musics
(
    id   int(11) unsigned auto_increment comment 'id',
    artist_id int(11) not null comment 'アーティストID',
    name varchar(50) not null comment '名前',
    primary key (id)
) comment '曲';
