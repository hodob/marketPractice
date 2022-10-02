-- show databases;
-- create database marketPractice;




use marketPractice;
drop table user;
show tables;
CREATE TABLE IF NOT EXISTS `user` (
`user_num` int auto_increment primary key,
  `user_id` varchar(20) ,
  `user_pw` varchar(20) 
) ;
select * from user;

drop table test ;
CREATE TABLE IF NOT EXISTS `test` (
  `id` varchar(20) ,
  `pw` varchar(20) 
) ;
select * from test;