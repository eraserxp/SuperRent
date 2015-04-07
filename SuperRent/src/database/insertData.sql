INSERT INTO vehicletype 
VALUES 
('boxtrucks',70000, 10000, 2000, 200, 28000, 1000, 200,40),
('cargovans',85000, 12500, 2500, 200, 28000, 1000, 200,40),
('compact',40000, 5500, 1100, 200, 28000, 1000, 200,40),
('economy',35000, 5000, 1000, 200, 28000, 1000, 200,40),
('foot12',55000, 8000, 1500, 200, 28000, 1000, 200,40),
('foot15',60000, 8500, 1600, 200, 28000, 1000, 200,40),
('foot24',60000, 8000, 1700, 200, 28000, 1000, 200,40),
('full-size',49000, 7000, 1400, 200, 28000, 1000, 200,40),
('luxury',320000, 80000, 16000, 200, 28000, 1000, 200,40),
('midsize',35000, 5000, 1200, 200, 28000, 1000, 200,40),
('premium',50000, 7500, 1500, 200, 28000, 1000, 200,40),
('standard',50000, 6500, 1300, 200, 28000, 1000, 200,40),
('suv',63000, 9000, 1800, 200, 28000, 1000, 200,40),
('van',70000, 10000, 2000, 200, 28000, 1000, 200,40);


INSERT INTO branch
VALUES 
('Vancouver','2660 Wesbrook Mall'),
('Toronto','300 Regina Street');


INSERT INTO user
VALUES 
('frank1','12345','frank1','manager'),
('manager','manager','frank1','manager'),
('frank2','12345','frank2','clerk'),
('clerk','clerk','frank2','clerk'),
('frank3','12345','frank3','clerk'),
('frank4','12345','frank4','clerk'),
('frank5','12345','frank5','clerk'),
('frank6','12345','frank6','admin'),
('admin','admin','frank6','admin'),
('frank7','12345','frank7','customer'),
('customer','customer','frank7','customer'),
('frank8','12345','frank8','customer'),
('frank9','12345','frank9','customer'),
('frank10','12345','frank10','customer');

INSERT INTO customer
VALUES 
('frank7','519-781-6707','94 Mcdonald Road',1,0,0,0,NULL),
('frank8','519-781-6708','94 Mcdonald Road',0,0,0,0,NULL),
('frank9','519-781-6709','94 Mcdonald Road',0,1,500,1,'2014-12-04'),
('customer','519-781-6787','94 Mcdonald Road',0,1,1000,0,'2014-02-04'),
('frank10','519-781-6700','94 Mcdonald Road',1,1,2300,0,'2014-03-04');

INSERT INTO clerk
VALUES 
('frank2','Vancouver','2660 Wesbrook Mall'),
('frank3','Vancouver','2660 Wesbrook Mall'),
('frank4','Toronto','300 Regina Street'),
('clerk','Toronto','300 Regina Street'),
('frank5','Toronto','300 Regina Street');

INSERT INTO manager
VALUES 
('manager'),
('frank1');

INSERT INTO admin
VALUES 
('admin'),
('frank6');

INSERT INTO equipment
VALUES 
('ski_rack','car',1000,200),
('child_safety_seat','car',1000,200),
('lift_gate','truck',1000,200),
('car_towing','truck',1000,200);

INSERT INTO vehiclesold
VALUES 
('PLT-4UP',5000000,'2014-02-02','economy','car','BMW','frank1',30000),
('PLT-4U1',6000000,'2014-02-03','economy','car','BMW','frank1',60000);


