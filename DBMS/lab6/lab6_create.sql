DROP TABLE visits;
DROP TABLE work_type;
DROP TABLE payment_type;
DROP TABLE patients;
DROP TABLE new_dentists;
DROP TABLE dentists;
DROP TABLE dental_formula;
DROP SEQUENCE visit_ids;

CREATE TABLE dental_formula (
    formula_id   INTEGER PRIMARY KEY,
    formula      VARCHAR2(100) NOT NULL
);

INSERT INTO dental_formula VALUES(1, 'I22C11P22M33');
INSERT INTO dental_formula VALUES(2, 'I22C11P11M44');
INSERT INTO dental_formula VALUES(3, 'I22C11P33M22');
INSERT INTO dental_formula VALUES(4, 'I22C11P22M33');
INSERT INTO dental_formula VALUES(5, 'I22C11P22M33');
INSERT INTO dental_formula VALUES(6, 'I22C11P22M33');
INSERT INTO dental_formula VALUES(7, 'I22C11P33M22');
INSERT INTO dental_formula VALUES(8, 'I22C11P22M33');

CREATE TABLE dentists (
    dentist_id           INTEGER PRIMARY KEY,
    dentist_surname      VARCHAR2(100) NOT NULL,
    dentist_name         VARCHAR2(100) NOT NULL,
    dentist_patronymic   VARCHAR2(100),
    dentist_address      VARCHAR2(100) NOT NULL,
    dentist_birthday     DATE NOT NULL
);

INSERT INTO dentists VALUES(10, 'Korhova', 'Irina', 'Batkovna', 'Svyazistov street, 45-123', TO_DATE('22/07/1971', 'dd/mm/yyyy'));
INSERT INTO dentists VALUES(20, 'Bobkovich', 'Efpatiy', 'Kirkorovich', 'Independence avenue, 174-151', TO_DATE('01/11/1986', 'dd/mm/yyyy'));
INSERT INTO dentists VALUES(30, 'Nikitin', 'Igor', 'Petrovich', 'Rotmistrova street, 23-20', TO_DATE('10/05/1973', 'dd/mm/yyyy'));

CREATE TABLE patients (
    patient_id                  INTEGER PRIMARY KEY,
    patient_surname             VARCHAR2(100) NOT NULL,
    patient_name                VARCHAR2(100) NOT NULL,
    patient_patronymic          VARCHAR2(100),
    patient_address             VARCHAR2(100) NOT NULL,
    patient_birthday            DATE NOT NULL,
    dentist_id                  INTEGER,
    formula_id                  INTEGER NOT NULL
);

INSERT INTO patients VALUES(1001, 'Kudosh', 'Ilya', 'Viktorovich', 'Selickogo street, 79-50', TO_DATE('01/06/2000', 'dd/mm/yyyy'), 10, 1);
INSERT INTO patients VALUES(1002, 'Pupkin', 'Vasya', 'Kirillovich', 'Bezadresnaya street, 12-110', TO_DATE('17/09/1978', 'dd/mm/yyyy'), 10, 2);
INSERT INTO patients VALUES(1003, 'GUItnikov', 'Serge', 'Eugenievich', 'Brilevskaya street, 16-31', TO_DATE('23/10/1991', 'dd/mm/yyyy'), 10, 3);
INSERT INTO patients VALUES(1004, 'Kashkevich', 'Serge', 'Ivanovich', 'Dzerzhinskogo avenue, 121-322', TO_DATE('15/06/1977', 'dd/mm/yyyy'), 20, 4);
INSERT INTO patients(patient_id, patient_surname, patient_name, patient_address, patient_birthday, dentist_id, formula_id) VALUES(1005, 'Cena', 'John', 'Alibegova street, 18-33', TO_DATE('04/03/1998', 'dd/mm/yyyy'), 20, 5);
INSERT INTO patients(patient_id, patient_surname, patient_name, patient_patronymic, patient_address, patient_birthday, formula_id) VALUES(1006, 'Ivanov', 'Ivan', 'Ivanovich', 'Bachilo street, 4-11', TO_DATE('11/09/1953', 'dd/mm/yyyy'), 6);
INSERT INTO patients(patient_id, patient_surname, patient_name, patient_patronymic, patient_address, patient_birthday, formula_id) VALUES(1007, 'Lopato', 'Anna', 'Petrovna', 'Nikiforova street, 18-23', TO_DATE('11/01/2005', 'dd/mm/yyyy'), 7);
INSERT INTO patients(patient_id, patient_surname, patient_name, patient_address, patient_birthday, dentist_id, formula_id) VALUES(1008, 'Tereshin', 'Kirill', 'Kizhevatova street, 56-158', TO_DATE('13/09/1990', 'dd/mm/yyyy'), 30, 8);

CREATE TABLE payment_type (
    payment_type_id   			INTEGER PRIMARY KEY,
    payment_type_description 	VARCHAR2(100),
    payment_card_number		  	INTEGER
);

INSERT INTO payment_type(payment_type_id, payment_type_description) VALUES(1, 'Cash');
INSERT INTO payment_type VALUES(2, 'Credit card', 10002000);
INSERT INTO payment_type VALUES(3, 'Credit card', 11112345);
INSERT INTO payment_type(payment_type_id, payment_type_description) VALUES(4, 'Bitcoin');

