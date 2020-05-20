insert into clinical_centre (id, name, version) values ('1', 'The Good Shepherd', '0');

/*Jedina klinika koju imamo za sada*/
insert into clinic (id, name, address, city, country, description, rating, clinical_center_id, version) 
values ('6', 'Saint Mary Clinic', 'Lipov Gaj 23', 'Novi Sad', 'Srbija', 'Mother and child clinic', '4.6', '1', '0');
/*Druga samo za probu*/
insert into clinic (id, name, address, city, country, description, rating, clinical_center_id, version) 
values ('17', 'Dr Gray Clinic', 'Aleksandar Veliki 11', 'Nis', 'Srbija', 'First cardio clinic', '4.9', '1', '0');
/*Treca samo za probu*/
insert into clinic (id, name, address, city, country, description, rating, clinical_center_id, version) 
values ('18', 'Shephered United Clinic', 'Puskinova 3', 'Beograd', 'Srbija', 'Our united front for health', '4.3', '1', '0');

/*Odinacije*/
/*Prva klinika*/
insert into ordination(id, ordination_type, name, ordination_number, clinic_id, version)
values('19', '0', 'The Great Room', '1', '6', '0');
insert into ordination(id, ordination_type, name, ordination_number, clinic_id, version)
values('20', '1', 'Regular Exam', '2', '6', '0');
insert into ordination(id, ordination_type, name, ordination_number, clinic_id, version)
values('37', '1', 'Regular Exam', '3', '6', '0');
/*Druga klinika*/
insert into ordination(id, ordination_type, name, ordination_number, clinic_id, version)
values('50', '1', 'The Gray Room', '1', '17', '0');
insert into ordination(id, ordination_type, name, ordination_number, clinic_id, version)
values('51', '1', 'The Good Exam', '2', '17', '0');

/*Admin klinickog centra*/
insert into clinical_centre_admin (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, clinical_centre_id, version) 
values ('7', 'thegoodshepherdadm@gmail.com', 'admin1tgs', 'Ksenija', 'Prcic', 'female', '1990-06-15', 'Adresa', 'City', 'Country', '55', '22', '1', '0');

/*Admin klinike*/
insert into clinic_admin (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number,clinic_id, version) 
values ('8', 'felisimadolanovski@gmail.com', 'felidola1', 'Felisima', 'Dolanovski', 'female', '1978-05-20', 'Adresa1', 'Grad1', 'Ukrajina', '55', '11', '17', '0');

insert into clinic_admin (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number,clinic_id, version) 
values ('22', 'perapera2359@gmail.com', 'perapera2', 'Petar', 'Isailovic', 'male', '1989-07-20', 'Kralja Petra I 42', 'Nis', 'Srbija', '55', '98', '6', '0');

/*Doktori nase prve klinike*/
insert into doctor (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, rating, clinic_id, specialization, start_work, end_work, version) 
values ('9', 'jovadoktor1@gmail.com', 'jovajova1', 'Jova', 'Jovic', 'male', '1965-03-01', 'Isidorina 3', 'Novi Sad', 'Srbija', '55', '33', '4.7', '6', '4', '6', '14', '0');
insert into doctor (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, rating, clinic_id, specialization, start_work, end_work, version) 
values ('10', 'zovadoktorka@gmail.com', 'zovazova1', 'Zova', 'Zovic', 'female', '1980-07-09', 'Knez Mihailova 2', 'Beograd', 'Srbija', '55', '44', '4.3', '6', '5', '8', '16', '0');
insert into doctor (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, rating, clinic_id, specialization, start_work, end_work, version) 
values ('52', 'kova', 'kova', 'Kova', 'Kovic', 'female', '1993-06-01', 'Milosa Obilica 56', 'Negotin', 'Srbija', '55', '99', '3.7', '6', '5', '13', '21', '0');
/*Doktori nase druge klinike*/
insert into doctor (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, rating, clinic_id, specialization, start_work, end_work, version) 
values ('11', 'govadoktor@gmail.com', 'govagova1', 'Gova', 'Govic', 'male', '1972-10-19', 'Hajdova 5', 'Zrenjanin', 'Srbija', '55', '55', '4.8', '17', '5', '12', '20', '0');
insert into doctor (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, rating, clinic_id, specialization, start_work, end_work, version) 
values ('48', 'mova', 'mova', 'Mova', 'Movic', 'female', '1978-7-18', 'Ivanjicka 17', 'Ivanjica', 'Srbija', '55', '88', '3.9', '17', '3', '08', '14', '0');

