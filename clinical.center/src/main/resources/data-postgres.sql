insert into clinical_centre (id, name) values ('1', 'The Good Shepherd');

/*Jedina klinika koju imamo za sada*/
insert into clinic (id, name, address, city, country, description, rating, clinical_center_id) 
values ('6', 'Saint Mary Clinic', 'Lipov Gaj 23', 'Novi Sad', 'Srbija', 'Mother and child clinic', '4.6', '1');
/*Druga samo za probu*/
insert into clinic (id, name, address, city, country, description, rating, clinical_center_id) 
values ('17', 'Dr Gray Clinic', 'Aleksandar Veliki 11', 'Nis', 'Srbija', 'First cardio clinic', '4.9', '1');
/*Treca samo za probu*/
insert into clinic (id, name, address, city, country, description, rating, clinical_center_id) 
values ('18', 'Shephered United Clinic', 'Puskinova 3', 'Beograd', 'Srbija', 'Our united front for health', '4.3', '1');

/*Odinacije*/
insert into ordination(id, ordination_type, name, ordination_number, clinic_id)
values('19', '0', 'The Great Room', '1', '6');

insert into ordination(id, ordination_type, name, ordination_number, clinic_id)
values('20', '1', 'Regular Exam', '2', '6');

/*Admin klinickog centra*/
insert into clinical_centre_admin (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, clinical_centre_id) 
values ('7', 'thegoodshepherdadm@gmail.com', 'admin1tgs', 'Ksenija', 'Prcic', 'female', 'datum', 'Adresa', 'City', 'Country', '55', '22', '1');

/*Admin klinike*/
insert into clinic_admin (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number,clinic_id) 
values ('8', 'felisimadolanovski@gmail.com', 'felidola1', 'Felisima', 'Dolanovski', 'female', '1978-05-20', 'Adresa1', 'Grad1', 'Ukrajina', '55', '11', '17');

insert into clinic_admin (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number,clinic_id) 
values ('22', 'perapera2359@gmail.com', 'perapera2', 'Petar', 'Isailovic', 'male', '1989-07-20', 'Kralja Petra I 42', 'Nis', 'Srbija', '55', '98', '6');

/*Doktori nase jedine klinike*/
insert into doctor (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, rating, clinic_id, specialization, start_work, end_work) 
values ('9', 'jova', 'jova', 'Jova', 'Jovic', 'male', '1965-03-01', 'Isidorina 3', 'Novi Sad', 'Srbija', '55', '33', '4.7', '6', '4', '6', '14');

insert into doctor (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, rating, clinic_id, specialization, start_work, end_work) 
values ('10', 'zova', 'zova', 'Zova', 'Zovic', 'female', '1980-07-09', 'Knez Mihailova 2', 'Beograd', 'Srbija', '55', '44', '4.3', '6', '5', '8', '16');
/*Doktor nase druge klinike*/
insert into doctor (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, rating, clinic_id, specialization, start_work, end_work) 
values ('11', 'gova', 'gova', 'Gova', 'Govic', 'male', '1972-10-19', 'Hajdova 5', 'Zrenjanin', 'Srbija', '55', '55', '4.8', '17', '5', '12', '20');

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

/* Pacijenti i njihovi zdravstveni kartoni*/
insert into medical_records(id, height, weight, blood_pressure, blood_type, allergies)
values ('30' ,'180', '60', '120/60', 'A','No allergies');
insert into patient (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, clinical_centre_id, medical_record_id) 
values ('16', 'srdjanmilic12@gmail.com', 'srdjabroj1', 'Srdjan', 'Milic', 'male', 'datum', 'Adresa', 'City', 'Country', '55', '77', '1', '30');
update medical_records
set patient_id = '16'
where id = '30';

insert into patient (id, email, password, name, surname, gender, date_of_birth, address, city, country, phone_number, security_number, clinical_centre_id) 
values ('29', 'milka', 'milka', 'Milka', 'Jagodic', 'female', 'datum', 'Adresa', 'City', 'Country', '55', '111', '1');

/*Jedan pregled potvrdjen*/
insert into appointment (id, app_finished, app_date, app_start_time, app_end_time, confirmed, patient_id, clinic_id, doctor_id, app_type)
values ('21', 'true', '2020-05-06', '16', '20', 'true', '16', '17', '11', '19');
/*Treba mi ovo samo za probu*/
insert into appointment (id, app_finished, app_date, app_start_time, app_end_time, confirmed, patient_id, clinic_id, doctor_id, app_type)
values ('36', 'false', '2020-05-12', '16', '20', 'true', '29', '17', '11', '19');
/*Jedan pregled NEpotvrdjen*/
insert into appointment (id, app_finished, app_date, app_start_time, app_end_time, confirmed, patient_id, clinic_id, doctor_id, app_type)
values ('23', 'false', '2020-05-05', '16', '20', 'false', '16', '17', '11', '19');

/*Jedan zahtev za nepotvrdjen pregled*/
insert into appointment_request (id, appointment_id, request_date, approved, clinic_id)
values ('24', '23', '2020-05-05', 'false', '17');

/*Zahtevi u prvoj klinici*/
insert into appointment (id, app_finished, app_date, app_start_time, app_end_time, confirmed, patient_id, clinic_id, doctor_id, app_type)
values ('25', 'false', '2020-06-05', '09', '10', 'false', '29', '6', '9', '12');
insert into appointment_request (id, appointment_id, request_date, approved, clinic_id)
values ('26', '25', '2020-05-05', 'false', '6');
insert into appointment (id, app_finished, app_date, app_start_time, app_end_time, confirmed, patient_id, clinic_id, doctor_id, app_type)
values ('27', 'false', '2020-05-05', '08', '11', 'false', '29', '6', '10', '14');
insert into appointment_request (id, appointment_id, request_date, approved, clinic_id)
values ('28', '27', '2020-05-06', 'false', '6');

/*Sifarnik*/
insert into diagnose_perscription (id)
values ('33');
/*Dijagnoza*/
insert into diagnosis (id, name, sifarnik_id)
values ('31', 'Vitiligo', '33');
/*Lek*/
insert into prescription (id, medicine)
values ('32', 'Krema za vitiligo');
insert into prescription (id, medicine)
values ('35', 'Tablete za vitiligo');
/*Povezivanje dijagnoze i lekova za nju*/
insert into diagnosis_perscription (diagnosis_id, perscription_id)
values ('31', '32');
insert into diagnosis_perscription (diagnosis_id, perscription_id)
values ('31', '35');

/*Medical report (izvestaj doktora)*/
insert into medical_report (id, description, diagnosis_id, appointment_id)
values ('34', 'Pacijent pokazuje pocetni stadijum Vitiliga, pecati se nalaze na sakama i ledjima.', '31', '21');
/*Lekovi prepisani ovim izvestajem gore*/
insert into medical_report_perscription(medical_report_id, prescription_id)
values ('34', '32');
insert into medical_report_perscription(medical_report_id, prescription_id)
values ('34', '35');
/*Dodavanje izvestaja u appointment*/
update appointment
set medical_report_id = '34'
where id = '21';