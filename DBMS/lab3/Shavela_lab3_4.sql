-- 1
/*
Требуется используя значения столбца START_DATE получить дату за десять дней до и после приема на работу,
 пол года до и после приема на работу, год до и после приема на работу сотрудника JOHN KLINTON.
*/
SELECT C.STARTDATE + 10 AS "Days + 10",
       C.STARTDATE - 10 AS "Days - 10",
       ADD_MONTHS(C.STARTDATE, 6) AS "Month + 6",
       ADD_MONTHS(C.STARTDATE, -6) AS "Month - 6",
       ADD_MONTHS(C.STARTDATE, 12) AS "Year + 1",
       ADD_MONTHS(C.STARTDATE, -12) AS "Year + 1"
FROM CAREER C 
INNER JOIN EMP E
ON C.EMPNO = E.EMPNO
WHERE E.EMPNAME = 'JOHN KLINTON';

-- 2
/*
Требуется найти разность между двумя датами и представить результат в днях. 
Вычислите разницу в днях между датами приема на работу сотрудников JOHN MARTIN и ALEX BOUSH.
*/
SELECT JOHN_START - ALEX_START AS "Difference"
FROM (SELECT C.STARTDATE AS JOHN_START FROM CAREER C NATURAL JOIN EMP WHERE EMP.EMPNAME = 'JOHN KLINTON'), 
     (SELECT C.STARTDATE AS ALEX_START FROM CAREER C NATURAL JOIN EMP WHERE EMP.EMPNAME = 'ALEX BOUSH');

-- 3
/*
Требуется найти разность между двумя датами в месяцах и в годах.
*/
SELECT ROUND(MONTHS_BETWEEN(MAX_DATE, MIN_DATE)) AS "Difference in months",
       ROUND(MONTHS_BETWEEN(MAX_DATE, MIN_DATE)) / 12 AS "Difference in years"
FROM (SELECT MIN(STARTDATE) MIN_DATE, MAX(STARTDATE) MAX_DATE FROM CAREER);

-- 4
/*
Требуется определить интервал времени в днях между двумя датами. 
Для каждого сотрудника 20-го отделе найти сколько дней прошло между датой его приема на работу 
и датой приема на работу следующего сотрудника.
*/
SELECT STARTDATE, NEXT_DATE, NEXT_DATE - STARTDATE AS "Difference"
FROM ( SELECT STARTDATE, LEAD(STARTDATE) OVER(ORDER BY STARTDATE) NEXT_DATE FROM CAREER);

-- 5
/*
Требуется подсчитать количество дней в году по столбцу START_DATE.
*/ 
SELECT STARTDATE, ADD_MONTHS(TRUNC(STARTDATE,'y'), 12) - TRUNC(STARTDATE, 'y') AS "Days in year"
FROM CAREER;

-- 6
/*
Требуется разложить текущую дату на день, месяц, год, секунды, минуты, часы. 
Результаты вернуть в численном виде.
*/
SELECT TO_NUMBER(TO_CHAR(SYSDATE,'dd')) DAY,
       TO_NUMBER(TO_CHAR(SYSDATE,'mm')) MONTH,
       TO_NUMBER(TO_CHAR(SYSDATE,'yyyy')) YEAR,
       TO_NUMBER(TO_CHAR(SYSDATE,'ss')) sec,
       TO_NUMBER(TO_CHAR(SYSDATE,'mi')) MIN,
       TO_NUMBER(TO_CHAR(SYSDATE,'hh24')) HOUR
FROM dual;

/*
SELECT STARTDATE, TO_NUMBER(TO_CHAR(STARTDATE, 'hh24')) hour,
	TO_NUMBER(TO_CHAR(STARTDATE, 'mi')) min,
	TO_NUMBER(TO_CHAR(STARTDATE, 'ss')) sec,
	TO_NUMBER(TO_CHAR(STARTDATE, 'dd')) day,
	TO_NUMBER(TO_CHAR(STARTDATE, 'mm')) month,
	TO_NUMBER(TO_CHAR(STARTDATE, 'yyyy')) year
FROM CAREER;
*/

-- 7
/*
Требуется получить первый и последний дни текущего месяца.
*/
SELECT TRUNC(SYSDATE, 'mm') FIRSTDAY, LAST_DAY(SYSDATE) LASTDAY
	FROM dual;
/*SELECT STARTDATE, TRUNC(STARTDATE, 'mm') FIRSTDAY,
LAST_DAY(STARTDATE) LASTDAY
FROM CAREER;
*/


-- 8
/*
Требуется возвратить даты начала и конца каждого из четырех кварталов данного года.
*/
SELECT ROWNUM KVARTAL, 
ADD_MONTHS(TRUNC(SYSDATE, 'Y'), (ROWNUM - 1) * 3) K_START,
ADD_MONTHS(TRUNC(SYSDATE, 'Y'), ROWNUM * 3) - 1 K_END
FROM CAREER
WHERE ROWNUM <= 4;

--9
/* Требуется найти все даты года, соответствующие заданному дню недели.
Сформируйте список понедельников текущего года.
Далее с помощью оператора CONNECT BY возвращаем все дни текущего года
Завершающий шаг – с использованием функции TO_CHAR выбрать только нужные.
все дни:
*/
WITH x AS (SELECT TRUNC(SYSDATE,'y')+level-1 DAY
FROM dual CONNECT BY LEVEL <= ADD_MONTHS(TRUNC(SYSDATE,'y'), 12) - TRUNC(SYSDATE,'y'))
SELECT * FROM x
WHERE TO_CHAR(DAY, 'dy') = 'mon';

--10
/*
Требуется создать календарь на текущий месяц. Календарь должен иметь семь
столбцов в ширину и пять строк вниз.
*/
WITH x AS (
SELECT *
FROM (
SELECT TO_CHAR(TRUNC(SYSDATE,'mm')+level-1,'iw') week_number,
        TO_CHAR(TRUNC(SYSDATE,'mm')+level-1,'dd') day_number_in_month,
        TO_NUMBER(TO_CHAR(TRUNC(SYSDATE,'mm')+level-1,'d')) day_of_week,
        TO_CHAR(TRUNC(SYSDATE,'mm')+level-1,'mm') cur_month,
        TO_CHAR(SYSDATE,'mm') MONTH
 FROM dual
   CONNECT BY LEVEL <= 31
         )
   WHERE cur_month = MONTH
  )
  SELECT MAX(CASE day_of_week WHEN 2 THEN day_number_in_month END) Mo,
         MAX(CASE day_of_week WHEN 3 THEN day_number_in_month END) Tu,
         MAX(CASE day_of_week WHEN 4 THEN day_number_in_month END) We,
         MAX(CASE day_of_week WHEN 5 THEN day_number_in_month END) Th,
         MAX(CASE day_of_week WHEN 6 THEN day_number_in_month END) Fr,
         MAX(CASE day_of_week WHEN 7 THEN day_number_in_month END) Sa,
         MAX(CASE day_of_week WHEN 1 THEN day_number_in_month END) Su
 FROM x
GROUP BY week_number  
ORDER BY week_number