/*Tri tipa pregleda*/
insert into appointment_type (id, name, duration, price, /*doctor_id,*/ clinic_id, version) values ('12', 'Gynecological', '1', '30', /*'9',*/ '6', '0');
insert into appointment_type (id, name, duration, price, /*doctor_id,*/ clinic_id, version) values ('13', 'Dermatological', '2', '40', /*'10',*/ '6', '0');
insert into appointment_type (id, name, duration, price, /*doctor_id,*/ clinic_id, version) values ('14', 'Dentist', '3', '60', /*'11',*/ '6', '0');
/*Dva tipa pregleda ce imati i klinika2*/
insert into appointment_type (id, name, duration, price, clinic_id, version) values ('19', 'Dentist', '4', '90', '17', '0');
insert into appointment_type (id, name, duration, price, clinic_id, version) values ('49', 'Orthopeadic', '2', '150', '17', '0');

/*Tabela doktori i tipovi pregleda*/
insert into appointment_type_doctor (appointment_type_id, doctor_id) values ('12', '9');
/*insert into appointment_type_doctor (appointment_type_id, doctor_id, version) values ('13', '10');klinika ima tip ali nema doktora*/
insert into appointment_type_doctor (appointment_type_id, doctor_id) values ('12', '10'); /*'14', '10'*/
insert into appointment_type_doctor (appointment_type_id, doctor_id) values ('14', '52');
/*Druga klinika*/
insert into appointment_type_doctor (appointment_type_id, doctor_id) values ('19', '11');
insert into appointment_type_doctor (appointment_type_id, doctor_id) values ('49', '48');

/* Medicinske sestre */
insert into nurse (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, version) 
values ('15', 'zikambrat@gmail.com', 'zikazika1', 'Zika', 'Zikic', 'male', '1990-06-15', 'Adresa', 'City', 'Country', '44', '66', '0');

/* Pacijenti i njihovi zdravstveni kartoni*/
insert into patient (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, clinical_centre_id, version) 
values ('16', 'srdjanmilic12@gmail.com', 'srdjabroj1', 'Srdjan', 'Milic', 'male', '1993-06-01', 'Adresa', 'City', 'Country', '55', '77', '1', '0');
/*Potvrdjen zahtev za registraciju*/
insert into registration_request(id, registered_user_id, approved, description, version) 
values ('56', '16', 'true', '', '0');
insert into medical_records(id, height, weight, blood_pressure, blood_type, allergies, patient_id, version)
values ('30' ,'180', '60', '120/60', 'A','No allergies', '16', '0');
update patient
set medical_record_id = '30', registration_request_id = '56'
where id = '16';
insert into patient (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, clinical_centre_id, version) 
values ('29', 'milkapacijent@gmail.com', 'milka12!', 'Milka', 'Jagodic', 'female', '1993-06-01', 'Adresa', 'City', 'Country', '55', '111', '1', '0');
/*Potvrdjen zahtev za registraciju*/
insert into registration_request(id, registered_user_id, approved, description, version) 
values ('55', '29', 'true', '', '0');
insert into medical_records(id, height, weight, blood_pressure, blood_type, allergies, patient_id, version)
values ('43' ,'163', '67', '130/80', 'AB-','Polen', '29', '0');
update patient
set medical_record_id = '43', registration_request_id = '55'
where id = '29';
/*Nova registracija pacijenta
insert into patient (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, clinical_centre_id, version) 
values ('30', 'dolanovkatarina@gmail.com', 'katarina1!', 'Katarina', 'Dolanov', 'female', '1993-06-01', 'Adresa', 'City', 'Country', '5500', '7700', '1', '0');
insert into registration_request(id, registered_user_id, approved, description, version) 
values ('31', '30', 'false', '', '0');*/

