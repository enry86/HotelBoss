-- SQL script for HotelBoss -- 

create schema if not exists `hotelboss`;
use `hotelboss`;


create table if not exists `Principals` (
`PrincipalID` text not null,
`Password` text not null);


create table if not exists `Roles` (
`PrincipalID` text not null,
`Role` text not null);

create table if not exists `users` (
`PrincipalID` varchar(20) not null,
`name` varchar(30) not null,
`surname` varchar(30) not null,
primary key (`PrincipalID`));


create table if not exists `Price` (
`price_id` integer not null,
`start_d` date not null,
`end_d` date not null,
`fb` numeric(5, 2) not null,
`hb` numeric(5, 2) not null,
`bb` numeric(5, 2) not null,
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

