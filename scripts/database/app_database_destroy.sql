truncate user;
drop table user;
/*ip of application server on network*/
revoke all from 'amtica_user'@'172.31.86.143';
/*ip of application server on network*/
drop user 'amtica_user'@'172.31.86.143';
drop database amticadb;