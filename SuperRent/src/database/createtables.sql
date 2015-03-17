

create table vehicletype
(typeName varchar(20) not null PRIMARY KEY,
w_rate float,
d_rate float,
h_rate float,
pk_rate float,
w_insurance float,
d_insurance float,
h_insurance float
)
ENGINE = InnoDB;



create table branch
(city varchar(20),
location varchar(20),
PRIMARY KEY(city,location)
)
ENGINE = InnoDB;




create table user
(username varchar(20) not null,
password varchar(20),
name varchar(20),
type varchar(20),
PRIMARY KEY(username)
)
ENGINE = InnoDB;




create table customer
(username varchar(20) not null,
phone varchar(20) unique,
address varchar(20),
isRoadStar boolean,
isClubMember boolean,
point float,
PRIMARY KEY(username),
index cust_ind (username),
FOREIGN KEY(username) REFERENCES user(username)
)
ENGINE = InnoDB;



create table clerk
(username varchar(20) not null,
city varchar(20),
location varchar(20),
PRIMARY KEY(username),
index user_ind (username),
FOREIGN KEY(username) REFERENCES user(username),
index branch_ind (city),
index location_ind (location),
FOREIGN KEY(city,location) REFERENCES branch(city,location)

)
ENGINE = InnoDB;




create table manager
(username varchar(20) not null,
PRIMARY KEY(username),
index user_ind (username),
FOREIGN KEY(username) REFERENCES user(username)
)
ENGINE = InnoDB;




create table admin
(username varchar(20) not null,
PRIMARY KEY(username),
index user_ind (username),
FOREIGN KEY(username) REFERENCES user(username)
)
ENGINE = InnoDB;




create table equipment
(equipName varchar(20) not null,
type varchar(20),
d_rate float,
h_rate float,
PRIMARY KEY(equipName)
)
ENGINE = InnoDB;



create table vehiclesold
(vid integer not null,
solddate date,
typeName varchar(20),
category varchar(20),
manager varchar(20),
PRIMARY KEY(vid),
index manager_ind (manager),
FOREIGN KEY(manager) REFERENCES manager(userName)
)
ENGINE = InnoDB;



create table vehicleforrent
(vid integer not null,
isAvailable boolean,
starting_date date,
category varchar(20),
vehicleType varchar(20),
manager varchar(20),
PRIMARY KEY(vid),
index vehicleType_ind (vehicleType),
FOREIGN KEY(vehicleType) REFERENCES vehicletype(typeName),
index manager_ind (manager),
FOREIGN KEY(manager) REFERENCES manager(userName)
)
ENGINE = InnoDB;




create table vehicleforsale
(vid integer not null,
price float,
starting_date date,
category varchar(20),
vehicleType varchar(20),
PRIMARY KEY(vid),
index vehicleType_ind (vehicleType),
FOREIGN KEY(vehicleType) REFERENCES vehicletype(typeName)
)
ENGINE = InnoDB;




create table vehicleinbranch
(vid integer not null,
city varchar(20),
location varchar(20),
PRIMARY KEY(vid),
index branch_ind (city),
index location_ind (location),
FOREIGN KEY(city,location) REFERENCES branch(city,location)
)
ENGINE = InnoDB;




create table keep_equipment
(equipName varchar(20) not null,
city varchar(20),
location varchar(20),
quantity integer,
PRIMARY KEY(equipName,city,location),
index equip_ind (equipName),
FOREIGN KEY(equipName) REFERENCES equipment(equipName),
index branch_ind (city),
index location_ind (location),
FOREIGN KEY(city,location) REFERENCES branch(city,location)
)
ENGINE = InnoDB;




create table reservation
(confirmation_number integer not null auto_increment,
pickup_date date,
pickup_time time,
return_date date,
return_time time,
estimation_cost float,
vid integer,
branch_city varchar(20),
branch_location varchar(20),
customer_username varchar(20),
equipment_type varchar(20),
no_of_equipment integer,
equip_name varchar(20),
PRIMARY KEY(confirmation_number),
index vehicle_ind (vid),
FOREIGN KEY(vid) REFERENCES vehicleinbranch(vid),
index branch_ind (branch_city),
index location_ind (branch_location),
FOREIGN KEY(branch_city,branch_location) REFERENCES branch(city,location),
index customer_ind (customer_username),
FOREIGN KEY(customer_username) REFERENCES customer(username),
index equip_ind (equip_name),
FOREIGN KEY(equip_name) REFERENCES equipment(equipName)
)
ENGINE = InnoDB;




create table rent
(rentid integer not null auto_increment,
is_reserve boolean,
driver_licence varchar(20),
rent_date date,
vid integer,
branch_city varchar(20),
branch_location varchar(20),
customer_username varchar(20),
equipment_type varchar(20),
no_of_equipment varchar(20),
equip_name varchar(20),
card_no varchar(40),
expiry_date date,
from_date date,
to_date date,
from_time time,
to_time time,
PRIMARY KEY(rentid),
index vehicle_ind (vid),
FOREIGN KEY(vid) REFERENCES vehicleinbranch(vid),
index branch_ind (branch_city),
index location_ind (branch_location),
FOREIGN KEY(branch_city,branch_location) REFERENCES branch(city,location),
index customer_ind (customer_username),
FOREIGN KEY(customer_username) REFERENCES customer(username),
index equip_ind (equip_name),
FOREIGN KEY(equip_name) REFERENCES equipment(equipName)
)
ENGINE = InnoDB;




create table vreturn
(returnid integer not null auto_increment,
rent_id integer,
return_date date,
return_time time,
vid integer,
branch_city varchar(20),
branch_location varchar(20),
customer_username varchar(20),
tank_full boolean,
od_reading float,
rental_charge float,
insurance_cost float,
additional_cost float,
total_cost float,
payment_method varchar(20),
PRIMARY KEY(returnid),
index vehicle_ind (vid),
FOREIGN KEY(vid) REFERENCES vehicleinbranch(vid),
index branch_ind (branch_city),
index location_ind (branch_location),
FOREIGN KEY(branch_city,branch_location) REFERENCES branch(city,location),
index customer_ind (customer_username),
FOREIGN KEY(customer_username) REFERENCES customer(username),
index rent_ind (rent_id),
FOREIGN KEY(rent_id) REFERENCES rent(rentid)
)
ENGINE = InnoDB;




create table rent_addon
(rentid integer not null,
equipName varchar(20) not null,
PRIMARY KEY(rentid,equipName),
index rent_ind (rentid),
FOREIGN KEY(rentid) REFERENCES rent(rentid),
index equip_ind (equipName),
FOREIGN KEY(equipName) REFERENCES equipment(equipName)
)
ENGINE = InnoDB;





create table reserve_addon
(confirmNo integer not null,
equipName varchar(20) not null,
PRIMARY KEY(confirmNo,equipName),
index reservation_ind (confirmNo),
FOREIGN KEY(confirmNo) REFERENCES reservation(confirmation_number),
index equip_ind (equipName),
FOREIGN KEY(equipName) REFERENCES equipment(equipName)
)
ENGINE = InnoDB;
