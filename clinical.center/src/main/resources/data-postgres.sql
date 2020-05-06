insert into clinical_centre (id, name) values ('1', 'The Good Shepherd');

/*Jedina klinika koju imamo za sada*/
insert into clinic (id, name, address, city, country, description, clinical_center_id) 
values ('6', 'Clinic1', 'Adresa', 'City','Country', 'Description', '1');
/*Druga samo za probu*/
insert into clinic (id, name, address, city, country, description, clinical_center_id) 
values ('17', 'Clinic2', 'Adresa', 'City','Country', 'Description', '1');
/*Treca samo za probu*/
insert into clinic (id, name, address, city, country, description, clinical_center_id) 
values ('18', 'Clinic3', 'Adresa', 'City','Country', 'Description', '1');

/*Odinacije*/
insert into ordination(id, ordination_type, name, ordination_number, clinic_id)
values('19', '0', 'Ordinacija1', '1', '6');

insert into ordination(id, ordination_type, name, ordination_number, clinic_id)
values('20', '1', 'Ordinacija2', '2', '6');

/*Admin klinickog centra*/
insert into clinical_centre_admin (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, clinical_centre_id) 
values ('7', 'thegoodshepherdadm@gmail.com', 'admin1tgs', 'Ksenija', 'Prcic', 'female', 'datum', 'Adresa', 'City', 'Country', '55', '22', '1');

/*Admin klinike*/
insert into clinic_admin (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number,clinic_id) 
values ('8', 'dolanovkatarina@gmail.com', 'katarina1!', 'Katarina', 'Dolanov', 'female', 'datum', 'Adresa', 'City', 'Country', '55', '11', '6');

insert into clinic_admin (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number,clinic_id) 
values ('22', 'pera', 'pera', 'Petar', 'Isailovic', 'male', 'datum', 'Adresa', 'City', 'Country', '55', '98', '17');

/*Doktori nase jedine klinike*/
insert into doctor (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, clinic_id, specialization, start_work, end_work) 
values ('9', 'jova', 'jova', 'Jova', 'Jovic', 'male', 'datum', 'Adresa', 'City', 'Country', '55', '33', '6', '4', '6', '14');

insert into doctor (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, clinic_id, specialization, start_work, end_work) 
values ('10', 'zova', 'zova', 'Zova', 'Zovic', 'female', 'datum', 'Adresa', 'City', 'Country', '55', '44', '6', '5', '8', '16');
/*Doktor nase druge klinike*/
insert into doctor (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, clinic_id, specialization, start_work, end_work) 
values ('11', 'gova', 'gova', 'Gova', 'Govic', 'male', 'datum', 'Adresa', 'City', 'Country', '55', '55', '17', '5', '12', '20');

/*Tri tipa pregleda*/
insert into appointment_type (id, name, duration, price, /*doctor_id,*/ clinic_id) values ('12', 'Gynecological', '1', '30', /*'9',*/ '6');
insert into appointment_type (id, name, duration, price, /*doctor_id,*/ clinic_id) values ('13', 'Dermatological', '2', '40', /*'10',*/ '6');
insert into appointment_type (id, name, duration, price, /*doctor_id,*/ clinic_id) values ('14', 'Dentist', '3', '60', /*'11',*/ '6');
/*Jedan tip pregleda ce imati i klinika2*/
insert into appointment_type (id, name, duration, price, clinic_id) values ('19', 'Dentist', '4', '90', '17');

/*Tabela doktori i tipovi pregleda*/
insert into appointment_type_doctor (appointment_type_id, doctor_id) values ('12', '9');
/*insert into appointment_type_doctor (appointment_type_id, doctor_id) values ('13', '10');klinika ima tip ali nema doktora*/
insert into appointment_type_doctor (appointment_type_id, doctor_id) values ('14', '10');
insert into appointment_type_doctor (appointment_type_id, doctor_id) values ('19', '11');

/* Medicinske sestre */
insert into nurse (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number) 
values ('15', 'zika', 'zika', 'Zika', 'Zikic', 'male', 'datum', 'Adresa', 'City', 'Country', '44', '66' );

/* Pacijenti */
insert into patient (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, clinical_centre_id) 
values ('16', 'srdjanmilic12@gmail.com', 'srdjabroj1', 'Srdjan', 'Milic', 'male', 'datum', 'Adresa', 'City', 'Country', '55', '77', '1');

/*Jedan pregled*/
insert into appointment (id, app_date, app_time, confirmed, patient_id, clinic_id, doctor_id, app_type)
values ('21', '2020-5-6', '15:00:00', 'true', '16', '17', '11', '19');