INSERT INTO vehicleforrent 
VALUES
('PLT-4D', 0,'1999-5-1','car', 'economy','Honda','frank1',3000),
('PLT-4M', 0,'2000-5-1','car', 'economy','BMW','frank1',30000),
('PLT-4T', 1,'2000-5-1','car', 'economy','BMW','frank1',30000),
('PLT-4C', 1,'2006-5-1','car', 'economy','BMW','frank1',30000),
('HOW DOO', 1,'2007-5-1','car', 'economy','BMW','frank1',60000),
('GM', 0,'2008-5-1','car', 'economy','BMW','frank1',60000),
('OUR BC', 1,'2009-5-1','car', 'economy','BMW','frank1',60000),
('4U 2DVY', 1,'2010-5-1','truck', 'boxtrucks','BMW','frank1',60000),
('4U 2AVY', 1,'1999-5-1','truck', 'foot24','BMW','frank1',6000),
('4U 2NRY', 1,'2000-5-1','truck', 'foot15','BMW','frank1',30000),
('4U 2NCY', 1,'2006-5-1','truck', 'foot12','BMW','frank1',30000),
('4U 2NYY', 1,'2008-5-1', 'truck','cargovans','BMW','frank1',30000),
('OUR CC', 1,'2009-5-1','truck', 'foot15','BMW','frank1',30000),
('OUR AC', 1,'2010-5-1','car', 'compact','BMW','frank1',30000),
('OUR DC', 1,'1999-5-1','car', 'compact','BMW','frank1',30000),
('OUR XC', 1,'2000-5-1','car', 'full-size','BMW','frank1',30000),
('OUR VC', 1,'2013-5-1','car', 'full-size','BMW','frank1',60000),
('OUR EC', 1,'1977-5-1','car', 'luxury','BMW','frank1',60000),
('IGOAAR', 1,'1977-5-1','car', 'luxury','BMW','frank1',60000),
('IGOBAR', 1,'2010-5-1','car', 'luxury','BMW','frank1',60000),
('IGOCAR', 1,'1998-5-1','car', 'midsize','BMW','frank1',60000),
('IGOFAR', 1,'1999-5-1','car', 'midsize','BMW','frank1',60000),
('IGODAR', 1,'2000-5-1','car', 'premium','BMW','frank1',60000),
('IGOEAR', 1,'2001-5-1','car', 'premium','BMW','frank1',60000),
('IGOGAR', 1,'1997-5-1','car', 'standard','BMW','frank1',120000),
('IGOHAR', 1,'1998-5-1', 'car','standard','BMW','frank1',120000),
('IGOIAR', 1,'2006-5-1','car', 'suv','BMW','frank1',120000),
('IGOJAR', 1,'2007-5-1', 'car','suv','BMW','frank1',120000),
('IGOKAR', 1,'2008-5-1', 'car','suv','BMW','frank1',120000),
('IGOLAR', 1,'2009-5-1','car', 'van','BMW','frank1',30000),
('IGOMAR', 1,'2010-5-1', 'car','van','BMW','frank1',30000),
('IGONAR', 1,'1999-5-1', 'car','van','BMW','frank1',30000),
('IGOOAR', 1,'2000-5-1','truck', 'foot12','Ford FSeries','frank1',30000),
('IGOPAR', 1,'2013-5-1','truck', 'foot15','Ford FSeries','frank1',30000),
('GOOGLE', 0,'1999-5-1','truck', 'foot24','Ford FSeries','frank1',30000),
('YAH-OO', 1,'2000-5-1','truck', 'foot12','Ford FSeries','frank1',30000),
('AMAZON', 1,'2006-5-1','truck', 'foot15','Ford FSeries','frank1',30000),
('BMAZON', 1,'2007-5-1','truck', 'foot24','Ford FSeries','frank1',30000),
('CMAZON', 0,'2008-5-1','truck', 'boxtrucks','Ford FSeries','frank1',30000),
('DMAZON', 1,'2009-5-1','truck', 'boxtrucks','Ford FSeries','frank1',30000),
('EMAZON', 1,'2010-5-1','truck', 'boxtrucks','Ford FSeries','frank1',30000),
('FMAZON', 1,'1999-5-1','truck', 'cargovans','Dodge Ram','frank1',30000),
('GMAZON', 1,'2000-5-1','truck', 'cargovans','Dodge Ram','frank1',30000),
('TMAZON', 1,'2013-5-1','truck', 'cargovans','Dodge Ram','frank1',30000),
('QMAZON', 0,'1999-5-1','car', 'economy','BMW','frank1',3000);

