create table company(
    company_id int primary key,
    name varchar(255) not null
);
create table model (
	model_id int primary key,
    model_name varchar(255) not null
);

create table year_of_manufacture(
	year_id int primary key,
    year int not null
);
create table Car(
    car_id int primary key,
    company_id int not null,
    model_id int not null,
    year_id int not null,
    foreign key (company_id) references company(company_id),
    foreign key (model_id) references model(model_id),
    foreign key (year_id) references year_of_manufacture(year_id)
);

create table city(
	city_id int primary key,
    city_name varchar(255) not null
);

create table Branch(
    branch_id int primary key,
    street varchar(255) not null,
    city_id int not null,
    building_number int not null,
    contact_number int not null,
    foreign key (city_id) references city(city_id)
);
create table car_branch(
    car_id int not null,
    branch_id int not null,
    no_of_cars int not null,
    PRIMARY KEY (car_id, branch_id),
    foreign key (car_id) references Car(car_id),
    foreign key (branch_id) references Branch(branch_id)
);
create table Car_Price_History( 
	car_price_id int primary key,
	car_id int not null,
    price float not null,
    price_date date not null,
    foreign key (car_id) references Car(car_id)
);
create table color(
    color_id int primary key,
    color_name varchar(255) not null   
);

create table car_color(
    car_id int not null,
    color_id int not null,
    primary key (car_id,color_id),
    foreign key (car_id) references Car(car_id),
    foreign key (color_id) references Color(color_id)
);

create table job(
    job_id int primary key,
    title varchar(255) not null
);
create table gender (
	gender_id int primary key,
    gender varchar(10) not null
    );
create table employee(
    SSN int primary key,
    fname varchar(255) not null,
    lname varchar(255) not null,
	street varchar(255) not null,
    city_id int not null,
    building_number int not null,
    phone_1 BIGINT not null,
    phone_2 BIGINT not null,
    gender_id int not null,
    birth_date date not null,
    job_id int not null,
    supervisor_id int not null,
    branch_id int not null,
    foreign key (job_id) references job(job_id),
    foreign key (supervisor_id) references employee(SSN),
    foreign key (branch_id) references Branch(branch_id),
    foreign key (city_id) references city(city_id),
    foreign key (gender_id) references gender(gender_id)
);

create table salary_history(
    job_id int not null,
    EMP_SSN int not null,
    salary float not null,
    comm_pct float null,
    date date not null,  
    primary key(job_id,EMP_SSN),
    foreign key (job_id) references job(job_id),
	foreign key (EMP_SSN) references employee(SSN)
);  



create table payment_method(
    method_id int primary key,
    method varchar(100) not null
);
create table customer(
    SSN int primary key,
    fname varchar(255) not null,
    lname varchar(255) not null,
    street varchar(255) not null,
    city_id int not null,
    building_number int not null,
    phone_1 bigint not null,
    phone_2 bigint not null,
    gender_id int not null,
    birthdate date not null,
    foreign key (city_id) references city(city_id),
    foreign key (gender_id) references gender(gender_id)
);

create table roles(
	role_id int primary key,
    role_name varchar(255) not null
    );
create table accounts (
	account_id int primary key auto_increment,
    username varchar(100) not null unique,
    email varchar(255)not null unique,
    password_hash varchar(255) not null, 
    role_id int not null,
    emp_ssn int ,
    cust_ssn int,
    created_at datetime default current_timestamp,
    foreign key (emp_ssn) references employee(SSN),
    foreign key (cust_ssn) references customer(SSN),
    foreign key (role_id) references roles(role_id)
    );
    
create table Contract(
    contract_id int primary key auto_increment,
    car_id int not null,
    branch_id int not null,
    EMP_SSN int not null,
    CUST_SSN int not null,
    account_id int not null,
    method_id  int not null,
    car_price_id int not null,
    contract_date date not null,
    foreign key (car_id) references car(car_id),
    foreign key (branch_id) references Branch(branch_id),
    foreign key (EMP_SSN) references employee(SSN),
    foreign key (CUST_SSN) references customer(SSN),
    foreign key (method_id) references payment_method(method_id),
    foreign key (car_price_id) references car_price_history(car_price_id),
    foreign key (account_id) references accounts(account_id)
);
    