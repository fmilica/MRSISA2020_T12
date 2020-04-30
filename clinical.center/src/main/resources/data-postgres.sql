insert into clinical_centre (id, name) values ('7', 'The Good Shepherd');

insert into clinic (id, name, address, city, country, description, clinical_center_id) 
values ('6', 'Clinic1', 'Adresa', 'City','Country', 'Description', '7');

insert into clinic_admin (id,email, password, name,surname, address, city, country,phone_number,security_number, clinic_id) 
values ('1','pera', 'pera', 'Pera', 'Peric','Adresa', 'City','Country', '55','55', '6');

insert into clinical_centre_admin (id,email, password, name,surname, address, city, country,phone_number,security_number, clinical_centre_id) 
values ('2','admin', 'admin', 'Ksenija', 'Prcic','Adresa', 'City','Country', '55','55', '7');

insert into doctor (id,email, password, name,surname, address, city, country,phone_number,security_number) 
values ('3','jova', 'jova', 'Jova', 'Jovic','Adresa', 'City','Country', '55','55');

insert into nurse (id,email, password, name,surname, address, city, country,phone_number,security_number) 
values ('4','zika', 'zika', 'Zika', 'Zikic','Adresa', 'City','Country', '55','55' );

insert into patient (id,email, password, name,surname, address, city, country,phone_number,security_number) 
values ('5','laza', 'laza', 'Laza', 'Lazic','Adresa', 'City','Country', '55','55' );