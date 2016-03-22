
-- -------------------------------[ TICKET MANAGEMENT SYSTEM ]----------------------------------------

CREATE DATABASE IF NOT EXISTS ticketmanagementsystem;

USE ticketmanagementsystem;

-- ------------------------------------< Creating Table >---------------------------------------------

-- employee_info Table
CREATE TABLE employee_info (
     employee_id  INT(3) NOT NULL AUTO_INCREMENT PRIMARY KEY,
     password VARCHAR(8) NOT NULL,
     employee_type ENUM('Administrator', 'Employee') NOT NULL,
     employee_name VARCHAR(40) NOT NULL,
     telephone_no VARCHAR(14) NOT NULL,
     address VARCHAR(40) NOT NULL,
     email VARCHAR(40) NOT NULL
) ENGINE = INNODB;

-- customer_info Table
CREATE TABLE customer_info (
    ticket_id INT(3) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(40) NOT NULL,
    telephone_no VARCHAR(14) NOT NULL,
    address VARCHAR(80) NOT NULL,
    purchase_location VARCHAR(20) NOT NULL,
    seat_no varchar(80) NOT NULL,
    destination VARCHAR(20) NOT NULL,
    purchase_time DATETIME NOT NULL,
    departure_time DATETIME NOT NULL,
	cost INT NOT NULL
) ENGINE = INNODB;

-- ------------------------------------< Inserting Data into Table >----------------------------------

-- for employe_info Table

INSERT INTO employee_info (password, employee_type,  employee_name, telephone_no, address, email) VALUES ('12345', 'Administrator', 'Saad Shaiket','0171696969', '278/4, South Kafrul, Dhaka', 'p_valochele@gmail.com');

INSERT INTO employee_info (password, employee_type,  employee_name, telephone_no, address, email) VALUES ('asdfg', 'Employee', 'Manik Hasan', '0171456789','27/1, Mirpur 14, Dhaka', 'manik_hasan@hotmail.com');

INSERT INTO employee_info (password, employee_type,  employee_name, telephone_no, address, email) VALUES ('zxcvb', 'Employee', 'Herok Roy', '0171420420','32A, Badda, Dhaka', 'valo_mon@live.com');

INSERT INTO employee_info (password, employee_type,  employee_name, telephone_no, address, email) VALUES ('abcde', 'Employee', 'Shamim Sarker', '0168192045','233, Moghbazar, Dhaka','ajebaje_1982@yahoo.com');

INSERT INTO employee_info (password, employee_type,  employee_name, telephone_no, address, email) VALUES ('vwxyz', 'Employee', 'Shuvo Chowdhury', '0182980635','27, Khilkhet, Dhaka', 'shuvo_pagla@yahoo.com');

-- ---------------------------------------------------------------------------------------------------

-- for customer_info Table
INSERT INTO customer_info (customer_name, telephone_no, address, purchase_location, seat_no, destination, purchase_time, departure_time, cost) VALUES ('Molla Umar', '0171420420', 'Mofiz Uddin Road, House 14, Dhaka', 'Chittagong', 'A1-4, B1-2', 'Dhaka', '2011-08-11 04:08:59', '2011-08-14 08:10:00', 100);

INSERT INTO customer_info (customer_name, telephone_no, address, purchase_location, seat_no, destination, purchase_time, departure_time, cost) VALUES ('Bangla Vai', '0191234567', 'Classified', 'Feni', 'B1-2', 'Kumilla', '2011-08-11 04:08:59', '2011-08-14 05:10:00', 200);

INSERT INTO customer_info (customer_name, telephone_no, address, purchase_location, seat_no, destination, purchase_time, departure_time, cost) VALUES ('Osama Bin Laden', '0182456789', 'Monir Road, Chittagong', 'Dhaka', 'D1-4', 'Bramonbaria', '2011-08-11 05:08:59', '2011-08-14 07:10:00', 300);

INSERT INTO customer_info (customer_name, telephone_no, address, purchase_location, seat_no, destination, purchase_time, departure_time, cost) VALUES ('Kala Zahangir', '0167898765', 'Taltola, House 14, Feni', 'Feni', 'C4', 'Chittagong', '2011-08-11 06:08:59', '2011-08-14 08:10:00', 400);

INSERT INTO customer_info (customer_name, telephone_no, address, purchase_location, seat_no, destination, purchase_time, departure_time, cost) VALUES ('Ershad Shikder', '0134561234', 'Kala Mofiz Road, Dhaka', 'Chittagong', 'E3', 'Kumilla', '2011-08-11 09:08:59', '2011-08-14 10:10:00', 500);

-- ----------------------------------------------------------------------------------------------------

