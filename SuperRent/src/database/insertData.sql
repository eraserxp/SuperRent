
INSERT INTO vehicletype 
VALUES 
('boxtrucks',700.0, 100.0, 20.0, 2.0, 280.0, 70.0, 20.0),
('cargovans',850.0, 125.0, 25.0, 2.0, 280.0, 70.0, 20.0),
('compact',400.0, 55.0, 11.0, 2.0, 280.0, 70.0, 20.0),
('economy',350.0, 50.0, 10.0, 2.0, 280.0, 70.0, 20.0),
('foot12',550.0, 80.0, 15.0, 2.0, 280.0, 70.0, 20.0),
('foot15',600.0, 85.0, 16.0, 2.0, 280.0, 70.0, 20.0),
('foot24',600.0, 80.0, 17.0, 2.0, 280.0, 70.0, 20.0),
('full-size',490.0, 70.0, 14.0, 2.0, 280.0, 70.0, 20.0),
('luxury',3200.0, 800.0, 160.0, 2.0, 280.0, 70.0, 20.0),
('midsize',350.0, 50.0, 12.0, 2.0, 280.0, 70.0, 20.0),
('premium',500.0, 75.0, 15.0, 2.0, 280.0, 70.0, 20.0),
('standard',500.0, 65.0, 13.0, 2.0, 280.0, 70.0, 20.0),
('suv',630.0, 90.0, 18.0, 2.0, 280.0, 70.0, 20.0),
('van',700.0, 100.0, 20.0, 2.0, 280.0, 70.0, 20.0);


INSERT INTO branch
VALUES 
('Vancouver','2660 Wesbrook Mall'),
('Toronto','300 Regina Street');


INSERT INTO user
VALUES 
('frank1','12345','frank1','manager'),
('frank2','12345','frank2','clerk'),
('frank3','12345','frank3','clerk'),
('frank4','12345','frank4','clerk'),
('frank5','12345','frank5','clerk'),
('frank6','12345','frank6','admin'),
('frank7','12345','frank7','customer'),
('frank8','12345','frank8','customer'),
('frank9','12345','frank9','customer'),
('frank10','12345','frank10','customer');

INSERT INTO customer
VALUES 
('frank7','5197816707','94 Mcdonald Road',1,0,0),
('frank8','5197816708','94 Mcdonald Road',0,0,0),
('frank9','5197816709','94 Mcdonald Road',0,1,500),
('frank10','5197816700','94 Mcdonald Road',1,1,2300);

INSERT INTO clerk
VALUES 
('frank2','Vancouver','2660 Wesbrook Mall'),
('frank3','Vancouver','2660 Wesbrook Mall'),
('frank4','Toronto','300 Regina Street'),
('frank5','Toronto','300 Regina Street');

INSERT INTO manager
VALUES 
('frank1');

INSERT INTO admin
VALUES 
('frank6');

INSERT INTO equipment
VALUES 
('ski_rack','car',10,2),
('child_safety_seat','car',10,2),
('lift_gate','truck',10,2),
('car_towing','truck',10,2);

INSERT INTO vehiclesold
VALUES 
(1,'2014-02-02','economy','car','frank1'),
(2,'2014-02-03','economy','car','frank1');

