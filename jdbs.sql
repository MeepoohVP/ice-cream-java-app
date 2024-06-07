create table DBcustomer(
	queue char(10)
);
create table DBorderdetail(
	menu char(20),
    price int,
    orderCode char(4)
);
create table DBorder (
	code char(20),
    ownerid char(20),
    status char(20)
);

insert into DBorder (status) values
	('in progress'),
    ('success'),
    ('wait payment'),
    ('cancel');


insert into DBorderdetail (menu,price) values 
	('banana split','99'),
    ('strawberry sundae','169'),
    ('rocky road','129'),
    ('cookie and cream cone','69'),
    ('vanilla cone','69');