insert into clinical_centre (id, name) values ('1', 'The Good Shepherd');

/*Price list */
insert into pricelist (id, clinical_centre_id) values ('2', '1');

/*Price list items, recimo za sada da imamo 3 tipa pregleda pa cemo imati i 3 price itema*/
insert into price_list_item (id, price, pricelist_id) values ('3', '50', '2');
insert into price_list_item (id, price, pricelist_id) values ('4', '30', '2');
insert into price_list_item (id, price, pricelist_id) values ('5', '40', '2');

/*Jedina klinika koju imamo za sada*/
insert into clinic (id, name, address, city, country, description, clinical_center_id) 
values ('6', 'Clinic1', 'Adresa', 'City','Country', 'Description', '1');
/*Druga samo za probu*/
insert into clinic (id, name, address, city, country, description, clinical_center_id) 
values ('17', 'Clinic2', 'Adresa', 'City','Country', 'Description', '1');

/*Admin klinickog centra*/
insert into clinical_centre_admin (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, clinical_centre_id) 
values ('7', 'admin', 'admin', 'Ksenija', 'Prcic', 'female', 'datum', 'Adresa', 'City', 'Country', '55', '22', '1');

/*Admin klinike*/
insert into clinic_admin (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number,clinic_id) 
values ('8', 'pera', 'pera', 'Pera', 'Peric', 'male', 'datum', 'Adresa', 'City', 'Country', '55', '11', '6');


/*Doktori nase jedine klinike*/
insert into doctor (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, clinic_id, specialization) 
values ('9', 'jova', 'jova', 'Jova', 'Jovic', 'male', 'datum', 'Adresa', 'City', 'Country', '55', '33', '6', '4');

insert into doctor (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, clinic_id, specialization) 
values ('10', 'zova', 'zova', 'Zova', 'Zovic', 'female', 'datum', 'Adresa', 'City', 'Country', '55', '44', '6', '1');

insert into doctor (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, clinic_id, specialization) 
values ('11', 'gova', 'gova', 'Gova', 'Govic', 'male', 'datum', 'Adresa', 'City', 'Country', '55', '55', '17', '5');

/*Tri tipa pregleda*/
insert into appointment_type (id, name, duration, pricelist_item_id, doctor_id) values ('12', 'Gynecological', '1', '3', '9');
insert into appointment_type (id, name, duration, pricelist_item_id, doctor_id) values ('13', 'Dermatological', '2', '4', '10');
insert into appointment_type (id, name, duration, pricelist_item_id, doctor_id) values ('14', 'Dentist', '3', '5', '11');

/* Medicinske sestre */
insert into nurse (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number) 
values ('15', 'zika', 'zika', 'Zika', 'Zikic', 'male', 'datum', 'Adresa', 'City', 'Country', '44', '66' );

/* Pacijenti */
insert into patient (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number) 
values ('16', 'laza', 'laza', 'Laza', 'Lazic', 'male', 'datum', 'Adresa', 'City', 'Country', '55', '77');