INSERT INTO vehicleforrent 
VALUES
(3, 1,'1999-5-1','car', 'economy','frank1'),
(4, 0,'2000-5-1','car', 'economy','frank1'),
(5, 1,'2006-5-1','car', 'economy','frank1'),
(6, 0,'2007-5-1','car', 'economy','frank1'),
(7, 1,'2008-5-1','car', 'economy','frank1'),
(8, 0,'2009-5-1','car', 'economy','frank1'),
(9, 1,'2010-5-1','car', 'economy','frank1'),
(10, 1,'1999-5-1','car', 'compact','frank1'),
(11, 0,'2000-5-1','car', 'compact','frank1'),
(12, 1,'2006-5-1','car', 'compact','frank1'),
(13, 1,'2008-5-1', 'car','compact','frank1'),
(14, 0,'2009-5-1','car', 'compact','frank1'),
(15, 1,'2010-5-1','car', 'compact','frank1'),
(16, 0,'1999-5-1','car', 'compact','frank1'),
(17, 1,'2000-5-1','car', 'full-size','frank1'),
(18, 1,'2013-5-1','car', 'full-size','frank1'),
(19, 0,'1977-5-1','car', 'luxury','frank1'),
(20, 1,'1977-5-1','car', 'luxury','frank1'),
(21, 1,'2010-5-1','car', 'luxury','frank1'),
(22, 1,'1998-5-1','car', 'midsize','frank1'),
(23, 1,'1999-5-1','car', 'midsize','frank1'),
(24, 0,'2000-5-1','car', 'premium','frank1'),
(25, 1,'2001-5-1','car', 'premium','frank1'),
(26, 1,'1997-5-1','car', 'standard','frank1'),
(27, 0,'1998-5-1', 'car','standard','frank1'),
(28, 1,'2006-5-1','car', 'suv','frank1'),
(29, 0,'2007-5-1', 'car','suv','frank1'),
(30, 1,'2008-5-1', 'car','suv','frank1'),
(31, 1,'2009-5-1','car', 'van','frank1'),
(32, 1,'2010-5-1', 'car','van','frank1'),
(33, 1,'1999-5-1', 'car','van','frank1'),
(34, 1,'2000-5-1','truck', 'foot12','frank1'),
(35, 1,'2013-5-1','truck', 'foot15','frank1'),
(36, 1,'1999-5-1','truck', 'foot24','frank1'),
(37, 1,'2000-5-1','truck', 'foot12','frank1'),
(38, 1,'2006-5-1','truck', 'foot15','frank1'),
(39, 1,'2007-5-1','truck', 'foot24','frank1'),
(40, 0,'2008-5-1','truck', 'boxtrucks','frank1'),
(41, 0,'2009-5-1','truck', 'boxtrucks','frank1'),
(42, 1,'2010-5-1','truck', 'boxtrucks','frank1'),
(43, 1,'1999-5-1','truck', 'cargovans','frank1'),
(44, 1,'2000-5-1','truck', 'cargovans','frank1'),
(45, 1,'2013-5-1','truck', 'cargovans','frank1'),
(46, 1,'1999-5-1','car', 'economy','frank1'),
(65, 1,'2009-5-3','truck','boxtrucks','frank1');

INSERT INTO vehicleforsale 
VALUES
(47, 300000,'2000-5-1','car', 'economy'),
(48, 300000,'2006-5-1','car', 'economy'),
(49, 300000,'2007-5-1','car', 'economy'),
(50, 300000,'2008-5-1','car', 'compact'),
(51, 300000,'2009-5-1','car', 'compact'),
(52, 300000,'2010-5-1','car', 'compact'),
(53, 300000,'1999-5-1','car','full-size'),
(54, 300000,'2000-5-1','car', 'full-size'),
(55, 700000,'2007-5-1','car', 'luxury'),
(56, 300000,'2008-5-1','car', 'luxury'),
(57, 300000,'2009-5-1','car', 'luxury'),
(58, 300000,'2010-5-1','car', 'midsize'),
(59, 300000,'1999-5-1','truck', 'foot15'),
(60, 300000,'2000-5-1','truck', 'foot24'),
(61, 300000,'2007-5-1','truck', 'foot12'),
(62, 300000,'2008-5-1','truck', 'foot15'),
(63, 300000,'2009-5-1','truck', 'foot24'),
(64, 300000,'2009-5-2','truck', 'boxtrucks');

