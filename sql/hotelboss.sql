-- SQL script for HotelBoss -- 

create schema if not exists `hotelboss`;
use `hotelboss`;


create table if not exists `users` (
`username` varchar(50) not null,
`passwd` text not null,
`role` varchar(30) not null,
`name` varchar(30) not null,
`surname` varchar(30) not null,
`email` varchar(100) not null,
primary key (`username`));



create table if not exists `Price` (
`price_id` integer not null,
`start_d` date not null,
`end_d` date not null,
`fb` numeric(10, 2) not null,
`hb` numeric(10, 2) not null,
`bb` numeric(10, 2) not null,
primary key(`price_id`));


create table if not exists `purchase` (
`id` integer not null auto_increment,
`room` integer not null,
`date` date not null,
`qty` integer not null,
`extra_id` integer not null,
primary key (`id`));


create table if not exists `extras` (
`id` integer not null auto_increment,
`name` varchar(50) not null,
`price` numeric(6, 2) not null, 
primary key (`id`));


create table if not exists `reductions` (
`id` integer not null auto_increment,
`descr` text not null,
`val` numeric (10, 2) not null,
`red_type` integer not null,
`perc` boolean not null,
primary key (`id`));


create table if not exists `reservation` (
`id` integer not null auto_increment,
`date_arr` date not null, 
`date_dep` date not null,
`room` varchar(10) not null, 
`customer` varchar(100) not null,
`note` text,
primary key (`id`));


create table if not exists `customer` (
`room` integer not null auto_increment,
`date_arr` date not null,
`name` varchar(100) not null, 
`people` integer not null,
`treatment` integer not null,
`discount` integer,
primary key (`room`));

insert into users values ('admin', 'admin', 'admin','Administrator','Administrator','admin@hotelboss.it');