CREATE TABLE work_type (
    work_id            INTEGER PRIMARY KEY,
    work_description   VARCHAR2(100) NOT NULL UNIQUE,
    work_cost          FLOAT NOT NULL CHECK (work_cost > 0)
);

INSERT INTO work_type VALUES (1, 'Desinfection', 300.0);
INSERT INTO work_type VALUES (2, 'Tooth removal', 250.0);
INSERT INTO work_type VALUES (3, 'Cleaning', 400.0);

CREATE SEQUENCE visit_ids
  	START WITH 100
  	NOMAXVALUE
  	INCREMENT BY 100
  	CACHE 10;

CREATE TABLE visits (
    visit_id                       INTEGER PRIMARY KEY,
    visit_date                     TIMESTAMP NOT NULL,
    description                    VARCHAR2(100),
    total_cost        			   FLOAT NOT NULL CHECK (total_cost > 0),
    dentist_id                     INTEGER,
    work_id                        INTEGER,
    patient_id                     INTEGER NOT NULL,
    payment_type_id                INTEGER
);

INSERT INTO visits(visit_id, visit_date, total_cost, work_id, patient_id, payment_type_id)
    VALUES(visit_ids.NEXTVAL, TO_TIMESTAMP('10/10/2017 14:10:00','dd/mm/yyyy HH24:MI:SS'), 200.0, 1, 1001, 1);
INSERT INTO visits(visit_id, visit_date, total_cost, dentist_id, work_id, patient_id, payment_type_id)
    VALUES(visit_ids.NEXTVAL, TO_TIMESTAMP('10/10/2017 16:30:00','dd/mm/yyyy HH24:MI:SS'), 220.0, 20, 3, 1007, 1);  
INSERT INTO visits(visit_id, visit_date, total_cost, work_id, patient_id, payment_type_id)
    VALUES(visit_ids.NEXTVAL, TO_TIMESTAMP('10/10/2017 18:15:00','dd/mm/yyyy HH24:MI:SS'), 100.0, 3, 1002, 1);
INSERT INTO visits(visit_id, visit_date, total_cost, work_id, patient_id, payment_type_id)
    VALUES(visit_ids.NEXTVAL, TO_TIMESTAMP('12/10/2017 12:20:00','dd/mm/yyyy HH24:MI:SS'), 350.0, 3, 1003, 1);
INSERT INTO visits(visit_id, visit_date, total_cost, work_id, patient_id, payment_type_id)
    VALUES(visit_ids.NEXTVAL, TO_TIMESTAMP('12/10/2017 14:20:00','dd/mm/yyyy HH24:MI:SS'), 290.0, 1, 1005, 1);
INSERT INTO visits(visit_id, visit_date, description, total_cost, work_id, patient_id, payment_type_id)
    VALUES(visit_ids.NEXTVAL, TO_TIMESTAMP('15/10/2017 13:00:00','dd/mm/yyyy HH24:MI:SS'), 'Gold tooth insertion', 500.0, 2, 1004, 1);
INSERT INTO visits(visit_id, visit_date, total_cost, description, work_id, patient_id, payment_type_id)
    VALUES(visit_ids.NEXTVAL, TO_TIMESTAMP('19/10/2017 14:50:00','dd/mm/yyyy HH24:MI:SS'), 'New tools were used', 430.0, 1, 1005, 3);
INSERT INTO visits(visit_id, visit_date, total_cost, work_id, patient_id, payment_type_id)
    VALUES(visit_ids.NEXTVAL, TO_TIMESTAMP('19/10/2017 16:20:00','dd/mm/yyyy HH24:MI:SS'), 120.0, 1, 1008, 1);
INSERT INTO visits(visit_id, visit_date, total_cost, dentist_id, work_id, patient_id, payment_type_id)
    VALUES(visit_ids.NEXTVAL, TO_TIMESTAMP('23/10/2017 11:10:00','dd/mm/yyyy HH24:MI:SS'), 230.0, 10, 3, 1006, 1);  
INSERT INTO visits(visit_id, visit_date, total_cost, work_id, patient_id, payment_type_id)
    VALUES(visit_ids.NEXTVAL, TO_TIMESTAMP('27/10/2017 13:45:00','dd/mm/yyyy HH24:MI:SS'), 215.0, 2, 1001, 4);
INSERT INTO visits(visit_id, visit_date, total_cost, work_id, patient_id, payment_type_id)
    VALUES(visit_ids.NEXTVAL, TO_TIMESTAMP('02/11/2017 12:30:00','dd/mm/yyyy HH24:MI:SS'), 200.0, 1, 1004, 2);
INSERT INTO visits(visit_id, visit_date, total_cost, work_id, patient_id, payment_type_id)
    VALUES(visit_ids.NEXTVAL, TO_TIMESTAMP('04/11/2017 11:00:00','dd/mm/yyyy HH24:MI:SS'), 230.0, 3, 1005, 1);
INSERT INTO visits(visit_id, visit_date, description, total_cost, dentist_id, work_id, patient_id, payment_type_id)
    VALUES(visit_ids.NEXTVAL, TO_TIMESTAMP('05/11/2017 15:15:00','dd/mm/yyyy HH24:MI:SS'), 'Unique system', 350.0, 20, 3, 1006, 1);  
    