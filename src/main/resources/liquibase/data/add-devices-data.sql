insert into device(id, name , manufacture_date, ready, temperature) values (1, 'device1', '2020-12-01 12:10:11', false, 30);
insert into device(id, name , manufacture_date, ready, temperature) values (2, 'device2', '2020-12-01 12:30:11', true, 85);
insert into device(id, name , manufacture_date, ready, temperature) values (3, 'device3', '2020-11-01 12:10:11', true, -25);

insert into sim(id, device_id, operator_code, country, status) values (1,1,'010400', 'Egypt', 'ACTIVE');
insert into sim(id, device_id, operator_code, country, status) values (2,2,'010500', 'Egypt', 'ACTIVE');
insert into sim(id, device_id, operator_code, country, status) values (3,3,'010600', 'Germany', 'WAITING');