/*Jedan pregled potvrdjen i zavrsen*/
insert into appointment (id, app_finished, app_date, app_start_time, app_end_time, confirmed, patient_id, clinic_id, doctor_id, app_type, version)
values ('21', 'true', '2020-05-13', '16', '20', 'true', '16', '17', '9', '19', '0');

/*TREBAO MI JE SAMO APPOINTMENT KOJI JE POTVRDJEN A NIJE ZAVRSEN*/
/*pregled kod jove koji je sada, pa moze da pristupi medical record*/
/*JOS JEDAN MILKIN PREGLED KOJI JE TRENUTNO*/
insert into appointment (id, app_finished, app_date, app_start_time, app_end_time, confirmed, patient_id, clinic_id, doctor_id, app_type, ordination_id, version)
values ('44', 'false', '2020-05-20', '20', '21', 'true', '29', '6', '9', '12', '19', '0');
insert into appointment_request (id, appointment_id, request_date, approved, clinic_id, version)
values ('54', '44', '2020-05-15', 'true', '6', '0');

/*NE preklapa se NI SA JEDNIM drugim vremenom u toj ordinaciji*/
insert into appointment (id, app_finished, app_date, app_start_time, app_end_time, confirmed, patient_id, clinic_id, doctor_id, app_type, version)
values ('36', 'false', '2020-05-21', '06', '07', 'false', '29', '6', '9', '12', '0');
insert into appointment_request (id, appointment_id, request_date, approved, clinic_id, version)
values ('38', '36', '2020-05-20', 'false', '6', '0');
/*NE preklapa se sa jednim drugim vremenom u toj ordinaciji*/
insert into appointment (id, app_finished, app_date, app_start_time, app_end_time, confirmed, patient_id, clinic_id, doctor_id, app_type, version)
values ('41', 'false', '2020-05-21', '08', '09', 'false', '29', '6', '9', '12', '0');
insert into appointment_request (id, appointment_id, request_date, approved, clinic_id, version)
values ('42', '41', '2020-05-20', 'false', '6', '0');
/*Preklapa se sa drugim vremenom u toj ordinaciji*/
insert into appointment (id, app_finished, app_date, app_start_time, app_end_time, confirmed, patient_id, clinic_id, doctor_id, app_type, version)
values ('39', 'false', '2020-05-21', '09', '10', 'false', '29', '6', '9', '12', '0');
insert into appointment_request (id, appointment_id, request_date, approved, clinic_id, version)
values ('40', '39', '2020-05-20', 'false', '6', '0');

/*Jedan pregled NEpotvrdjen*/
/*ZAHTEVI DRUGE KLINIKE!!!*/
insert into appointment (id, app_finished, app_date, app_start_time, app_end_time, confirmed, patient_id, clinic_id, doctor_id, app_type, version)
values ('23', 'false', '2020-05-15', '16', '20', 'false', '16', '17', '11', '19', '0');
/*Jedan zahtev za nepotvrdjen pregled*/
insert into appointment_request (id, appointment_id, request_date, approved, clinic_id, version)
values ('24', '23', '2020-05-15', 'false', '17', '0');

/*Predefinisani pregledi*/
/*Predefinisani druga klinika*/
insert into appointment (id, app_finished, app_date, app_start_time, app_end_time, confirmed, clinic_id, doctor_id, app_type, ordination_id, version)
values ('45', 'false', '2020-05-21', '16', '20', 'false', '17', '11', '19', '50', '0');
insert into appointment (id, app_finished, app_date, app_start_time, app_end_time, confirmed, clinic_id, doctor_id, app_type, ordination_id, version)
values ('46', 'false', '2020-05-21', '12', '16', 'false', '17', '11', '19', '51', '0');
insert into appointment (id, app_finished, app_date, app_start_time, app_end_time, confirmed, clinic_id, doctor_id, app_type, ordination_id, version)
values ('47', 'false', '2020-05-21', '08', '10', 'false', '17', '48', '49', '50', '0');

