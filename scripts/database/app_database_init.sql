create database amticadb;

use amticadb;
/*ip of application server on network*/
create user 'amtica_user'@'172.31.86.143' identified by '@mT!cA';
/*ip of application server on network*/
grant select, insert, delete, update on amticadb.* to 'amtica_user'@'172.31.86.143';

create table user (
id int(11) not null auto_increment,
email_id varchar(100) not null,
phone_number varchar(15) not null,
secret_hash varchar(500) not null,
first_name varchar(100),
last_name varchar(100),
registered_on timestamp not null,
status boolean default true,
/*permanent_id varchar(200) not null,*/
primary key user_pk (id)
);