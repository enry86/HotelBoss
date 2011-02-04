-- SQL script for HotelBoss -- 

create schema if not exists `hotelboss`;
use `hotelboss`;


create table if not exists `users` (
`PrincipalID` varchar(50) not null,
`Password` text not null,
`Role` varchar(30) not null,
`name` varchar(30) not null,
`surname` varchar(30) not null,
`email` varchar(100) not null,
primary key (`PrincipalID`));



create table if not exists `Price` (
`price_id` integer not null,
`start_d` date not null,
`end_d` date not null,
`fb` numeric(10, 2) not null,
`hb` numeric(10, 2) not null,
`bb` numeric(10, 2) not null,
primary key(`price_id`));


create table if not exists `rooms` (
`id` integer not null, 
`capacity` integer not null,
`note` text,
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






