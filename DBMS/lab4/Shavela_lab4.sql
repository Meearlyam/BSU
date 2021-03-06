-- 1. Поднимите нижнюю границу минимальной заработной платы в таблице JOB до 1000$.

UPDATE JOB 
SET MINSALARY = 1000; 


-- 2. Поднимите минимальную зарплату в таблице JOB на 10% для всех специальностей, кроме финансового директора.

UPDATE JOB 
SET MINSALARY = MINSALARY * 1.1 
WHERE JOBNAME != 'FINANCIAL DIRECTOR';  


-- 3. Поднимите минимальную зарплату в таблице JOB на 10% для клерков и на 20% для финансового директора 
-- (одним оператором).

UPDATE JOB 
SET MINSALARY = 
	CASE 
		WHEN JOBNAME = 'CLERK' 
			THEN MINSALARY * 1.1 
		WHEN JOBNAME = 'FINANCIAL DIRECTOR' 
			THEN MINSALARY * 1.2 
		ELSE MINSALARY 
	END; 


-- 4. Установите минимальную зарплату финансового директора равной 90% от зарплаты исполнительного директора.

UPDATE JOB 
SET MINSALARY = 0.9 * (SELECT MINSALARY 
							FROM JOB
							WHERE JOBNAME = 'EXECUTIVE DIRECTOR') 
WHERE JOBNAME = 'FINANCIAL DIRECTOR'; 


-- 5. Приведите в таблице EMP имена служащих, начинающиеся на букву ‘J’, к нижнему регистру.

UPDATE EMP 
SET EMPNAME = LOWER(EMPNAME) 
WHERE EMPNAME LIKE 'J%'; 


-- 6. Измените в таблице EMP имена служащих, состоящие из двух слов, так, чтобы оба слова в имени начинались с 
-- заглавной буквы, а продолжались прописными.

UPDATE EMP 
SET EMPNAME = INITCAP(EMPNAME) 
WHERE LENGTH(EMPNAME) - LENGTH(REPLACE(EMPNAME, ' ', '')) + 1 = 2; 


-- 7. Приведите в таблице EMP имена служащих к верхнему регистру.

UPDATE EMP 
SET EMPNAME = UPPER(EMPNAME); 


-- 8. Перенесите отдел исследований (RESEARCH) в тот же город, в котором расположен отдел продаж (SALES).

UPDATE DEPT 
SET DEPTADDR = (SELECT DEPTADDR FROM DEPT WHERE DEPTNAME = 'SALES') 
WHERE DEPTNAME = 'RESEARCH'; 


-- 9. Добавьте нового сотрудника в таблицу EMP. Его имя и фамилия должны совпадать с Вашими, записанными латинскими 
-- буквами согласно паспорту, дата рождения также совпадает с Вашей.

INSERT INTO EMP VALUES (7799, 'VERA SHAVELA', TO_DATE('29-09-1999', 'dd-mm-yyyy'), 7790); 


-- 10. Определите нового сотрудника (см. предыдущее задание) на работу в бухгалтерию (отдел ACCOUNTING) 
-- начиная с текущей даты.

INSERT INTO CAREER VALUES(
	(SELECT JOBNO FROM JOB WHERE JOBNAME = 'CLERK'), 
	(SELECT EMPNO FROM EMP WHERE EMPNAME = 'VERA SHAVELA'), 
	(SELECT DEPTNO FROM DEPT WHERE DEPTNAME = 'ACCOUNTING'), 
	TO_DATE(SYSDATE, 'dd-mm-yyyy'),
	NULL); 


-- 11. Удалите все записи из таблицы TMP_EMP. Добавьте в нее информацию о сотрудниках, которые работают клерками в 
-- настоящий момент.

DELETE FROM TMP_EMP;

INSERT INTO TMP_EMP 
SELECT * FROM EMP 
WHERE EMPNO IN (SELECT EMPNO FROM CAREER 
				NATURAL JOIN JOB 
				WHERE JOBNAME = 'CLERK' AND ENDDATE IS NULL); 


-- 12. Добавьте в таблицу TMP_EMP информацию о тех сотрудниках, которые уже не работают на предприятии, а в период 
-- работы занимали только одну должность.

INSERT INTO TMP_EMP 
SELECT * FROM EMP 
WHERE EMPNO IN (SELECT EMPNO
				FROM CAREER FIRST_C 
				WHERE ENDDATE IS NOT NULL AND (SELECT COUNT(*) 
												FROM CAREER SEC_C
												WHERE ENDDATE IS NOT NULL AND FIRST_C.EMPNO = SEC_C.EMPNO) = 1); 


-- 13. Выполните тот же запрос для тех сотрудников, которые никогда не приступали к работе на предприятии.

INSERT INTO TMP_EMP 
SELECT * FROM EMP 
WHERE EMPNO NOT IN (SELECT EMPNO FROM CAREER); 


-- 14. Удалите все записи из таблицы TMP_JOB и добавьте в нее информацию по тем специальностям, которые не используются 
-- в настоящий момент на предприятии.

DELETE FROM TMP_JOB; 

INSERT INTO TMP_JOB 
SELECT * FROM JOB 
WHERE JOBNO NOT IN (SELECT JOBNO FROM CAREER); 


-- 15. Начислите зарплату в размере 120% минимального должностного оклада всем сотрудникам, работающим на предприятии. 
-- Зарплату начислять по должности, занимаемой сотрудником в настоящий момент и отнести ее на прошлый месяц
-- относительно текущей даты.

INSERT INTO TMP_SALARY 
(SELECT EMPNO, 
		(EXTRACT(MONTH FROM SYSDATE) - 1) AS MONTH, 
		EXTRACT(YAER FROM SYSDATE) AS YEAR, 
		MINSALARY * 1.2 AS SALVALUE 
FROM CAREER NATURAL JOIN JOB 
WHERE ENDDATE IS NULL); 


-- 16. Удалите данные о зарплате за прошлый год.

DELETE FROM TMP_SALARY 
WHERE YEAR = EXTRACT(YEAR FROM SYSDATE) - 1; 


-- 17. Удалите информацию о карьере сотрудников, которые в настоящий момент уже не работают на предприятии, 
-- но когда-то работали.

DELETE FROM TMP_CAREER 
WHERE ENDDATE IS NOT NULL; 


-- 18. Удалите информацию о начисленной зарплате сотрудников, которые в настоящий момент уже не работают на предприятии 
-- (можно использовать результаты работы предыдущего запроса)

DELETE FROM TMP_SALARY 
WHERE EMPNO IN (SELECT EMPNO FROM CAREER WHERE ENDDATE IN NOT NULL); 

DELETE FROM TMP_SALARY 
WHERE EMPNO NOT IN (SELECT EMPNO FROM CAREER); 


-- 19. Удалите записи из таблицы EMP для тех сотрудников, которые никогда не приступали к работе на предприятии.

DELETE FROM TMP_EMP 
WHERE EMPNO NOT IN (SELECT EMPNO FROM CAREER); 