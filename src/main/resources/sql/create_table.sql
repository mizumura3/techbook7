
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
    album_id int(11) not null comment 'アルバムID',
    name varchar(50) not null comment '名前',
    primary key (id)
) comment '曲';

create table albums
(
    id   int(11) unsigned auto_increment comment 'id',
    name varchar(50) not null comment '名前',
    website varchar(200) not null comment 'website',
    primary key (id)
) comment 'アルバム';