--   Составьте запросы на выборку данных с использованием следующих операторов, конструкций и функций
--   языка SQL:

--1. простого оператора CASE;
--   Выводит ФИО всех пациентов, хранящихся в базе, и наличие у них доктора 
SELECT patient_surname AS SURNAME, patient_name AS NAME, patient_patronymic AS PATRONYMIC,
    CASE dentist_id
        WHEN 10
            THEN 'Best dentist ever!!!'
        ELSE dentist_surname
    END
    AS DENTIST
    FROM patients LEFT JOIN dentists USING(dentist_id)

--2. поискового оператора CASE;
--   Выводит ФИО всех пациентов, хранящихся в базе, и их возраст 
SELECT patient_surname AS SURNAME, patient_name AS NAME, patient_patronymic AS PATRONYMIC,
    CASE
        WHEN MONTHS_BETWEEN (SYSDATE, patient_birthday) / 12 < 18
            THEN 'Child'
        WHEN MONTHS_BETWEEN (SYSDATE, patient_birthday) / 12 >= 60
            THEN 'Aged'
        ELSE 'Adult'
    END
    AS BIRTHDAY
    FROM patients

--3. оператора WITH;
--   Выводит посещения пациентов, у которых есть личный врач
WITH pat AS (SELECT * FROM patients WHERE dentist_id IS NOT NULL)
SELECT patient_name, patient_surname, TO_CHAR(visit_date, 'dd/mm/yyyy HH24:MI:SS')
    FROM visits v INNER JOIN pat USING (patient_id)

--4. встроенного представления;
--   Выводит посещения за последние 40 дней
SELECT patient_name, patient_surname, TO_CHAR(visit_date, 'dd/mm/yyyy HH24:MI:SS') 
    FROM (SELECT * FROM visits INNER JOIN patients USING(patient_id)) 
    WHERE (SYSDATE - cast(visit_date AS DATE)) < 40

--5. некоррелированного запроса
--   Выводит пациентов, чьи врачи старше 40
SELECT patient_name, patient_surname
    FROM patients WHERE dentist_id IN 
        (SELECT dentist_id FROM dentists
            WHERE MONTHS_BETWEEN (SYSDATE, dentist_birthday) / 12 > 40)

--6. коррелированного запроса
--   Выводит пациентов, чьи доктора младше
SELECT patient_name, patient_surname
    FROM patients p
    WHERE p.dentist_id IN
        (SELECT dentist_id FROM dentists
            WHERE dentist_birthday > p.patient_birthday)

--7. функции NULLIF
SELECT patient_name, NULLIF(patient_surname, 'Kudosh') FROM patients

--8. функции NVL2
--   Аналог запроса №1
SELECT patient_surname AS SURNAME, patient_name AS NAME, patient_patronymic AS PATRONYMIC,
    NVL2(dentist_id, 'Yes', 'No') AS DENTIST
    FROM patients

--9. TOP-N анализа
--   Выводит 3 пациентов с наибольшим числом посещений
SELECT visits, patient_name, patient_surname
    FROM (SELECT COUNT(visit_id) AS visits, patient_id
        FROM visits
        GROUP BY patient_id
        ORDER BY COUNT(visit_id) DESC) INNER JOIN patients USING(patient_id)
    WHERE ROWNUM <= 3;

--10. функции ROLLUP
--    Выводит потраченную в клинике сумму
WITH sum AS (SELECT patient_id, COALESCE(CAST(visit_id AS CHAR(5)),'Total') AS visit, SUM(total_cost) AS total_cost
    FROM visits
    GROUP BY ROLLUP(patient_id, visit_id))

SELECT patient_name, patient_surname, visit, total_cost
    FROM sum LEFT JOIN patients USING(patient_id)

--11. Составьте запрос на использование оператора MERGE языка манипулирования данными.

SELECT * FROM dentists;
DROP TABLE new_dentists;

CREATE TABLE new_dentists (
    dentist_id           INTEGER PRIMARY KEY,
    dentist_surname      VARCHAR2(100) NOT NULL,
    dentist_name         VARCHAR2(100) NOT NULL,
    dentist_patronymic   VARCHAR2(100),
    dentist_address      VARCHAR2(100) NOT NULL,
    dentist_birthday     DATE NOT NULL
);

INSERT INTO new_dentists VALUES(10, 'Zhebenkova', 'Tatyana', 'Ivanovna', 'Yanki Mavra street, 11-23', TO_DATE('21/04/1981', 'dd/mm/yyyy'));
INSERT INTO new_dentists VALUES(40, 'Bodrov', 'Sergey', 'Sergeevich', 'Masherova avenue, 23-124', TO_DATE('27/12/1971', 'dd/mm/yyyy'));

MERGE INTO new_dentists new_d
    USING (SELECT * FROM dentists) d
        ON (new_d.dentist_id = d.dentist_id)
   
    WHEN NOT MATCHED THEN
        INSERT (new_d.dentist_id, new_d.dentist_surname, new_d.dentist_name, new_d.dentist_patronymic, new_d.dentist_address, new_d.dentist_birthday)
        VALUES (d.dentist_id, d.dentist_surname, d.dentist_name, d.dentist_patronymic, d.dentist_address, d.dentist_birthday);


SELECT * FROM new_dentists;
SELECT * FROM dentists;