drop table IF EXISTS Customer_accounts;
drop table IF EXISTS Customer;
drop table IF EXISTS Account;
create table Customer(
customer_id int PRIMARY KEY AUTO_INCREMENT,
first_name varchar(20),
last_name varchar(20),
email varchar(100)
);
create table Account(
account_number int PRIMARY KEY AUTO_INCREMENT,
account_type varchar(20),
balance double
);
create table Customer_accounts(
customer_id int,
account_id int,
FOREIGN KEY (account_id)REFERENCES account(account_number),
FOREIGN KEY (customer_id)REFERENCES customer(customer_id)
);
