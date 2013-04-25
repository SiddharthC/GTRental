/**
Creates a database and the various tables used by the program.
*/

CREATE DATABASE dbproject;

 use dbproject;


CREATE TABLE Login (
    username varchar(15) NOT NULL,
    user_passwd varchar(20) NOT NULL,
    user_type varchar(10) NOT NULL,
    PRIMARY KEY (username),
	UNIQUE (username, user_type)
);


CREATE TABLE Credit_card (
    card_number bigint(16) NOT NULL,
    name_on_card varchar(60) NOT NULL,
    cvv int(7) NOT NULL,
    expiry_date date NOT NULL,
    billing_address varchar(200) NOT NULL,
    PRIMARY KEY (card_number)
);


CREATE TABLE Driving_plan (
    plan_type varchar(10) NOT NULL,
    discount float(4 , 2 ),
    montly_payment float(6 , 2 ) NOT NULL,
    annual_payment float(6 , 2 ) NOT NULL,
    PRIMARY KEY (plan_type)
);


CREATE TABLE Location (
    loc_name varchar(20) NOT NULL,
    capacity int(10) NOT NULL,
    loc_address varchar(200),
    PRIMARY KEY (loc_name),
    UNIQUE (loc_address)
);


CREATE TABLE Car (
    reg_no varchar(20) NOT NULL,
    aux_cable varchar(5),
    transm_type varchar(10) NOT NULL,
    capacity int(2) NOT NULL,
    b_tooth varchar(5),
    daily_rate float(6 , 2 ) NOT NULL,
    hourly_rate float(6 , 2 ) NOT NULL,
    color varchar(10),
    car_type varchar(10) NOT NULL,
    model varchar(20) NOT NULL,
    mflag int(1) DEFAULT 0,
    loc_name varchar(20) NOT NULL,
    PRIMARY KEY (reg_no),
    FOREIGN KEY (loc_name)
        REFERENCES LOCATION (loc_name)
);


CREATE TABLE Member (
    username varchar(15) NOT NULL,
    first_name varchar(20) NOT NULL,
    middle_name varchar(20),
    last_name varchar(20) NOT NULL,
    email varchar(50) NOT NULL,
    address varchar(200),
    phone bigint(10),
    card_number bigint(16) NOT NULL,
    plan_type varchar(10) NOT NULL,
    PRIMARY KEY (username),
    FOREIGN KEY (username)
        REFERENCES Login (username),
    FOREIGN KEY (card_number)
        REFERENCES Credit_card (card_number)
        ON UPDATE CASCADE,
    FOREIGN KEY (plan_type)
        REFERENCES Driving_plan (plan_type)
);


CREATE TABLE Admin_Employee (
    username varchar(15) NOT NULL,
    user_type varchar(10) NOT NULL,
    PRIMARY KEY (username),
    CONSTRAINT username_with_type FOREIGN KEY (username, user_type)
        REFERENCES Login (username, user_type)
);


CREATE TABLE Maintenance_req (
    req_timestamp datetime NOT NULL,
    car_no varchar(20) NOT NULL,
    req_username varchar(15) NOT NULL,
    description varchar(200) NOT NULL,
    PRIMARY KEY (req_timestamp , car_no , description),
    UNIQUE (req_timestamp , req_username , description),
    UNIQUE (req_timestamp , car_no , req_username),
    FOREIGN KEY (car_no)
        REFERENCES Car (reg_no),
    FOREIGN KEY (req_username)
        REFERENCES Admin_Employee (username)
);


CREATE TABLE Reservation (
    req_timestamp datetime NOT NULL,
    regID int NOT NULL auto_increment,
    mem_username varchar(15) NOT NULL,
    car_no varchar(20) NOT NULL,
    loc_name varchar(20) NOT NULL,
    pickup_date date NOT NULL,
    pickup_time time NOT NULL,
    return_date date NOT NULL,
    return_time time NOT NULL,
    late_fee float(6 , 2 ) DEFAULT 0,
    return_flag int(1) DEFAULT 0,
    cancel_flag int(1) DEFAULT 0,
    late_by int DEFAULT 0,
    estimated_cost float(6 , 2 ),
    PRIMARY KEY (regID),
    CONSTRAINT un_ptime_pdate UNIQUE (mem_username , pickup_date , pickup_time),
    CONSTRAINT un_rtime_rdate UNIQUE (mem_username , return_date , return_time),
    CONSTRAINT car_ptime_pdate UNIQUE (car_no , pickup_date , pickup_time),
    CONSTRAINT car_rtime_rdate UNIQUE (car_no , return_date , return_time),
    FOREIGN KEY (mem_username)
        REFERENCES Member (username),
    FOREIGN KEY (car_no)
        REFERENCES Car (reg_no),
    FOREIGN KEY (loc_name)
        REFERENCES LOCATION (loc_name)
);


CREATE TABLE Extended_time (
    regID int NOT NULL,
    ext_date date NOT NULL,
    ext_time time NOT NULL,
    FOREIGN KEY (regID)
        REFERENCES Reservation (regID)
);

CREATE VIEW Maintenance_History_Count 
AS SELECT count(*) as Count, m.car_no
FROM 
    maintenance_req AS M,
    car AS C
WHERE
    C.reg_no = M.car_no 
GROUP BY M.car_no;


 COMMIT;