INSERT INTO vehicleinbranch 
VALUES
(3, 'Vancouver','2660 Wesbrook Mall'),
(4, 'Vancouver','2660 Wesbrook Mall'),
(5,'Vancouver','2660 Wesbrook Mall'),
(6,'Vancouver','2660 Wesbrook Mall'),
(7,'Vancouver','2660 Wesbrook Mall'),
(8,'Vancouver','2660 Wesbrook Mall'),
(9, 'Vancouver','2660 Wesbrook Mall'),
(10, 'Vancouver','2660 Wesbrook Mall'),
(11,'Vancouver','2660 Wesbrook Mall'),
(12,'Vancouver','2660 Wesbrook Mall'),
(13,'Vancouver','2660 Wesbrook Mall'),
(14, 'Vancouver','2660 Wesbrook Mall'),
(15, 'Vancouver','2660 Wesbrook Mall'),
(16,'Vancouver','2660 Wesbrook Mall'),
(17, 'Vancouver','2660 Wesbrook Mall'),
(18, 'Vancouver','2660 Wesbrook Mall'),
(19, 'Vancouver','2660 Wesbrook Mall'),
(20, 'Vancouver','2660 Wesbrook Mall'),
(21,'Vancouver','2660 Wesbrook Mall' ),
(22,'Vancouver','2660 Wesbrook Mall'),
(23, 'Vancouver','2660 Wesbrook Mall'),
(24,'Vancouver','2660 Wesbrook Mall'),
(25,'Vancouver','2660 Wesbrook Mall'),
(26, 'Vancouver','2660 Wesbrook Mall'),
(27, 'Vancouver','2660 Wesbrook Mall'),
(28,'Vancouver','2660 Wesbrook Mall'),
(29,'Vancouver','2660 Wesbrook Mall' ),
(30, 'Toronto','300 Regina Street'),
(31, 'Toronto','300 Regina Street'),
(32, 'Toronto','300 Regina Street'),
(33, 'Toronto','300 Regina Street'),
(34, 'Toronto','300 Regina Street'),
(35, 'Toronto','300 Regina Street'),
(36, 'Toronto','300 Regina Street'),
(37, 'Toronto','300 Regina Street'),
(38, 'Toronto','300 Regina Street'),
(39, 'Toronto','300 Regina Street'),
(40,'Toronto','300 Regina Street'),
(41, 'Toronto','300 Regina Street'),
(42, 'Toronto','300 Regina Street'),
(43, 'Toronto','300 Regina Street'),
(44, 'Toronto','300 Regina Street'),
(45,'Toronto','300 Regina Street'),
(46,'Toronto','300 Regina Street'),
(65,'Toronto','300 Regina Street'),
(47, 'Toronto','300 Regina Street'),
(48, 'Toronto','300 Regina Street'),
(49, 'Toronto','300 Regina Street'),
(50, 'Toronto','300 Regina Street'),
(51, 'Toronto','300 Regina Street'),
(52, 'Toronto','300 Regina Street'),
(53, 'Toronto','300 Regina Street'),
(54, 'Toronto','300 Regina Street'),
(55, 'Toronto','300 Regina Street'),
(56, 'Toronto','300 Regina Street'),
(57, 'Toronto','300 Regina Street'),
(58, 'Vancouver','2660 Wesbrook Mall'),
(59, 'Vancouver','2660 Wesbrook Mall'),
(60, 'Vancouver','2660 Wesbrook Mall'),
(61, 'Vancouver','2660 Wesbrook Mall'),
(62, 'Vancouver','2660 Wesbrook Mall'),
(63, 'Vancouver','2660 Wesbrook Mall'),
(64, 'Vancouver','2660 Wesbrook Mall');

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
(1,'2014-02-04','3:30','2014-02-04','3:30',800,47,'Toronto','300 Regina Street','frank7','car',1,'ski_rack');

INSERT INTO rent
VALUES
(1,0,'A123456789876','2008-07-06',3,'Vancouver','2660 Wesbrook Mall','frank8','car',1,'ski_rack','4507556798979876','2017-03-09','2014-03-12','2014-05-09','3:40','4:50');


