INSERT INTO vehicletype 
VALUES 
('boxtrucks',70000, 10000, 2000, 200, 28000, 1000, 200),
('cargovans',85000, 12500, 2500, 200, 28000, 1000, 200),
('compact',40000, 5500, 1100, 200, 28000, 1000, 200),
('economy',35000, 5000, 1000, 200, 28000, 1000, 200),
('foot12',55000, 8000, 1500, 200, 28000, 1000, 200),
('foot15',60000, 8500, 1600, 200, 28000, 1000, 200),
('foot24',60000, 8000, 1700, 200, 28000, 1000, 200),
('full-size',49000, 7000, 1400, 200, 28000, 1000, 200),
('luxury',320000, 80000, 16000, 200, 28000, 1000, 200),
('midsize',35000, 5000, 1200, 200, 28000, 1000, 200),
('premium',50000, 7500, 1500, 200, 28000, 1000, 200),
('standard',50000, 6500, 1300, 200, 28000, 1000, 200),
('suv',63000, 9000, 1800, 200, 28000, 1000, 200),
('van',70000, 10000, 2000, 200, 28000, 1000, 200);


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
('frank7','5197816707','94 Mcdonald Road',1,0,0),
('frank8','5197816708','94 Mcdonald Road',0,0,0),
('frank9','5197816709','94 Mcdonald Road',0,1,500),
('customer','5197816787','94 Mcdonald Road',0,1,1000),
('frank10','5197816700','94 Mcdonald Road',1,1,2300);

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
('ski_rack','car',10,2),
('child_safety_seat','car',10,2),
('lift_gate','truck',10,2),
('car_towing','truck',10,2);

INSERT INTO vehiclesold
VALUES 
('PLT-4UP','2014-02-02','economy','car','BMW','frank1',30000),
('PLT-4U1','2014-02-03','economy','car','BMW','frank1',60000);

INSERT INTO vehicleforrent 
VALUES
('PLT-4D', 0,'1999-5-1','car', 'economy','Honda','frank1',3000),
('PLT-4M', 1,'2000-5-1','car', 'economy','BMW','frank1',3000),
('PLT-4T', 1,'2000-5-1','car', 'economy','BMW','frank1',3000),
('PLT-4C', 1,'2006-5-1','car', 'economy','BMW','frank1',3000),
('HOW DOO', 1,'2007-5-1','car', 'economy','BMW','frank1',6000),
('GM', 1,'2008-5-1','car', 'economy','BMW','frank1',6000),
('OUR BC', 1,'2009-5-1','car', 'economy','BMW','frank1',6000),
('4U 2DVY', 1,'2010-5-1','truck', 'boxtrucks','BMW','frank1',6000),
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
('IGOPAR', 1,'2013-5-1','truck', 'foot15','Ford FSeries','frank1',3000),
('GOOGLE', 1,'1999-5-1','truck', 'foot24','Ford FSeries','frank1',3000),
('YAH-OO', 1,'2000-5-1','truck', 'foot12','Ford FSeries','frank1',3000),
('AMAZON', 1,'2006-5-1','truck', 'foot15','Ford FSeries','frank1',3000),
('BMAZON', 1,'2007-5-1','truck', 'foot24','Ford FSeries','frank1',3000),
('CMAZON', 1,'2008-5-1','truck', 'boxtrucks','Ford FSeries','frank1',3000),
('DMAZON', 1,'2009-5-1','truck', 'boxtrucks','Ford FSeries','frank1',3000),
('EMAZON', 1,'2010-5-1','truck', 'boxtrucks','Ford FSeries','frank1',3000),
('FMAZON', 1,'1999-5-1','truck', 'cargovans','Dodge Ram','frank1',3000),
('GMAZON', 1,'2000-5-1','truck', 'cargovans','Dodge Ram','frank1',3000),
('TMAZON', 1,'2013-5-1','truck', 'cargovans','Dodge Ram','frank1',3000),
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
('1','2014-02-04',3,'2014-02-04',8,8000,'QMAZON','Toronto','300 Regina Street','frank7','car',1,'ski_rack');

INSERT INTO rent
VALUES
(1,0,'A123456789876','2008-07-06','PLT-4D','Vancouver','2660 Wesbrook Mall','frank8','car',1,'ski_rack','4507556798979876','2017-03-09','2014-03-12','2014-05-09',3,18);