INSERT INTO vehicleforsale 
VALUES
('BCPL1S', 300000,'2000-5-1','car','BMW', 'economy',30000),
('BCPL2S', 300000,'2006-5-1','car','BMW', 'economy',30000),
('BCPL3S', 300000,'2007-5-1','car','BMW', 'economy',30000),
('BCPL4S', 300000,'2008-5-1','car','BMW', 'compact',30000),
('BCPL5S', 300000,'2009-5-1','car','BMW', 'compact',60000),
('BCPL6S', 300000,'2010-5-1','car','BMW', 'compact',60000),
('BCPL7S', 300000,'1999-5-1','car','BMW','full-size',60000),
('BCPL8S', 300000,'2000-5-1','car','BMW', 'full-size',60000),
('BCPL9S', 700000,'2007-5-1','car','BMW', 'luxury',60000),
('BCPL0S', 300000,'2008-5-1','car','BMW', 'luxury',60000),
('ACPL1S', 300000,'2009-5-1','car','BMW', 'luxury',60000),
('ACPL2S', 300000,'2010-5-1','car','BMW', 'midsize',60000),
('ACPL3S', 300000,'1999-5-1','truck','Dodge Ram', 'foot15',90000),
('ACPL4S', 300000,'2000-5-1','truck','Dodge Ram', 'foot24',90000),
('ACPL5S', 300000,'2007-5-1','truck','Dodge Ram', 'foot12',90000),
('ACPL6S', 300000,'2008-5-1','truck','Dodge Ram', 'foot15',90000),
('ACPL7S', 300000,'2009-5-1','truck','Dodge Ram', 'foot24',90000),
('ACPL8S', 300000,'2009-5-2','truck','BMW', 'boxtrucks',90000);

INSERT INTO vehicleinbranch 
VALUES
('PLT-4D', 'Vancouver','2660 Wesbrook Mall'),
('PLT-4M', 'Vancouver','2660 Wesbrook Mall'),
('PLT-4T','Vancouver','2660 Wesbrook Mall'),
('PLT-4C','Vancouver','2660 Wesbrook Mall'),
('HOW DOO','Vancouver','2660 Wesbrook Mall'),
('GM','Vancouver','2660 Wesbrook Mall'),
('OUR BC', 'Vancouver','2660 Wesbrook Mall'),
('4U 2DVY', 'Vancouver','2660 Wesbrook Mall'),
('4U 2AVY','Vancouver','2660 Wesbrook Mall'),
('4U 2NRY','Vancouver','2660 Wesbrook Mall'),
('4U 2NCY','Vancouver','2660 Wesbrook Mall'),
('4U 2NYY', 'Vancouver','2660 Wesbrook Mall'),
('OUR CC', 'Vancouver','2660 Wesbrook Mall'),
('OUR AC','Vancouver','2660 Wesbrook Mall'),
('OUR DC', 'Vancouver','2660 Wesbrook Mall'),
('OUR XC', 'Vancouver','2660 Wesbrook Mall'),
('OUR VC', 'Vancouver','2660 Wesbrook Mall'),
('OUR EC', 'Vancouver','2660 Wesbrook Mall'),
('IGOAAR','Vancouver','2660 Wesbrook Mall' ),
('IGOBAR','Vancouver','2660 Wesbrook Mall'),
('IGOCAR', 'Vancouver','2660 Wesbrook Mall'),
('IGOFAR','Vancouver','2660 Wesbrook Mall'),
('IGODAR','Vancouver','2660 Wesbrook Mall'),
('IGOEAR', 'Vancouver','2660 Wesbrook Mall'),
('IGOGAR', 'Vancouver','2660 Wesbrook Mall'),
('IGOHAR','Vancouver','2660 Wesbrook Mall'),
('IGOIAR','Vancouver','2660 Wesbrook Mall' ),
('IGOJAR', 'Toronto','300 Regina Street'),
('IGOKAR', 'Toronto','300 Regina Street'),
('IGOLAR', 'Toronto','300 Regina Street'),
('IGOMAR', 'Toronto','300 Regina Street'),
('IGONAR', 'Toronto','300 Regina Street'),
('IGOOAR', 'Toronto','300 Regina Street'),
('IGOPAR', 'Toronto','300 Regina Street'),
('GOOGLE', 'Toronto','300 Regina Street'),
('YAH-OO', 'Toronto','300 Regina Street'),
('AMAZON', 'Toronto','300 Regina Street'),
('BMAZON','Toronto','300 Regina Street'),
('CMAZON', 'Toronto','300 Regina Street'),
('DMAZON', 'Toronto','300 Regina Street'),
('EMAZON', 'Toronto','300 Regina Street'),
('FMAZON', 'Toronto','300 Regina Street'),
('GMAZON','Toronto','300 Regina Street'),
('TMAZON','Toronto','300 Regina Street'),
('QMAZON','Toronto','300 Regina Street'),
('BCPL1S', 'Toronto','300 Regina Street'),
('BCPL2S', 'Toronto','300 Regina Street'),
('BCPL3S', 'Toronto','300 Regina Street'),
('BCPL4S', 'Toronto','300 Regina Street'),
('BCPL5S', 'Toronto','300 Regina Street'),
('BCPL6S', 'Toronto','300 Regina Street'),
('BCPL7S', 'Toronto','300 Regina Street'),
('BCPL8S', 'Toronto','300 Regina Street'),
('BCPL9S', 'Toronto','300 Regina Street'),
('BCPL0S', 'Toronto','300 Regina Street'),
('ACPL1S', 'Toronto','300 Regina Street'),
('ACPL2S', 'Vancouver','2660 Wesbrook Mall'),
('ACPL3S', 'Vancouver','2660 Wesbrook Mall'),
('ACPL4S', 'Vancouver','2660 Wesbrook Mall'),
('ACPL5S', 'Vancouver','2660 Wesbrook Mall'),
('ACPL6S', 'Vancouver','2660 Wesbrook Mall'),
('ACPL7S', 'Vancouver','2660 Wesbrook Mall'),
('ACPL8S', 'Vancouver','2660 Wesbrook Mall');

