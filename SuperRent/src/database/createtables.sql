

create table vehicletype
(typeName varchar(20) not null PRIMARY KEY,
w_rate integer,
d_rate integer,
h_rate integer,
pk_rate integer,
w_insurance integer,
d_insurance integer,
h_insurance integer
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
point integer,
PRIMARY KEY(username),
index cust_ind (username),
FOREIGN KEY(username) REFERENCES user(username)
ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB;



create table clerk
(username varchar(20) not null,
branch_city varchar(20),
branch_location varchar(20),
PRIMARY KEY(username),
index user_ind (username),
FOREIGN KEY(username) REFERENCES user(username)
ON DELETE CASCADE ON UPDATE CASCADE,
index branch_ind (branch_city),
index location_ind (branch_location),
FOREIGN KEY(branch_city,branch_location) REFERENCES branch(city,location)
ON DELETE CASCADE ON UPDATE CASCADE

)
ENGINE = InnoDB;




create table manager
(username varchar(20) not null,
PRIMARY KEY(username),
index user_ind (username),
FOREIGN KEY(username) REFERENCES user(username)
ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB;




create table admin
(username varchar(20) not null,
PRIMARY KEY(username),
index user_ind (username),
FOREIGN KEY(username) REFERENCES user(username)
ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB;




create table equipment
(equipName varchar(20) not null,
type varchar(20),
d_rate integer,
h_rate integer,
PRIMARY KEY(equipName)
)
ENGINE = InnoDB;



create table vehiclesold
(vlicense varchar(10) not null,
solddate date,
typeName varchar(20),
category varchar(20),
brand varchar(20),
manager varchar(20),
odometer integer,
PRIMARY KEY(vlicense),
index manager_ind (manager),
FOREIGN KEY(manager) REFERENCES manager(userName)
ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB;



create table vehicleforrent
(vlicense varchar(10) not null,
isAvailable boolean,
starting_date date,
category varchar(20),
vehicleType varchar(20),
brand varchar(20),
manager varchar(20),
PRIMARY KEY(vlicense),
index vehicleType_ind (vehicleType),
odometer integer,
FOREIGN KEY(vehicleType) REFERENCES vehicletype(typeName)
ON DELETE CASCADE ON UPDATE CASCADE,
index manager_ind (manager),
FOREIGN KEY(manager) REFERENCES manager(userName)
ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB;




create table vehicleforsale
(vlicense varchar(10) not null,
price integer,
starting_date date,
category varchar(20),
brand varchar(20),
vehicleType varchar(20),
PRIMARY KEY(vlicense),
odometer integer,
index vehicleType_ind (vehicleType),
FOREIGN KEY(vehicleType) REFERENCES vehicletype(typeName)
ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB;




create table vehicleinbranch
(vlicense varchar(10) not null,
city varchar(20),
location varchar(20),
PRIMARY KEY(vlicense),
index branch_ind (city),
index location_ind (location),
FOREIGN KEY(city,location) REFERENCES branch(city,location)
ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB;




create table keep_equipment
(equipName varchar(20) not null,
city varchar(20),
location varchar(20),
quantity integer,
PRIMARY KEY(equipName,city,location),
index equip_ind (equipName),
FOREIGN KEY(equipName) REFERENCES equipment(equipName)
ON DELETE CASCADE ON UPDATE CASCADE,
index branch_ind (city),
index location_ind (location),
FOREIGN KEY(city,location) REFERENCES branch(city,location)
ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB;




create table reservation
(confirmation_number varchar(20) not null,
pickup_date date,
pickup_time integer,
return_date date,
return_time integer,
estimation_cost integer,
vlicense varchar(10),
branch_city varchar(20),
branch_location varchar(20),
customer_username varchar(20),
equipment_type varchar(20),
no_of_equipment integer,
equip_name varchar(20),
PRIMARY KEY(confirmation_number),
index vehicle_ind (vlicense),
FOREIGN KEY(vlicense) REFERENCES vehicleinbranch(vlicense)
ON DELETE CASCADE ON UPDATE CASCADE,
index branch_ind (branch_city),
index location_ind (branch_location),
FOREIGN KEY(branch_city,branch_location) REFERENCES branch(city,location)
ON DELETE CASCADE ON UPDATE CASCADE,
index customer_ind (customer_username),
FOREIGN KEY(customer_username) REFERENCES customer(username)
ON DELETE CASCADE ON UPDATE CASCADE,
index equip_ind (equip_name),
FOREIGN KEY(equip_name) REFERENCES equipment(equipName)
ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB;




create table rent
(rentid integer not null auto_increment,
is_reserve boolean,
driver_license varchar(20),
vlicense varchar(10),
branch_city varchar(20),
branch_location varchar(20),
customer_username varchar(20),
equipment_type varchar(20),
no_of_equipment varchar(20),
equip_name varchar(20),
card_no varchar(40),
expiry_date date,
from_date date,
from_time integer,
PRIMARY KEY(rentid),
index vehicle_ind (vlicense),
FOREIGN KEY(vlicense) REFERENCES vehicleinbranch(vlicense)
ON DELETE CASCADE ON UPDATE CASCADE,
index branch_ind (branch_city),
index location_ind (branch_location),
FOREIGN KEY(branch_city,branch_location) REFERENCES branch(city,location)
ON DELETE CASCADE ON UPDATE CASCADE,
index customer_ind (customer_username),
FOREIGN KEY(customer_username) REFERENCES customer(username)
ON DELETE CASCADE ON UPDATE CASCADE,
index equip_ind (equip_name),
FOREIGN KEY(equip_name) REFERENCES equipment(equipName)
ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB;




create table vreturn
(returnid integer not null auto_increment,
rent_id integer,
return_date date,
return_time integer,
vlicense varchar(10),
branch_city varchar(20),
branch_location varchar(20),
customer_username varchar(20),
tank_full boolean,
odometer int,
rental_charge integer,
insurance_cost integer,
additional_cost integer,
total_cost integer,
payment_method varchar(20),
from_date date,
from_time integer,
is_reserve boolean,
driver_license varchar(20),
card_no varchar(40),
expiry_date date,
PRIMARY KEY(returnid),
index vehicle_ind (vlicense),
FOREIGN KEY(vlicense) REFERENCES vehicleinbranch(vlicense)
ON DELETE CASCADE ON UPDATE CASCADE,
index branch_ind (branch_city),
index location_ind (branch_location),
FOREIGN KEY(branch_city,branch_location) REFERENCES branch(city,location)
ON DELETE CASCADE ON UPDATE CASCADE,
index customer_ind (customer_username),
FOREIGN KEY(customer_username) REFERENCES customer(username)
ON DELETE CASCADE ON UPDATE CASCADE,
index rent_ind (rent_id),
FOREIGN KEY(rent_id) REFERENCES rent(rentid)
ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB;




create table rent_addon
(rentid integer not null,
equipName varchar(20) not null,
PRIMARY KEY(rentid,equipName),
index rent_ind (rentid),
FOREIGN KEY(rentid) REFERENCES rent(rentid)
ON DELETE CASCADE ON UPDATE CASCADE,
index equip_ind (equipName),
FOREIGN KEY(equipName) REFERENCES equipment(equipName)
ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB;





create table reserve_addon
(confirmNo varchar(20) not null,
equipName varchar(20) not null,
PRIMARY KEY(confirmNo,equipName),
index reservation_ind (confirmNo),
FOREIGN KEY(confirmNo) REFERENCES reservation(confirmation_number)
ON DELETE CASCADE ON UPDATE CASCADE,
index equip_ind (equipName),
FOREIGN KEY(equipName) REFERENCES equipment(equipName)
ON DELETE CASCADE ON UPDATE CASCADE
)
ENGINE = InnoDB;