/*Zahtevi u prvoj klinici*/
/*Milkini pregledi - OBA ODOBRENA!*/
/*Jedan prosao*/
insert into appointment (id, app_finished, app_date, app_start_time, app_end_time, confirmed, patient_id, clinic_id, doctor_id, app_type, ordination_id, version)
values ('57', 'true', '2020-05-13', '09', '10', 'true', '29', '6', '9', '12', '20', '0');
insert into appointment_request (id, appointment_id, request_date, approved, clinic_id, version)
values ('58', '57', '2020-05-12', 'true', '6', '0');
/*Jedan potvrdjen - preklapanje sa njim*/
insert into appointment (id, app_finished, app_date, app_start_time, app_end_time, confirmed, patient_id, clinic_id, doctor_id, app_type, ordination_id, version)
values ('25', 'false', '2020-05-21', '09', '10', 'true', '29', '6', '9', '12', '20', '0');
insert into appointment_request (id, appointment_id, request_date, approved, clinic_id, version)
values ('26', '25', '2020-05-15', 'true', '6', '0');
/*Jedan nepotvrdjen - preklapanje sa njim*/
insert into appointment (id, app_finished, app_date, app_start_time, app_end_time, confirmed, patient_id, clinic_id, doctor_id, app_type, ordination_id, version)
values ('27', 'false', '2020-05-21', '08', '11', 'false', '29', '6', '10', '14', '37', '0');
insert into appointment_request (id, appointment_id, request_date, approved, clinic_id, version)
values ('28', '27', '2020-05-20', 'true', '6', '0');
update appointment
set appointment_request_id = '28'
where id = '27';

/*Zahtev za operaciju*/
insert into appointment (id, app_finished, app_date, app_start_time, app_end_time, confirmed, patient_id, clinic_id, doctor_id, app_type, ordination_type, version)
values ('55', 'false', '2020-05-25', '13', '15', 'false', '29', '6', '9', '12', '0', '0');
insert into appointment_request (id, appointment_id, request_date, approved, clinic_id, version)
values ('56', '55', '2020-05-15', 'false', '6', '0');

/*Sifarnik*/
insert into diagnose_perscription (id, version)
values ('33', '0');
/*Dijagnoza*/
insert into diagnosis (id, name, sifarnik_id, version)
values ('31', 'Vitiligo', '33', '0');
insert into diagnosis (id, name, sifarnik_id, version)
values ('53', 'Healthy', '33', '0');
/*Lek*/
insert into prescription (id, medicine, version)
values ('32', 'Krema za vitiligo', '0');
insert into prescription (id, medicine, version)
values ('35', 'Tablete za vitiligo', '0');
/*Povezivanje dijagnoze i lekova za nju*/
insert into perscription_diagnosis (diagnosis_id, perscription_id)
values ('31', '32');
insert into perscription_diagnosis (diagnosis_id, perscription_id)
values ('31', '35');

/*Medical report (izvestaj doktora)*/
insert into medical_report (id, description, diagnosis_id, appointment_id, version)
values ('34', 'Pacijent pokazuje pocetni stadijum Vitiliga, pecati se nalaze na sakama i ledjima.', '31', '21', '0');
/*Lekovi prepisani ovim izvestajem gore*/
insert into medical_report_perscription(medical_report_id, prescription_id)
values ('34', '32');
insert into medical_report_perscription(medical_report_id, prescription_id)
values ('34', '35');
/*Dodavanje izvestaja u appointment*/
update appointment
set medical_report_id = '34'
where id = '21';

insert into medical_report (id, description, diagnosis_id, appointment_id, version)
values ('59', 'Pacijent pokazuje pocetni stadijum Vitiliga, pecati se nalaze na sakama i ledjima.', '31', '57', '0');
/*Lekovi prepisani ovim izvestajem gore*/
insert into medical_report_perscription(medical_report_id, prescription_id)
values ('59', '32');
insert into medical_report_perscription(medical_report_id, prescription_id)
values ('59', '35');
/*Dodavanje izvestaja u appointment*/
update appointment
set medical_report_id = '59'
where id = '57';