INSERT INTO keep_equipment 
VALUES
('ski_rack','Vancouver','2660 Wesbrook Mall',10),
('child_safety_seat','Vancouver','2660 Wesbrook Mall',10),
('lift_gate','Vancouver','2660 Wesbrook Mall',10),
('car_towing','Vancouver','2660 Wesbrook Mall',10),
('ski_rack','Toronto','300 Regina Street',10),
('child_safety_seat','Toronto','300 Regina Street',10),
('lift_gate','Toronto','300 Regina Street',10),
('car_towing','Toronto','300 Regina Street',10);

INSERT INTO reservation
VALUES
(1,'2014-02-04',3,'2014-02-06',8,8000,'Toronto','300 Regina Street','frank7','expired','economy'),
(2,'2014-04-04',3,'2014-04-06',8,8000,'Toronto','300 Regina Street','frank7','rented','economy'),
(3,'2014-03-04',3,'2014-03-06',8,18000,'Vancouver','2660 Wesbrook Mall','frank8','expired','economy'),
(4,'2014-04-04',3,'2014-04-06',8,18000,'Vancouver','2660 Wesbrook Mall','frank8','rented','economy'),
(5,'2014-03-04',3,'2014-03-06',8,18000,'Vancouver','2660 Wesbrook Mall','frank9','expired','economy'),
(6,'2014-04-04',3,'2014-04-06',8,18000,'Vancouver','2660 Wesbrook Mall','frank9','rented','economy'),
(7,'2014-03-04',3,'2014-03-06',8,28000,'Toronto','300 Regina Street','customer','expired','foot24'),
(8,'2014-04-04',3,'2014-04-06',8,28000,'Toronto','300 Regina Street','customer','rented','foot24'),
(9,'2014-03-04',3,'2014-03-06',8,24000,'Toronto','300 Regina Street','frank10','expired','boxtrucks'),
(10,'2014-04-04',3,'2014-04-06',8,24000,'Toronto','300 Regina Street','frank10','rented','boxtrucks'),
(11,'2012-02-04',3,'2012-02-06',8,8000,'Toronto','300 Regina Street','frank7','expired','boxtrucks'),
(12,'2012-06-04',3,'2012-06-06',8,8000,'Toronto','300 Regina Street','frank7','pending','boxtrucks');


INSERT INTO rent
VALUES
(1,1,'A123456789871','PLT-4M','Vancouver','2660 Wesbrook Mall','frank8','Visa','4507556798979871','2017-03-01','2014-02-04',3,'2014-02-06',8),
(2,1,'A123456789871','PLT-4M','Vancouver','2660 Wesbrook Mall','frank8','Visa','4507556798979871','2017-03-01','2014-04-04',3,'2014-04-06',8),

(3,1,'A123456789872','QMAZON','Toronto','300 Regina Street','frank7','Visa','4507556798979872','2017-03-02','2014-03-04',3,'2014-03-06',8),
(4,1,'A123456789872','QMAZON','Toronto','300 Regina Street','frank7','Visa','4507556798979872','2017-03-02','2014-04-04',3,'2014-04-06',8),

(5,1,'A123456789873','GM','Vancouver','2660 Wesbrook Mall','frank9','Visa','4507556798979873','2017-03-03','2014-03-04',3,'2014-03-06',8),
(6,1,'A123456789873','GM','Vancouver','2660 Wesbrook Mall','frank9','Visa','4507556798979873','2017-03-03','2014-04-04',3,'2014-04-06',8),

(7,1,'A123456789874','GOOGLE','Toronto','300 Regina Street','frank10','Visa','4507556798979874','2017-03-04','2014-03-04',3,'2014-03-06',8),
(8,1,'A123456789874','GOOGLE','Toronto','300 Regina Street','frank10','Visa','4507556798979874','2017-03-04','2014-04-04',3,'2014-04-06',8),

(9,1,'A123456789875','CMAZON','Toronto','300 Regina Street','customer','Visa','4507556798979875','2017-03-09','2014-03-04',3,'2014-03-06',8),
(10,1,'A123456789875','CMAZON','Toronto','300 Regina Street','customer','Visa','4507556798979875','2017-03-09','2014-04-04',3,'2014-04-06',8),

(11,0,'A123456789875','CMAZON','Toronto','300 Regina Street','customer','Visa','4507556798979875','2017-03-09','2014-03-11',3,'2014-03-16',8),
(12,0,'A123456789875','CMAZON','Toronto','300 Regina Street','customer','Visa','4507556798979875','2017-03-09','2014-04-17',3,'2014-04-23',8);

INSERT INTO vreturn
VALUES
(1,1,'2014-03-06',8,'Vancouver','2660 Wesbrook Mall',1,30070,23000,'Cash'),
(2,3,'2014-03-08',20,'Toronto','300 Regina Street',1,30080,24000,'Visa'),
(3,5,'2014-03-06',8,'Vancouver','2660 Wesbrook Mall',1,30090,20000,'Cash'),
(4,7,'2014-03-08',10,'Toronto','300 Regina Street',1,30060,20000,'Visa'),
(5,9,'2014-03-06',8,'Toronto','300 Regina Street',1,30070,25000,'Cash'),
(6,11,'2014-03-19',9,'Toronto','300 Regina Street',1,30070,23000,'Visa');

INSERT INTO reserve_addon
VALUES
(1,1,'ski_rack'),
(2,1,'child_safety_seat'),
(3,1,'ski_rack'),
(4,1,'child_safety_seat'),
(7,2,'lift_gate'),
(8,1,'car_towing'),
(9,2,'lift_gate'),
(10,1,'car_towing');

INSERT INTO rent_addon
VALUES
(1,1,'ski_rack'),
(1,1,'child_safety_seat'),
(2,1,'child_safety_seat'),
(2,1,'ski_rack'),
(3,1,'ski_rack'),
(4,1,'child_safety_seat'),
(5,1,'child_safety_seat'),
(7,2,'lift_gate'),
(9,2,'lift_gate'),
(10,1,'car_towing');
